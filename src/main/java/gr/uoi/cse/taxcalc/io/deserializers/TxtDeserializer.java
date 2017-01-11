package gr.uoi.cse.taxcalc.io.deserializers;

import gr.uoi.cse.taxcalc.data.FamilyStatus;
import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TxtDeserializer implements Deserializer {
    @Override
    public Taxpayer deserializeFile(final String path)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(path));
        return deserializeInternal(scanner);
    }

    @Override
    public Taxpayer deserializeData(final String data) {
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))
        );

        return deserializeInternal(scanner);
    }

    private Taxpayer deserializeInternal(final Scanner scanner) {
        Taxpayer taxpayer = parseTaxpayer(scanner);

        while (scanner.hasNextLine()) {
            String fileLine = scanner.nextLine();

            if (fileLine.isEmpty() || fileLine.contains("Receipts:")) {
                continue;
            }

            taxpayer.addReceipt(parseReceipt(fileLine, scanner));
        }

        return taxpayer;
    }

    private Taxpayer parseTaxpayer(final Scanner scanner) {
        String taxpayerName = getParamValue(scanner.nextLine(), "Name: ");
        String taxpayerAFM = getParamValue(scanner.nextLine(), "AFM: ");
        String taxpayerStatus = getParamValue(scanner.nextLine(), "Status: ");
        String taxpayerIncome = getParamValue(scanner.nextLine(), "Income: ");

        return new Taxpayer(
                taxpayerName,
                taxpayerAFM,
                FamilyStatus.getEnum(taxpayerStatus),
                Double.parseDouble(taxpayerIncome)
        );
    }

    private Receipt parseReceipt(final String firstLine,
                                 final Scanner scanner) {
        String receiptID = getParamValue(firstLine, "Receipt ID: ");
        String receiptDate = getParamValue(scanner.nextLine(), "Date: ");
        String receiptKind = getParamValue(scanner.nextLine(), "Kind: ");
        String receiptAmount = getParamValue(scanner.nextLine(), "Amount: ");
        String receiptCompany = getParamValue(scanner.nextLine(), "Company: ");
        String receiptCountry = getParamValue(scanner.nextLine(), "Country: ");
        String receiptCity = getParamValue(scanner.nextLine(), "City: ");
        String receiptStreet = getParamValue(scanner.nextLine(), "Street: ");
        String receiptNumber = getParamValue(scanner.nextLine(), "Number: ");

        return new Receipt(
                ReceiptKind.getEnum(receiptKind),
                receiptID,
                receiptDate,
                Double.parseDouble(receiptAmount),
                receiptCompany,
                receiptCountry,
                receiptCity,
                receiptStreet,
                receiptNumber
        );
    }

    private String getParamValue(final String fileLine,
                                 final String parameterName) {
        return fileLine.substring(parameterName.length(), fileLine.length());
    }
}
