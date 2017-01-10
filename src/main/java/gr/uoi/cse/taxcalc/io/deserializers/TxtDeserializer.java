package gr.uoi.cse.taxcalc.io.deserializers;

import gr.uoi.cse.taxcalc.data.FamilyStatus;
import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtDeserializer implements Deserializer {
    @Override
    public List<Taxpayer> deserializeFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(path));
        return deserializeInternal(scanner);
    }

    @Override
    public List<Taxpayer> deserializeData(String data) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
        return deserializeInternal(scanner);
    }

    private List<Taxpayer> deserializeInternal(Scanner scanner) {
        List<Taxpayer> taxpayers = new ArrayList<>();
        Taxpayer currentTaxpayer = null;

        while (scanner.hasNextLine()) {
            String fileLine = scanner.nextLine();

            if (fileLine.isEmpty() || fileLine.startsWith("Receipts:")) {
                continue;
            }

            if (fileLine.startsWith("Name: ")) {
                if (currentTaxpayer != null) {
                    taxpayers.add(currentTaxpayer);
                }

                currentTaxpayer = parseTaxpayer(fileLine, scanner);
                continue;
            }

            if (currentTaxpayer != null) {
                currentTaxpayer.addReceiptToList(parseReceipt(fileLine, scanner));
            }
        }

        if (currentTaxpayer != null && !taxpayers.contains(currentTaxpayer)) {
            taxpayers.add(currentTaxpayer);
        }

       return taxpayers;
    }

    private Taxpayer parseTaxpayer(String firstLine, Scanner scanner) {
        String taxpayerName = getParameterValueFromTxtFileLine(firstLine, "Name: ");
        String taxpayerAFM = getParameterValueFromTxtFileLine(scanner.nextLine(), "AFM: ");
        String taxpayerStatus = getParameterValueFromTxtFileLine(scanner.nextLine(), "Status: ");
        String taxpayerIncome = getParameterValueFromTxtFileLine(scanner.nextLine(), "Income: ");

        return new Taxpayer(taxpayerName, taxpayerAFM, FamilyStatus.valueOf(taxpayerStatus), Double.parseDouble(taxpayerIncome));
    }

    private Receipt parseReceipt(String firstLine, Scanner scanner) {
        String receiptID = getParameterValueFromTxtFileLine(firstLine, "receipts ID: ");
        String receiptDate = getParameterValueFromTxtFileLine(scanner.nextLine(), "Date: ");
        String receiptKind = getParameterValueFromTxtFileLine(scanner.nextLine(), "Kind: ");
        String receiptAmount = getParameterValueFromTxtFileLine(scanner.nextLine(), "Amount: ");
        String receiptCompany = getParameterValueFromTxtFileLine(scanner.nextLine(), "Company: ");
        String receiptCountry = getParameterValueFromTxtFileLine(scanner.nextLine(), "Country: ");
        String receiptCity = getParameterValueFromTxtFileLine(scanner.nextLine(), "City: ");
        String receiptStreet = getParameterValueFromTxtFileLine(scanner.nextLine(), "Street: ");
        String receiptNumber = getParameterValueFromTxtFileLine(scanner.nextLine(), "Number: ");

        return new Receipt(ReceiptKind.valueOf(receiptKind), receiptID, receiptDate, Double.parseDouble(receiptAmount), receiptCompany, receiptCountry, receiptCity, receiptStreet, receiptNumber);
    }

    private static String getParameterValueFromTxtFileLine(String fileLine, String parameterName){
        return fileLine.substring(parameterName.length(), fileLine.length());
    }
}
