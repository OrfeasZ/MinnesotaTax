package gr.uoi.cse.taxcalc;

import gr.uoi.cse.taxcalc.gui.frames.MainFrame;

import java.awt.EventQueue;

public class Entry {
    public static void main(final String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
