package gr.uoi.cse.taxcalc.io.serializers;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Writer;

public class XmlSerializer extends Serializer {
    @Override
    public void serialize(Taxpayer taxpayer, Writer writer) throws ParserConfigurationException, TransformerException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document dom = db.newDocument();

        // Create our taxpayer.
        Element taxpayerElement = dom.createElement("Taxpayer");
        addTaxpayerAttributes(taxpayer, taxpayerElement);

        // Add the Receipts.
        Element receiptsElement = dom.createElement("Receipts");

        for (Receipt receipt : taxpayer.getReceiptsArrayList()) {
            addReceipt(receipt, receiptsElement);
        }

        taxpayerElement.appendChild(receiptsElement);

        // Add the taxpayer to the document.
        dom.appendChild(taxpayerElement);

        // Write to our writer.
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(dom), new StreamResult(writer));
    }

    private void addTaxpayerAttributes(Taxpayer taxpayer, Element taxpayerElement) {
        addChild("Name", taxpayer.getName(), taxpayerElement);
        addChild("TID", taxpayer.getAFM(), taxpayerElement);
        addChild("FamilyStatus", taxpayer.getFamilyStatus().toString(), taxpayerElement);
        addChild("Income", Double.toString(taxpayer.getIncome()), taxpayerElement);
    }

    private void addReceipt(Receipt receipt, Element receiptsElement) {
        Element receiptElement = receiptsElement.getOwnerDocument().createElement("Receipt");

        // Add the ID.
        Attr id = receiptsElement.getOwnerDocument().createAttribute("id");
        id.setValue(receipt.getId());
        receiptElement.setAttributeNode(id);

        // Add the members.
        addChild("Kind", receipt.getKind().toString(), receiptElement);
        addChild("Date", receipt.getDate(), receiptElement);
        addChild("Amount", Double.toString(receipt.getAmount()), receiptElement);
        addChild("Name", receipt.getCompany().getName(), receiptElement);
        addChild("Country", receipt.getCompany().getCountry(), receiptElement);
        addChild("City", receipt.getCompany().getCity(), receiptElement);
        addChild("Street", receipt.getCompany().getStreet(), receiptElement);
        addChild("Number", receipt.getCompany().getNumber(), receiptElement);

        receiptsElement.appendChild(receiptElement);
    }

    private void addChild(String name, String value, Element element) {
        Element attribute = element.getOwnerDocument().createElement(name);
        attribute.appendChild(element.getOwnerDocument().createTextNode(value));
        element.appendChild(attribute);
    }
}
