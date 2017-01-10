package gr.uoi.cse.taxcalc.gui.dialogs;

import gr.uoi.cse.taxcalc.data.Taxpayer;

import javax.swing.*;

public class NewReceiptDialog extends JDialog {
    Taxpayer loadedTaxpayer;

    public NewReceiptDialog(Taxpayer taxpayer) {
        setResizable(false);
        setBounds(100, 100, 310, 530);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setType(Type.POPUP);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Εισαγωγή νέας απόδειξης");

        loadedTaxpayer = taxpayer;
    }
}
