package gr.uoi.cse.taxcalc.gui.dialogs;

import gr.uoi.cse.taxcalc.data.Database;
import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;
import gr.uoi.cse.taxcalc.util.GUIUtils;
import gr.uoi.cse.taxcalc.io.ExportFormat;
import gr.uoi.cse.taxcalc.io.OutputSystem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

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

            Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerList.getSelectedIndex());

            ReceiptManagementDialog receiptManagementDialog = new ReceiptManagementDialog(taxpayer);

            // Set a handler to save the taxpayer data when the receipts dialog gets closed.
            receiptManagementDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        OutputSystem.exportTaxpayerFull(taxpayer, Database.getDataFolder());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατα την αποθήκευση του αρχείου. Οι αλλαγές σας δεν αποθηκεύτηκαν.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            receiptManagementDialog.setVisible(true);
        });
    }

    private void setShowPieChartHandler() {
        showPieChartButton.addActionListener(arg0 -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerList.getSelectedIndex());
            showTaxpayerPieChart(taxpayer);
        });
    }

    private void setShowBarChartHandler() {
        showBarChartButton.addActionListener(arg0 -> {
            if (taxpayerList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Taxpayer taxpayer = Database.getTaxpayerByIndex(taxpayerList.getSelectedIndex());
            showTaxpayerBarChart(taxpayer);
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
                    JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατα την αποθήκευση του αρχείου.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατα την αποθήκευση του αρχείου.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
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

    private void showTaxpayerPieChart(final Taxpayer taxpayer) {
        DefaultPieDataset pieChartDataset = new DefaultPieDataset();

        pieChartDataset.setValue("Basic", taxpayer.getReceiptsTotalAmount(ReceiptKind.BASIC));
        pieChartDataset.setValue("Entertainment", taxpayer.getReceiptsTotalAmount(ReceiptKind.ENTERTAINMENT));
        pieChartDataset.setValue("Travel", taxpayer.getReceiptsTotalAmount(ReceiptKind.TRAVEL));
        pieChartDataset.setValue("Health", taxpayer.getReceiptsTotalAmount(ReceiptKind.HEALTH));
        pieChartDataset.setValue("Other", taxpayer.getReceiptsTotalAmount(ReceiptKind.OTHER));

        JFreeChart pieChart = ChartFactory.createPieChart("Receipt Pie Chart", pieChartDataset);
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}: {1}$ ({2})", new DecimalFormat("0.00"), new DecimalFormat("0.00%"));
        piePlot.setLabelGenerator(generator);

        showChartFrame(taxpayer.getName() + " | " + taxpayer.getAFM(), pieChart);
    }

    private void showTaxpayerBarChart(final Taxpayer taxpayer) {
        DefaultCategoryDataset barChartDataset = new DefaultCategoryDataset();

        String taxVariationType = taxpayer.getTaxIncrease() != 0 ? "Tax Increase" : "Tax Decrease";
        double taxVariationAmount = taxpayer.getTaxIncrease() != 0 ? taxpayer.getTaxIncrease() : taxpayer.getTaxDecrease() * (-1);

        barChartDataset.setValue(taxpayer.getBasicTax(), "Tax", "Basic Tax");
        barChartDataset.setValue(taxVariationAmount, "Tax", taxVariationType);
        barChartDataset.setValue(taxpayer.getTotalTax(), "Tax", "Total Tax");

        JFreeChart barChart = ChartFactory.createBarChart("Tax Analysis Bar Chart", "", "Tax Analysis in $", barChartDataset, PlotOrientation.VERTICAL, true, true, false);
        showChartFrame(taxpayer.getName() + " | " + taxpayer.getAFM(), barChart);
    }

    private void showChartFrame(final String title,
                                final JFreeChart chart) {
        ChartFrame chartFrame = new ChartFrame(title, chart);
        chartFrame.pack();
        chartFrame.setResizable(false);
        chartFrame.setLocationRelativeTo(null);
        chartFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        chartFrame.setVisible(true);
    }
}
