package gr.uoi.cse.taxcalc.io;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.io.serializers.Serializer;
import gr.uoi.cse.taxcalc.io.serializers.TxtSerializer;
import gr.uoi.cse.taxcalc.io.serializers.XmlSerializer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;

public class OutputSystem {
    public static void exportTaxpayerFull(Taxpayer taxpayer, String folder, ExportFormat format) throws IOException, TransformerException, ParserConfigurationException {
        Serializer serializer = createSerializer(format);

        Writer writer = new FileWriter(Paths.get(folder, taxpayer.getAFM() + "_INFO." + serializer.getExtension()).toFile());
        serializer.serializeFull(taxpayer, writer);
    }

    public static void exportTaxpayerInfo(Taxpayer taxpayer, String folder, ExportFormat format) throws IOException, TransformerException, ParserConfigurationException {
        Serializer serializer = createSerializer(format);

        Writer writer = new FileWriter(Paths.get(folder, taxpayer.getAFM() + "_LOG." + serializer.getExtension()).toFile());
        serializer.serializeInfo(taxpayer, writer);
    }

    private static Serializer createSerializer(ExportFormat format) {
        switch (format) {
            case TXT:
                return new TxtSerializer();

            case XML:
                return new XmlSerializer();
        }

        throw new IllegalArgumentException();
    }
}
