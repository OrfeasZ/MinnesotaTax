package gr.uoi.cse.taxcalc.gui.dialogs;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;

public class ReceiptManagementDialog extends JDialog {
    private Taxpayer loadedTaxpayer;
    private JList<String> receiptList;
    private JButton addReceiptButton;
    private JButton deleteReceiptButton;
    private JButton showReceiptDetailsButton;


    public ReceiptManagementDialog(Taxpayer taxpayer) {
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

    }

    private void populateReceipts() {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Receipt receipt : loadedTaxpayer.getReceipts()) {
            listModel.addElement(receipt.getId() + " | " + receipt.getDate() + " | " + receipt.getAmount());
        }

        receiptList.setModel(listModel);
    }
}
