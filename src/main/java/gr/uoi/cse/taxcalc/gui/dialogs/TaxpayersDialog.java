package gr.uoi.cse.taxcalc.gui.dialogs;

import gr.uoi.cse.taxcalc.data.Database;
import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.gui.GUIUtils;
import gr.uoi.cse.taxcalc.io.ExportFormat;
import gr.uoi.cse.taxcalc.io.OutputSystem;

import javax.swing.*;
import java.awt.*;

public class TaxpayersDialog extends JDialog {
    private JList<String> taxpayerList;
    private JButton showTaxpayerInfoButton;
    private JButton deleteTaxpayerButton;
    private JButton showTaxpayerReceiptsButton;
    private JButton showPieChartButton;
    private JButton showBarChartButton;
    private JButton saveInfoTxtButton;
    private JButton saveInfoXmlButton;

    public TaxpayersDialog() {
        setResizable(false);
        setBounds(100, 100, 556, 525);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setType(Type.POPUP);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Φορολογούμενοι");

        initElements();
        initHandlers();
        populateTaxpayers();
    }

    private void initElements() {
        JScrollPane scrollPane = GUIUtils.addScrollPane(getContentPane(), new Rectangle(10, 11, 250, 400));

        taxpayerList = new JList<>();
        taxpayerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taxpayerList.setForeground(Color.BLUE);
        taxpayerList.setFont(new Font("Tahoma", Font.BOLD, 14));
        taxpayerList.setVisibleRowCount(100);
        scrollPane.setViewportView(taxpayerList);

        showTaxpayerInfoButton = GUIUtils.addButton(getContentPane(), "<html>Εμφάνιση στοιχείων<br>επιλεγμένου φορολογούμενου</html>", new Rectangle(300, 12, 240, 71));
        deleteTaxpayerButton = GUIUtils.addButton(getContentPane(), "<html>Διαγραφή επιλεγμένου<br>φορολογούμενου</html>", new Rectangle(300, 93, 240, 71));
        showTaxpayerReceiptsButton = GUIUtils.addButton(getContentPane(), "<html>Εμφάνιση αποδείξεων<br>επιλεγμένου φορολογούμενου</html>", new Rectangle(300, 175, 240, 71));
        showPieChartButton = GUIUtils.addButton(getContentPane(), "Διάγραμμα πίτας αποδείξεων", new Rectangle(300, 257, 240, 71));
        showBarChartButton = GUIUtils.addButton(getContentPane(), "Ραβδόγραμμα ανάλυσης φόρου", new Rectangle(300, 340, 240, 71));
        saveInfoTxtButton = GUIUtils.addButton(getContentPane(), "Αποθήκευση στοιχείων φορολογούμενου σε txt", new Rectangle(10, 422, 530, 29));
        saveInfoXmlButton = GUIUtils.addButton(getContentPane(), "Αποθήκευση στοιχείων φορολογούμενου σε xml", new Rectangle(10, 455, 530, 29));
    }

    private void initHandlers() {
        setShowTaxpayerInfoHandler();
        setDeleteTaxpayerHandler();
        setShowTaxpayerReceiptsHandler();
        setShowPieChartHandler();
        setShowBarChartHandler();
        setSaveInfoTxtHandler();
        setSaveInfoXmlHandler();
    }

    private void setShowTaxpayerInfoHandler() {
        showTaxpayerInfoButton.addActionListener(arg0 -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, Database.getTaxpayerByIndex(taxpayerList.getSelectedIndex()).toString(), taxpayerList.getSelectedValue(), JOptionPane.PLAIN_MESSAGE);
        });
    }

    private void setDeleteTaxpayerHandler() {
        deleteTaxpayerButton.addActionListener(arg0 -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Διαγραφή επιλεγμένου φορολογούμενου (" + taxpayerList.getSelectedValue() + ") απο την βάση δεδομένων?", "Επιβεβαίωση διαγραφής", JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {
                Database.removeTaxpayerByIndex(taxpayerList.getSelectedIndex());

                populateTaxpayers();

                if (Database.getTaxpayerCount() == 0) {
                    dispose();
                }
            }
        });
    }

    private void setShowTaxpayerReceiptsHandler() {
        showTaxpayerReceiptsButton.addActionListener(arg0 -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ReceiptManagementDialog receiptManagementDialog = new ReceiptManagementDialog(Database.getTaxpayerByIndex(taxpayerList.getSelectedIndex()));
            receiptManagementDialog.setVisible(true);
        });
    }

    private void setShowPieChartHandler() {
        showPieChartButton.addActionListener(arg0 -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // TODO
            /*int taxpayerIndex = taxpayerList.getSelectedIndex();
            OutputSystem.createTaxpayerReceiptsPieJFreeChart(taxpayerIndex);*/
        });
    }

    private void setShowBarChartHandler() {
        showBarChartButton.addActionListener(arg0 -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // TODO
            /*int taxpayerIndex = taxpayerList.getSelectedIndex();
            OutputSystem.createTaxpayerTaxAnalysisBarJFreeChart(taxpayerIndex);*/
        });
    }

    private void setSaveInfoTxtHandler() {
        saveInfoTxtButton.addActionListener(e -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int taxpayerIndex = taxpayerList.getSelectedIndex();
            JFileChooser saveFileFolderChooser = new JFileChooser();
            saveFileFolderChooser.setCurrentDirectory(new java.io.File("."));
            saveFileFolderChooser.setDialogTitle("Επιλέξτε φάκελο αποθήκευσης " + Database.getTaxpayerByIndex(taxpayerIndex).getAFM() + "_LOG.txt");
            saveFileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (saveFileFolderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String savePath = saveFileFolderChooser.getSelectedFile().toString();

                try {
                    OutputSystem.exportTaxpayerInfo(Database.getTaxpayerByIndex(taxpayerIndex), savePath, ExportFormat.TXT);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατα την αποθήκευση του αρχείου", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void setSaveInfoXmlHandler() {
        saveInfoXmlButton.addActionListener(e -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int taxpayerIndex = taxpayerList.getSelectedIndex();
            JFileChooser saveFileFolderChooser = new JFileChooser();
            saveFileFolderChooser.setCurrentDirectory(new java.io.File("."));
            saveFileFolderChooser.setDialogTitle("Επιλέξτε φάκελο αποθήκευσης " + Database.getTaxpayerByIndex(taxpayerIndex).getAFM() + "_LOG.xml");
            saveFileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (saveFileFolderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String savePath = saveFileFolderChooser.getSelectedFile().toString();

                try {
                    OutputSystem.exportTaxpayerInfo(Database.getTaxpayerByIndex(taxpayerIndex), savePath, ExportFormat.XML);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατα την αποθήκευση του αρχείου", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void populateTaxpayers() {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Taxpayer taxpayer : Database.getTaxpayers()) {
            listModel.addElement(taxpayer.getName() + " | " + taxpayer.getAFM());
        }

        taxpayerList.setModel(listModel);
    }
}
