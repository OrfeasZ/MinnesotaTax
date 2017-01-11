package gr.uoi.cse.taxcalc.gui.dialogs;

import gr.uoi.cse.taxcalc.data.Taxpayer;
import gr.uoi.cse.taxcalc.data.receipts.Receipt;
import gr.uoi.cse.taxcalc.data.receipts.ReceiptKind;
import gr.uoi.cse.taxcalc.gui.GUIUtils;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class NewReceiptDialog extends JDialog {
    private Taxpayer loadedTaxpayer;

    private JTextField receiptIdField;
    private JTextField dateField;
    private JComboBox kindComboBox;
    private JTextField amountField;
    private JTextField companyField;
    private JTextField countryField;
    private JTextField cityField;
    private JTextField streetField;
    private JTextField numberField;
    private JButton okButton;
    private JButton cancelButton;

    NewReceiptDialog(Taxpayer taxpayer) {
        setResizable(false);
        setBounds(100, 100, 310, 530);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setType(Type.POPUP);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Εισαγωγή νέας απόδειξης");

        loadedTaxpayer = taxpayer;

        initElements();
        initHandlers();
    }

    private void initElements() {
        receiptIdField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 11, 165, 25), SwingConstants.CENTER);
        dateField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 63, 165, 25), SwingConstants.CENTER);
        kindComboBox = GUIUtils.addComboBox(getContentPane(), new DefaultComboBoxModel(ReceiptKind.values()), new Rectangle(119, 115, 165, 25));
        amountField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 167, 165, 25), SwingConstants.CENTER);
        companyField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 219, 165, 25), SwingConstants.CENTER);
        countryField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 271, 165, 25), SwingConstants.CENTER);
        cityField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 323, 165, 25), SwingConstants.CENTER);
        streetField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 375, 165, 25), SwingConstants.CENTER);
        numberField = GUIUtils.addTextField(getContentPane(), 10, new Rectangle(119, 427, 165, 25), SwingConstants.CENTER);

        GUIUtils.addLabel(getContentPane(), "Receipt ID:", Color.BLUE, new Rectangle(20, 11, 99, 25));
        GUIUtils.addLabel(getContentPane(), "Date:", Color.BLUE, new Rectangle(20, 61, 99, 25));
        GUIUtils.addLabel(getContentPane(), "Kind:", Color.BLUE, new Rectangle(20, 113, 99, 25));
        GUIUtils.addLabel(getContentPane(), "Amount:", Color.BLUE, new Rectangle(20, 165, 99, 25));
        GUIUtils.addLabel(getContentPane(), "Company:", Color.BLUE, new Rectangle(20, 217, 99, 25));
        GUIUtils.addLabel(getContentPane(), "Country:", Color.BLUE, new Rectangle(20, 269, 99, 25));
        GUIUtils.addLabel(getContentPane(), "City:", Color.BLUE, new Rectangle(20, 321, 99, 25));
        GUIUtils.addLabel(getContentPane(), "Street:", Color.BLUE, new Rectangle(20, 375, 99, 25));
        GUIUtils.addLabel(getContentPane(), "Number:", Color.BLUE, new Rectangle(20, 427, 99, 25));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(0, 465, 304, 33);
        buttonsPanel.setLayout(null);
        getContentPane().add(buttonsPanel);

        okButton = GUIUtils.addButton(buttonsPanel, "OK", new Rectangle(10, 5, 104, 23));
        cancelButton = GUIUtils.addButton(buttonsPanel, "Cancel", new Rectangle(192, 5, 104, 23));
    }

    private void initHandlers() {
        setAmountHandler();
        setOkHandler();
        setCancelHandler();
    }

    private void setAmountHandler() {
        amountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent keyEvent) {
                char charTyped = keyEvent.getKeyChar();

                if ((!Character.isDigit(charTyped) && charTyped != '.')
                        || (charTyped == '.' && amountField.getText().contains("."))) {
                    keyEvent.consume();
                }
            }
        });
    }

    private void setOkHandler() {
        okButton.addActionListener(e -> {
            if (receiptIdField.getText().isEmpty()
                    || dateField.getText().isEmpty()
                    || amountField.getText().isEmpty()
                    || companyField.getText().isEmpty()
                    || countryField.getText().isEmpty()
                    || cityField.getText().isEmpty()
                    || streetField.getText().isEmpty()
                    || numberField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Υπάρχουν μη συμπληρωμένα πεδία", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Receipt newReceipt = new Receipt(ReceiptKind.getEnum(kindComboBox.getSelectedItem().toString()), receiptIdField.getText(),
                    dateField.getText(), Double.parseDouble(amountField.getText()), companyField.getText(),
                    countryField.getText(), cityField.getText(), streetField.getText(), numberField.getText());

            loadedTaxpayer.addReceipt(newReceipt);
            dispose();
        });
    }

    private void setCancelHandler() {
        cancelButton.addActionListener(e -> dispose());
    }
}
