package gr.uoi.cse.taxcalc.gui;

import javax.swing.*;
import java.awt.*;

public class GUIUtils {
    private static Font defaultFont = new Font("Tahoma", Font.BOLD, 12);

    public static JLabel addLabel(Container container, String text, Color color, Rectangle bounds) {
        return addLabel(container, text, color, bounds, defaultFont, SwingConstants.LEADING);
    }

    public static JLabel addLabel(Container container, String text, Color color, Rectangle bounds, int alignment) {
        return addLabel(container, text, color, bounds, defaultFont, alignment);
    }

    public static JLabel addLabel(Container container, String text, Color color, Rectangle bounds, Font font) {
        return addLabel(container, text, color, bounds, font, SwingConstants.LEADING);
    }

    public static JLabel addLabel(Container container, String text, Color color, Rectangle bounds, Font font, int alignment) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
        label.setForeground(color);
        label.setFont(font);
        label.setBounds(bounds);
        container.add(label);

        return label;
    }

    public static JSeparator addSeparator(Container container, Rectangle bounds) {
        JSeparator separator = new JSeparator();
        separator.setBounds(bounds);
        container.add(separator);

        return separator;
    }

    public static JPanel addPanel(Container container, Rectangle bounds) {
        JPanel panel = new JPanel();
        panel.setBounds(bounds);
        container.add(panel);

        return panel;
    }

    public static JButton addButton(Container container, String text, Rectangle bounds) {
        return addButton(container, text, bounds, defaultFont, text, true);
    }

    public static JButton addButton(Container container, String text, Rectangle bounds, Font font) {
        return addButton(container, text, bounds, font, text, true);
    }

    public static JButton addButton(Container container, String text, Rectangle bounds, boolean enabled) {
        return addButton(container, text, bounds, defaultFont, text, enabled);
    }

    public static JButton addButton(Container container, String text, Rectangle bounds, String action) {
        return addButton(container, text, bounds, defaultFont, action, true);
    }

    public static JButton addButton(Container container, String text, Rectangle bounds, Font font, String action) {
        return addButton(container, text, bounds, font, action, true);
    }

    public static JButton addButton(Container container, String text, Rectangle bounds, Font font, boolean enabled) {
        return addButton(container, text, bounds, font, text, enabled);
    }

    public static JButton addButton(Container container, String text, Rectangle bounds, String action, boolean enabled) {
        return addButton(container, text, bounds, defaultFont, action, enabled);
    }

    public static JButton addButton(Container container, String text, Rectangle bounds, Font font, String action, boolean enabled) {
        JButton button = new JButton(text);
        button.setEnabled(enabled);
        button.setFont(font);
        button.setBounds(bounds);
        button.setActionCommand(action);
        container.add(button);

        return button;
    }

    public static JComboBox addComboBox(Container container, ComboBoxModel model, Rectangle bounds) {
        return addComboBox(container, model, bounds, defaultFont);
    }

    public static JComboBox addComboBox(Container container, ComboBoxModel model, Rectangle bounds, Font font) {
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(model);
        comboBox.setFont(font);
        comboBox.setBounds(bounds);
        container.add(comboBox);

        return comboBox;
    }

    public static JTextField addTextField(Container container, int columns, Rectangle bounds) {
        return addTextField(container, columns, bounds, defaultFont, SwingConstants.LEADING);
    }

    public static JTextField addTextField(Container container, int columns, Rectangle bounds, Font font) {
        return addTextField(container, columns, bounds, font, SwingConstants.LEADING);
    }

    public static JTextField addTextField(Container container, int columns, Rectangle bounds, int alignment) {
        return addTextField(container, columns, bounds, defaultFont, alignment);
    }

    public static JTextField addTextField(Container container, int columns, Rectangle bounds, Font font, int alignment) {
       JTextField textField = new JTextField();
       textField.setColumns(columns);
       textField.setBounds(bounds);
       textField.setFont(font);
       textField.setHorizontalAlignment(alignment);
       container.add(textField);

       return textField;
    }
}
