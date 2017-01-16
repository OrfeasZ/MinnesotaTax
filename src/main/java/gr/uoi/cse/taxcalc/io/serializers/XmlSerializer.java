package gr.uoi.cse.taxcalc.io.serializers;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.Writer;

public class XmlSerializer extends Serializer {
    @Override
    public String getExtension() {
        return "xml";
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
        writeLine("<Receipts>", writer);
        writeLine(writer);

        for (Receipt receipt : taxpayer.getReceipts()) {
            serializeReceipt(receipt, writer);
        }

        writeLine("</Receipts>", writer);
    }

    @Override
    public void serializeInfo(final Taxpayer taxpayer, final Writer writer)
            throws ParserConfigurationException,
            TransformerException,
            IOException {
        writeLine("<Name> " + taxpayer.getName() + " </Name>", writer);
        writeLine("<AFM> " + taxpayer.getAFM() + " </AFM>", writer);
        writeLine("<Income> " + taxpayer.getIncome() + " </Income>", writer);
        writeLine("<BasicTax> " + taxpayer.getBasicTax() + " </BasicTax>", writer);

        if (taxpayer.getTaxIncrease() != 0) {
            writeLine("<TaxIncrease> " + taxpayer.getTaxIncrease() + " </TaxIncrease>", writer);
        } else {
            writeLine("<TaxDecrease> " + taxpayer.getTaxDecrease() + " </TaxDecrease>", writer);
        }

        writeLine("<TotalTax> " + taxpayer.getTotalTax() + " </TotalTax>", writer);
        writeLine("<Receipts> " + taxpayer.getTotalReceiptsAmount() + " </Receipts>", writer);
        writeLine("<Entertainment> " + taxpayer.getReceiptsTotalAmount(ReceiptKind.ENTERTAINMENT) + " </Entertainment>", writer);
        writeLine("<Basic> " + taxpayer.getReceiptsTotalAmount(ReceiptKind.BASIC) + " </Basic>", writer);
        writeLine("<Travel> " + taxpayer.getReceiptsTotalAmount(ReceiptKind.TRAVEL) + " </Travel>", writer);
        writeLine("<Health> " + taxpayer.getReceiptsTotalAmount(ReceiptKind.HEALTH) + " </Health>", writer);
        writeLine("<Other> " + taxpayer.getReceiptsTotalAmount(ReceiptKind.OTHER) + " </Other>", writer);
    }

    private void serializeTaxpayer(final Taxpayer taxpayer,
                                   final Writer writer) throws IOException {
        writeLine("<Name> " + taxpayer.getName() + " </Name>", writer);
        writeLine("<AFM> " + taxpayer.getAFM() + " </AFM>", writer);
        writeLine("<Status> " + taxpayer.getFamilyStatus() + " </Status>", writer);
        writeLine("<Income> " + taxpayer.getIncome() + " </Income>", writer);
    }

    private void serializeReceipt(final Receipt receipt, final Writer writer)
            throws IOException {
        writeLine("<ReceiptID> " + receipt.getId() + " </ReceiptID>", writer);
        writeLine("<Date> " + receipt.getDate() + " </Date>", writer);
        writeLine("<Kind> " + receipt.getKind() + " </Kind>", writer);
        writeLine("<Amount> " + receipt.getAmount() + " </Amount>", writer);
        writeLine("<Company> " + receipt.getCompany() + " </Company>", writer);
        writeLine("<Country> " + receipt.getCountry() + " </Country>", writer);
        writeLine("<City> " + receipt.getCity() + " </City>", writer);
        writeLine("<Street> " + receipt.getStreet() + " </Street>", writer);
        writeLine("<Number> " + receipt.getNumber() + " </Number>", writer);
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
