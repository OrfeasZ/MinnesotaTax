package gr.uoi.cse.taxcalc.gui.frames;

import gr.uoi.cse.taxcalc.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;

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

        initFrame();
    }

    private void initFrame() {
        GUIUtils.addLabel(getContentPane(), "Συν. αριθμός φορολογούμενων:", Color.BLUE, new Rectangle(30, 11, 218, 33), new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
        taxpayerCountLabel = GUIUtils.addLabel(getContentPane(), "0", Color.RED, new Rectangle(247, 20, 75, 14), new Font("Tahoma", Font.BOLD, 14), SwingConstants.LEFT);
        GUIUtils.addSeparator(getContentPane(), new Rectangle(29, 42, 293, 2));
        loadDataButton = GUIUtils.addButton(getContentPane(), "Φόρτωση δεδομένων φορολογούμενου (-ων)", new Rectangle(27, 55, 295, 53), new Font("Tahoma", Font.BOLD, 11));
        showTaxpayersButton = GUIUtils.addButton(getContentPane(), "Εμφάνιση λίστας φορολογουμένων", new Rectangle(27, 121, 295, 53), new Font("Tahoma", Font.BOLD, 11), false);
    }
}
