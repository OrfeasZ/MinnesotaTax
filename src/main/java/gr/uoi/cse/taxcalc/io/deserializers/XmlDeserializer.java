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

            if (fileLine.isEmpty() || fileLine.contains("<Receipts>")) {
                continue;
            }

            if (fileLine.contains("</Receipts>")) {
                break;
            }

            taxpayer.addReceiptToList(parseReceipt(fileLine, scanner));
        }

        return taxpayer;
    }

    private Taxpayer parseTaxpayer(Scanner scanner) {
        String taxpayerName = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Name> ", " </Name>");
        String taxpayerAFM = getParameterValueFromXmlFileLine(scanner.nextLine(), "<AFM> ", " </AFM>");
        String taxpayerStatus = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Status> ", " </Status>");
        String taxpayerIncome = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Income> ", " </Income>");

        return new Taxpayer(taxpayerName, taxpayerAFM, FamilyStatus.getEnum(taxpayerStatus), Double.parseDouble(taxpayerIncome));
    }

    private Receipt parseReceipt(String firstLine, Scanner scanner) {
        String receiptID = getParameterValueFromXmlFileLine(firstLine, "<ReceiptID> ", " </ReceiptID>");
        String receiptDate = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Date> ", " </Date>");
        String receiptKind = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Kind> ", " </Kind>");
        String receiptAmount = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Amount> ", " </Amount>");
        String receiptCompany = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Company> ", " </Company>");
        String receiptCountry = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Country> ", " </Country>");
        String receiptCity = getParameterValueFromXmlFileLine(scanner.nextLine(), "<City> ", " </City>");
        String receiptStreet = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Street> ", " </Street>");
        String receiptNumber = getParameterValueFromXmlFileLine(scanner.nextLine(), "<Number> ", " </Number>");

        return new Receipt(ReceiptKind.getEnum(receiptKind), receiptID, receiptDate, Double.parseDouble(receiptAmount), receiptCompany, receiptCountry, receiptCity, receiptStreet, receiptNumber);
    }

    private String getParameterValueFromXmlFileLine(String fileLine, String parameterStartField, String parameterEndField) {
        return fileLine.substring(parameterStartField.length(), fileLine.length() - parameterEndField.length());
    }
}
