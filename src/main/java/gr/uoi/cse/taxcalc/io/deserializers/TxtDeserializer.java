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
    public Taxpayer deserializeFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(path));
        return deserializeInternal(scanner);
    }

    @Override
    public Taxpayer deserializeData(String data) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
        return deserializeInternal(scanner);
    }

    private Taxpayer deserializeInternal(Scanner scanner) {
        Taxpayer taxpayer = parseTaxpayer(scanner);

        while (scanner.hasNextLine()) {
            String fileLine = scanner.nextLine();

            if (fileLine.isEmpty() || fileLine.contains("Receipts:")) {
                continue;
            }

            taxpayer.addReceiptToList(parseReceipt(fileLine, scanner));
        }

        return taxpayer;
    }

    private Taxpayer parseTaxpayer(Scanner scanner) {
        String taxpayerName = getParameterValueFromTxtFileLine(scanner.nextLine(), "Name: ");
        String taxpayerAFM = getParameterValueFromTxtFileLine(scanner.nextLine(), "AFM: ");
        String taxpayerStatus = getParameterValueFromTxtFileLine(scanner.nextLine(), "Status: ");
        String taxpayerIncome = getParameterValueFromTxtFileLine(scanner.nextLine(), "Income: ");

        return new Taxpayer(taxpayerName, taxpayerAFM, FamilyStatus.getEnum(taxpayerStatus), Double.parseDouble(taxpayerIncome));
    }

    private Receipt parseReceipt(String firstLine, Scanner scanner) {
        String receiptID = getParameterValueFromTxtFileLine(firstLine, "Receipt ID: ");
        String receiptDate = getParameterValueFromTxtFileLine(scanner.nextLine(), "Date: ");
        String receiptKind = getParameterValueFromTxtFileLine(scanner.nextLine(), "Kind: ");
        String receiptAmount = getParameterValueFromTxtFileLine(scanner.nextLine(), "Amount: ");
        String receiptCompany = getParameterValueFromTxtFileLine(scanner.nextLine(), "Company: ");
        String receiptCountry = getParameterValueFromTxtFileLine(scanner.nextLine(), "Country: ");
        String receiptCity = getParameterValueFromTxtFileLine(scanner.nextLine(), "City: ");
        String receiptStreet = getParameterValueFromTxtFileLine(scanner.nextLine(), "Street: ");
        String receiptNumber = getParameterValueFromTxtFileLine(scanner.nextLine(), "Number: ");

        return new Receipt(ReceiptKind.getEnum(receiptKind), receiptID, receiptDate, Double.parseDouble(receiptAmount), receiptCompany, receiptCountry, receiptCity, receiptStreet, receiptNumber);
    }

    private String getParameterValueFromTxtFileLine(String fileLine, String parameterName) {
        return fileLine.substring(parameterName.length(), fileLine.length());
    }
}
