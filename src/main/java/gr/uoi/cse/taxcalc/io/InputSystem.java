package gr.uoi.cse.taxcalc.io;

import gr.uoi.cse.taxcalc.data.Database;
import gr.uoi.cse.taxcalc.io.deserializers.Deserializer;
import gr.uoi.cse.taxcalc.io.deserializers.XmlDeserializer;
import gr.uoi.cse.taxcalc.io.deserializers.TxtDeserializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class InputSystem {
    public static void importTaxpayers(String folderPath, List<String> files) throws ParserConfigurationException, SAXException, IOException {
        Database.setDataFolder(folderPath);

        for (String file : files) {
            Deserializer deserializer = getDeserializer(file);
            Database.addTaxpayer(deserializer.deserializeFile(Paths.get(folderPath, file).toString()));
        }
    }

    private static Deserializer getDeserializer(String file) throws IllegalArgumentException {
        if (file.endsWith(".txt")) {
            return new TxtDeserializer();
        }

        if (file.endsWith(".xml")) {
            return new XmlDeserializer();
        }

        throw new IllegalArgumentException("Could not find a suitable deserializer for file '" + file + "'.");
    }
}
