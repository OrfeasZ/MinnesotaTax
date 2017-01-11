package gr.uoi.cse.taxcalc.io;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.io.serializers.Serializer;
import gr.uoi.cse.taxcalc.io.serializers.TxtSerializer;
import gr.uoi.cse.taxcalc.io.serializers.XmlSerializer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;

public class OutputSystem {
    public static void exportTaxpayerFull(final Taxpayer taxpayer,
                                          final String folder)
            throws IOException,
            TransformerException,
            ParserConfigurationException {
        exportTaxpayerFull(taxpayer, folder, ExportFormat.TXT);
        exportTaxpayerFull(taxpayer, folder, ExportFormat.XML);
    }

    public static void exportTaxpayerFull(final Taxpayer taxpayer,
                                          final String folder,
                                          final ExportFormat format)
            throws IOException,
            TransformerException,
            ParserConfigurationException {
        Serializer serializer = createSerializer(format);
        File outFile = Paths.get(folder, taxpayer.getAFM()
                + "_INFO." + serializer.getExtension()).toFile();

        if (!outFile.exists()) {
            return;
        }

        Writer writer = new FileWriter(outFile);
        serializer.serializeFull(taxpayer, writer);
        writer.close();
    }

    public static void exportTaxpayerInfo(final Taxpayer taxpayer,
                                          final String folder,
                                          final ExportFormat format)
            throws IOException,
            TransformerException,
            ParserConfigurationException {
        Serializer serializer = createSerializer(format);

        Writer writer = new FileWriter(
                Paths.get(folder,
                        taxpayer.getAFM() + "_LOG."
                                + serializer.getExtension()).toFile()
        );

        serializer.serializeInfo(taxpayer, writer);
        writer.close();
    }

    private static Serializer createSerializer(final ExportFormat format) {
        switch (format) {
            case TXT:
                return new TxtSerializer();

            case XML:
                return new XmlSerializer();

            default:
                throw new IllegalArgumentException();
        }
    }
}
