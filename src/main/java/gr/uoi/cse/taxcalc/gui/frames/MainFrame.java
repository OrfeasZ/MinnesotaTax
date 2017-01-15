package gr.uoi.cse.taxcalc.gui.frames;

import gr.uoi.cse.taxcalc.data.Database;
import gr.uoi.cse.taxcalc.util.GUIUtils;
import gr.uoi.cse.taxcalc.gui.dialogs.DataImportDialog;
import gr.uoi.cse.taxcalc.gui.dialogs.TaxpayersDialog;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private JLabel taxpayerCountLabel;
    private JButton loadDataButton;
    private JButton showTaxpayersButton;

    public MainFrame() {
        setResizable(false);
        setTitle("Διαχείρηση φορολογίας");
        setBounds(-1, -1, 357, 228);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        initElements();
        initHandlers();
    }

    private void initElements() {
        GUIUtils.addLabel(getContentPane(), "Συν. αριθμός φορολογούμενων:", Color.BLUE, new Rectangle(30, 11, 218, 33), new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
        taxpayerCountLabel = GUIUtils.addLabel(getContentPane(), "0", Color.RED, new Rectangle(247, 20, 75, 14), new Font("Tahoma", Font.BOLD, 14), SwingConstants.LEFT);
        GUIUtils.addSeparator(getContentPane(), new Rectangle(29, 42, 293, 2));
        loadDataButton = GUIUtils.addButton(getContentPane(), "Φόρτωση δεδομένων φορολογούμενου (-ων)", new Rectangle(27, 55, 295, 53), new Font("Tahoma", Font.BOLD, 11));
        showTaxpayersButton = GUIUtils.addButton(getContentPane(), "Εμφάνιση λίστας φορολογουμένων", new Rectangle(27, 121, 295, 53), new Font("Tahoma", Font.BOLD, 11), false);
    }

    private void initHandlers() {
        setLoadDataHandler();
        setShowTaxpayersHandler();
    }

    private void setLoadDataHandler() {
        loadDataButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Επιλέξτε τον φάκελο που περιέχει τα <AFM>_INFO.* αρχεία");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                DataImportDialog dataImportDialog = new DataImportDialog(chooser.getSelectedFile().toString());

                dataImportDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(final WindowEvent e) {
                        updateTaxpayerData();
                    }
                });

                dataImportDialog.setVisible(true);
            }
        });
    }

    private void setShowTaxpayersHandler() {
        showTaxpayersButton.addActionListener(e -> {
            TaxpayersDialog taxpayersDialog = new TaxpayersDialog();

            taxpayersDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(final WindowEvent e) {
                    updateTaxpayerData();
                }
            });

            taxpayersDialog.setVisible(true);
        });
    }

    private void updateTaxpayerData() {
        taxpayerCountLabel.setText(String.valueOf(Database.getTaxpayerCount()));
        showTaxpayersButton.setEnabled(Database.getTaxpayerCount() > 0);
    }
}
