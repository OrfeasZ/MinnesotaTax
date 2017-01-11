package gr.uoi.cse.taxcalc.gui.dialogs;

import gr.uoi.cse.taxcalc.gui.GUIUtils;
import gr.uoi.cse.taxcalc.io.InputSystem;
import org.xml.sax.SAXException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataImportDialog extends JDialog {
    private JList<String> fileList;
    private JButton loadDataButton;
    private JButton selectAllButton;
    private String folderPath;

    public DataImportDialog(final String path) {
        setResizable(false);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setType(Type.POPUP);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 486, 332);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Αρχεία φόρτωσης δεδομένων");

        folderPath = path;

        initElements();
        initHandlers();
        populateFiles();
    }

    private void initElements() {
        JScrollPane scrollPane = GUIUtils.addScrollPane(getContentPane(),
                new Rectangle(10, 11, 250, 258));

        fileList = new JList<>();
        fileList.setForeground(Color.BLUE);
        fileList.setFont(new Font("Tahoma", Font.BOLD, 11));
        fileList.setVisibleRowCount(100);
        scrollPane.setViewportView(fileList);

        loadDataButton = GUIUtils.addButton(getContentPane(),
                "<html>Φόρτωση δεδομένων<br>" +
                        "επιλεγμένων αρχείων</html>",
                new Rectangle(270, 11, 198, 68)
        );

        selectAllButton = GUIUtils.addButton(getContentPane(),
                "Επιλογή όλων",
                new Rectangle(10, 274, 250, 23)
        );
    }

    private void initHandlers() {
        setLoadDataHandler();
        setSelectAllHandler();
    }

    private void setLoadDataHandler() {
        loadDataButton.addActionListener(e -> {
            List<String> filesToLoad = fileList.getSelectedValuesList();

            if (filesToLoad.size() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Δεν έχεις επιλέξει αρχείο(α) απο την λίστα.",
                        "Σφάλμα",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String confirmDialogText = "Φόρτωση δεδομένων φορολογούμενων απο τα ακόλουθα αρχεία:\n";

            for (String afmInfoFileName : filesToLoad) {
                confirmDialogText += afmInfoFileName + "\n";
            }

            confirmDialogText += "Είστε σίγουρος?";

            int dialogResult = JOptionPane.showConfirmDialog(null,
                    confirmDialogText,
                    "Επιβεβαίωση",
                    JOptionPane.YES_NO_OPTION
            );

            if (dialogResult == JOptionPane.YES_OPTION) {
                try {
                    InputSystem.importTaxpayers(folderPath, filesToLoad);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Παρουσιάστικε σφάλμα κατά την ανάγνωση των αρχείων.",
                            "Σφάλμα",
                            JOptionPane.WARNING_MESSAGE
                    );
                } catch (SAXException | ParserConfigurationException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Παρουσιάστικε σφάλμα κατά την αποκοδικοποίηση των αρχείων.",
                            "Σφάλμα",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                dispose();
            }
        });
    }

    private void setSelectAllHandler() {
        selectAllButton.addActionListener(e ->
                fileList.setSelectionInterval(0, fileList.getModel().getSize() - 1)
        );
    }

    private void populateFiles() {
        File folder = new File(folderPath);
        File[] folderFiles = folder.listFiles((dir, name) ->
                (name.toLowerCase().endsWith("_info.txt") || name.toLowerCase().endsWith("_info.xml"))
        );

        DefaultListModel<String> listModel = new DefaultListModel<>();

        if (folderFiles != null) {
            for (File file : folderFiles) {
                if (file.isFile()) {
                    listModel.addElement(file.getName());
                }
            }
        }

        fileList.setModel(listModel);
    }
}
