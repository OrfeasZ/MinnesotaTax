package gr.uoi.cse.taxcalc.io.serializers;

import gr.uoi.cse.taxcalc.data.Taxpayer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class Serializer {
    public abstract String getExtension();
    public abstract void serializeFull(Taxpayer taxpayer, Writer writer) throws ParserConfigurationException, TransformerException, IOException;
    public abstract void serializeInfo(Taxpayer taxpayer, Writer writer) throws ParserConfigurationException, TransformerException, IOException;

    public String serializeFull(Taxpayer taxpayer) throws TransformerException, ParserConfigurationException, IOException {
        StringWriter writer = new StringWriter();
        serializeFull(taxpayer, writer);
        return writer.getBuffer().toString();
    }

    public String serializeInfo(Taxpayer taxpayer) throws TransformerException, ParserConfigurationException, IOException {
        StringWriter writer = new StringWriter();
        serializeInfo(taxpayer, writer);
        return writer.getBuffer().toString();
    }
}
