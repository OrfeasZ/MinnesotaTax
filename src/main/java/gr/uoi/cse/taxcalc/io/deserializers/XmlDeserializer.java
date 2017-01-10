package gr.uoi.cse.taxcalc.io.deserializers;

import gr.uoi.cse.taxcalc.data.FamilyStatus;
import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class XmlDeserializer implements Deserializer {
    @Override
    public Taxpayer deserializeFile(String path) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = db.parse(path);
        return deserializeInternal(dom);
    }

    @Override
    public Taxpayer deserializeData(String data) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(data));
        Document dom = db.parse(source);
        return deserializeInternal(dom);
    }

    private Taxpayer deserializeInternal(Document dom) {
        // Create our taxpayer object.
        Taxpayer taxpayer = parseTaxpayer(dom);

        // Read the receipts.
        NodeList receiptNodes = dom.getElementsByTagName("Receipts");

        for (int j = 0; j < receiptNodes.getLength(); ++j) {
            Element receiptElement = (Element) receiptNodes.item(j);
            taxpayer.addReceiptToList(parseReceipt(receiptElement));
        }

        return taxpayer;
    }

    private Taxpayer parseTaxpayer(Document elem) {
        return new Taxpayer(
                elem.getElementsByTagName("Name").item(0).getNodeValue(),
                elem.getElementsByTagName("TID").item(0).getNodeValue(),
                FamilyStatus.getEnum(elem.getElementsByTagName("FamilyStatus").item(0).getNodeValue()),
                Double.parseDouble(elem.getElementsByTagName("Income").item(0).getNodeValue())
        );
    }

    private Receipt parseReceipt(Element elem) {
        return new Receipt(
                ReceiptKind.getEnum(elem.getElementsByTagName("Kind").item(0).getNodeValue()),
                elem.getAttributeNode("id").getNodeValue(),
                elem.getElementsByTagName("Date").item(0).getNodeValue(),
                Double.parseDouble(elem.getElementsByTagName("Amount").item(0).getNodeValue()),
                elem.getElementsByTagName("Name").item(0).getNodeValue(),
                elem.getElementsByTagName("Country").item(0).getNodeValue(),
                elem.getElementsByTagName("City").item(0).getNodeValue(),
                elem.getElementsByTagName("Street").item(0).getNodeValue(),
                elem.getElementsByTagName("Number").item(0).getNodeValue()
        );
    }
}
