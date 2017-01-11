package gr.uoi.cse.taxcalc.gui.dialogs;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.gui.GUIUtils;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ReceiptManagementDialog extends JDialog {
    private Taxpayer loadedTaxpayer;

    private JList<String> receiptList;
    private JButton addReceiptButton;
    private JButton deleteReceiptButton;
    private JButton showReceiptDetailsButton;

    ReceiptManagementDialog(final Taxpayer taxpayer) {
        setResizable(false);
        setBounds(100, 100, 480, 460);
        getContentPane().setLayout(null);
        setTitle(taxpayer.getName() + " | " + taxpayer.getAFM());
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setType(Type.POPUP);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        loadedTaxpayer = taxpayer;

        initElements();
        initHandlers();
        populateReceipts();
    }

    private void initElements() {
        JScrollPane scrollPane = GUIUtils.addScrollPane(getContentPane(), new Rectangle(10, 38, 250, 384));

        receiptList = new JList<>();
        receiptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receiptList.setForeground(Color.BLUE);
        receiptList.setFont(new Font("Tahoma", Font.BOLD, 13));
        scrollPane.setViewportView(receiptList);

        GUIUtils.addLabel(getContentPane(), "Αποδείξεις Φορολογούμενου", Color.RED, new Rectangle(10, 11, 250, 22), new Font("Tahoma", Font.BOLD, 14), SwingConstants.CENTER);

        addReceiptButton = GUIUtils.addButton(getContentPane(), "Εισαγωγή νέας απόδειξης", new Rectangle(270, 114, 194, 65));
        deleteReceiptButton = GUIUtils.addButton(getContentPane(), "<html>Διαγραφή επιλεγμένης<br>απόδειξης</html>", new Rectangle(270, 190, 194, 65));
        showReceiptDetailsButton = GUIUtils.addButton(getContentPane(), "<html>Εμφάνιση πληροφοριών<br>επιλεγμένης απόδειξης</html>", new Rectangle(270, 38, 194, 65));
    }

    private void initHandlers() {
        setAddReceiptHandler();
        setDeleteReceiptHandler();
        setShowReceiptDetailsHandler();
    }

    private void setAddReceiptHandler() {
        addReceiptButton.addActionListener(arg0 -> {
            NewReceiptDialog newReceiptDialog = new NewReceiptDialog(loadedTaxpayer);

            newReceiptDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    populateReceipts();
                }
            });

            newReceiptDialog.setVisible(true);
        });
    }

    private void setDeleteReceiptHandler() {
        deleteReceiptButton.addActionListener(e -> {
            if (receiptList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποια απόδειξη απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Διαγραφή επιλεγμένης απόδειξης (" + receiptList.getSelectedValue() + ") ?", "Επιβεβαίωση διαγραφής", JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {
                loadedTaxpayer.removeReceiptByIndex(receiptList.getSelectedIndex());
                populateReceipts();
            }
        });
    }

    private void setShowReceiptDetailsHandler() {
        showReceiptDetailsButton.addActionListener(arg0 -> {
            if (receiptList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποια απόδειξη απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, loadedTaxpayer.getReceipt(receiptList.getSelectedIndex()).toString(), receiptList.getSelectedValue(), JOptionPane.PLAIN_MESSAGE);
        });
    }

    private void populateReceipts() {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Receipt receipt : loadedTaxpayer.getReceipts()) {
            listModel.addElement(receipt.getId() + " | " + receipt.getDate() + " | " + receipt.getAmount());
        }

        receiptList.setModel(listModel);
    }
}
