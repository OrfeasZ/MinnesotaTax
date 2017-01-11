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

public class XmlDeserializer implements Deserializer {
    @Override
    public Taxpayer deserializeFile(final String path) throws FileNotFoundException {
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

            if (fileLine.isEmpty() || fileLine.contains("<Receipts>")) {
                continue;
            }

            if (fileLine.contains("</Receipts>")) {
                break;
            }

            taxpayer.addReceipt(parseReceipt(fileLine, scanner));
        }

        return taxpayer;
    }

    private Taxpayer parseTaxpayer(final Scanner scanner) {
        String taxpayerName = getParamValue(scanner.nextLine(), "<Name> ", " </Name>");
        String taxpayerAFM = getParamValue(scanner.nextLine(), "<AFM> ", " </AFM>");
        String taxpayerStatus = getParamValue(scanner.nextLine(), "<Status> ", " </Status>");
        String taxpayerIncome = getParamValue(scanner.nextLine(), "<Income> ", " </Income>");

        return new Taxpayer(
                taxpayerName,
                taxpayerAFM,
                FamilyStatus.getEnum(taxpayerStatus),
                Double.parseDouble(taxpayerIncome)
        );
    }

    private Receipt parseReceipt(final String firstLine,
                                 final Scanner scanner) {
        String receiptID = getParamValue(firstLine, "<ReceiptID> ", " </ReceiptID>");
        String receiptDate = getParamValue(scanner.nextLine(), "<Date> ", " </Date>");
        String receiptKind = getParamValue(scanner.nextLine(), "<Kind> ", " </Kind>");
        String receiptAmount = getParamValue(scanner.nextLine(), "<Amount> ", " </Amount>");
        String receiptCompany = getParamValue(scanner.nextLine(), "<Company> ", " </Company>");
        String receiptCountry = getParamValue(scanner.nextLine(), "<Country> ", " </Country>");
        String receiptCity = getParamValue(scanner.nextLine(), "<City> ", " </City>");
        String receiptStreet = getParamValue(scanner.nextLine(), "<Street> ", " </Street>");
        String receiptNumber = getParamValue(scanner.nextLine(), "<Number> ", " </Number>");

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
                                 final String parameterStartField,
                                 final String parameterEndField) {
        return fileLine.substring(parameterStartField.length(),
                fileLine.length() - parameterEndField.length());
    }
}
