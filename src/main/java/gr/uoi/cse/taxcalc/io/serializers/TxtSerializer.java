package gr.uoi.cse.taxcalc.io.serializers;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.Writer;

public class TxtSerializer extends Serializer {
    @Override
    public String getExtension() {
        return "txt";
    }

    @Override
    public void serializeFull(final Taxpayer taxpayer, final Writer writer)
            throws ParserConfigurationException,
            TransformerException,
            IOException {
        serializeTaxpayer(taxpayer, writer);

        if (taxpayer.getReceipts().size() == 0) {
            return;
        }

        writeLine(writer);
        writeLine("Receipts:", writer);
        writeLine(writer);

        for (Receipt receipt : taxpayer.getReceipts()) {
            serializeReceipt(receipt, writer);
        }
    }

    @Override
    public void serializeInfo(final Taxpayer taxpayer, final Writer writer)
            throws ParserConfigurationException,
            TransformerException,
            IOException {
        writeLine("Name: " + taxpayer.getName(), writer);
        writeLine("AFM: " + taxpayer.getAFM(), writer);
        writeLine("Income: " + taxpayer.getIncome(), writer);
        writeLine("Basic Tax: " + taxpayer.getBasicTax(), writer);

        if (taxpayer.getTaxIncrease() != 0) {
            writeLine("Tax Increase: " + taxpayer.getTaxIncrease(), writer);
        } else {
            writeLine("Tax Decrease: " + taxpayer.getTaxDecrease(), writer);
        }

        writeLine("Total Tax: " + taxpayer.getTotalTax(), writer);
        writeLine("Total Receipts Amount: " + taxpayer.getTotalReceiptsAmount(), writer);
        writeLine("Entertainment: " + taxpayer.getReceiptsTotalAmount(ReceiptKind.ENTERTAINMENT), writer);
        writeLine("Basic: " + taxpayer.getReceiptsTotalAmount(ReceiptKind.BASIC), writer);
        writeLine("Travel: " + taxpayer.getReceiptsTotalAmount(ReceiptKind.TRAVEL), writer);
        writeLine("Health: " + taxpayer.getReceiptsTotalAmount(ReceiptKind.HEALTH), writer);
        writeLine("Other: " + taxpayer.getReceiptsTotalAmount(ReceiptKind.OTHER), writer);
    }

    private void serializeTaxpayer(final Taxpayer taxpayer,
                                   final Writer writer) throws IOException {
        writeLine("Name: " + taxpayer.getName(), writer);
        writeLine("AFM: " + taxpayer.getAFM(), writer);
        writeLine("Status: " + taxpayer.getFamilyStatus(), writer);
        writeLine("Income: " + taxpayer.getIncome(), writer);
    }

    private void serializeReceipt(final Receipt receipt,
                                  final Writer writer) throws IOException {
        writeLine("Receipt ID: " + receipt.getId(), writer);
        writeLine("Date: " + receipt.getDate(), writer);
        writeLine("Kind: " + receipt.getKind(), writer);
        writeLine("Amount: " + receipt.getAmount(), writer);
        writeLine("Company: " + receipt.getCompany(), writer);
        writeLine("Country: " + receipt.getCountry(), writer);
        writeLine("City: " + receipt.getCity(), writer);
        writeLine("Street: " + receipt.getStreet(), writer);
        writeLine("Number: " + receipt.getNumber(), writer);
        writeLine(writer);
    }

    private void writeLine(final Writer writer) throws IOException {
        writer.append("\n");
    }

    private void writeLine(final String line, final Writer writer)
            throws IOException {
        writer.append(line);
        writer.append("\n");
    }
}
