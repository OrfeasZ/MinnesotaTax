package gr.uoi.cse.taxcalc.io.deserializers;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Deserializer {
    Taxpayer deserializeFile(String path)
            throws IOException, ParserConfigurationException, SAXException;

    Taxpayer deserializeData(String data)
            throws ParserConfigurationException, IOException, SAXException;
}
