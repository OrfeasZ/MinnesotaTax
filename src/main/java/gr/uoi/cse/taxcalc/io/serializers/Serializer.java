package gr.uoi.cse.taxcalc.io.serializers;

import gr.uoi.cse.taxcalc.data.Taxpayer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class Serializer {
    abstract void serialize(Taxpayer taxpayer, Writer writer) throws ParserConfigurationException, TransformerException;

    public String serialize(Taxpayer taxpayer) throws TransformerException, ParserConfigurationException {
        StringWriter writer = new StringWriter();
        serialize(taxpayer, writer);
        return writer.getBuffer().toString();
    }
}
