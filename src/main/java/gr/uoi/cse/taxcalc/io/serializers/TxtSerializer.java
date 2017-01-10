package gr.uoi.cse.taxcalc.io.serializers;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.Writer;

public class TxtSerializer extends Serializer {
    @Override
    void serialize(Taxpayer taxpayer, Writer writer) throws ParserConfigurationException, TransformerException, IOException {
        serializeTaxpayer(taxpayer, writer);

        if (taxpayer.getReceiptsArrayList().size() == 0) {
            return;
        }

        writeLine(writer);
        writeLine("Receipts:", writer);
        writeLine(writer);

        for (Receipt receipt : taxpayer.getReceiptsArrayList()) {
            serializeReceipt(receipt, writer);
        }
    }

    private void serializeTaxpayer(Taxpayer taxpayer, Writer writer) throws IOException {
        writeLine("Name: " + taxpayer.getName(), writer);
        writeLine("AFM: " + taxpayer.getAFM(), writer);
        writeLine("Status: " + taxpayer.getFamilyStatus(), writer);
        writeLine("Income: " + taxpayer.getIncome(), writer);
    }

    private void serializeReceipt(Receipt receipt, Writer writer) throws IOException {
        writeLine("Receipt ID: " + receipt.getId(), writer);
        writeLine("Date: " + receipt.getDate(), writer);
        writeLine("Kind: " + receipt.getKind(), writer);
        writeLine("Amount: " + receipt.getAmount(), writer);
        writeLine("Company: " + receipt.getCompany().getName(), writer);
        writeLine("Country: " + receipt.getCompany().getCountry(), writer);
        writeLine("City: " + receipt.getCompany().getCity(), writer);
        writeLine("Street: " + receipt.getCompany().getStreet(), writer);
        writeLine("Number: " + receipt.getCompany().getNumber(), writer);
        writeLine(writer);
    }

    private void writeLine(Writer writer) throws IOException {
        writer.write("\n");
    }

    private void writeLine(String line, Writer writer) throws IOException {
        writer.write(line);
        writer.write("\n");
    }
}
