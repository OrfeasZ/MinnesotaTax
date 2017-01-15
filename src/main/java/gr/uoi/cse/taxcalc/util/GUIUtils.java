package gr.uoi.cse.taxcalc.util;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;

public class GUIUtils {
    private static Font defaultFont = new Font("Tahoma", Font.BOLD, 12);

    public static JLabel addLabel(final Container container,
                                  final String text,
                                  final Color color,
                                  final Rectangle bounds) {
        return addLabel(
                container,
                text,
                color,
                bounds,
                defaultFont,
                SwingConstants.LEADING
        );
    }

    public static JLabel addLabel(final Container container,
                                  final String text,
                                  final Color color,
                                  final Rectangle bounds,
                                  final int alignment) {
        return addLabel(container, text, color, bounds, defaultFont, alignment);
    }

    public static JLabel addLabel(final Container container,
                                  final String text,
                                  final Color color,
                                  final Rectangle bounds,
                                  final Font font) {
        return addLabel(
                container,
                text,
                color,
                bounds,
                font,
                SwingConstants.LEADING
        );
    }

    public static JLabel addLabel(final Container container,
                                  final String text,
                                  final Color color,
                                  final Rectangle bounds,
                                  final Font font,
                                  final int alignment) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
        label.setForeground(color);
        label.setFont(font);
        label.setBounds(bounds);
        container.add(label);

        return label;
    }

    public static JSeparator addSeparator(final Container container,
                                          final Rectangle bounds) {
        JSeparator separator = new JSeparator();
        separator.setBounds(bounds);
        container.add(separator);

        return separator;
    }

    public static JPanel addPanel(final Container container,
                                  final Rectangle bounds) {
        JPanel panel = new JPanel();
        panel.setBounds(bounds);
        container.add(panel);

        return panel;
    }

    public static JScrollPane addScrollPane(final Container container,
                                            final Rectangle bounds) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(bounds);
        container.add(scrollPane);

        return scrollPane;
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds) {
        return addButton(container, text, bounds, defaultFont, text, true);
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds,
                                    final Font font) {
        return addButton(container, text, bounds, font, text, true);
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds,
                                    final boolean enabled) {
        return addButton(container, text, bounds, defaultFont, text, enabled);
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds,
                                    final String action) {
        return addButton(container, text, bounds, defaultFont, action, true);
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds,
                                    final Font font,
                                    final String action) {
        return addButton(container, text, bounds, font, action, true);
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds,
                                    final Font font,
                                    final boolean enabled) {
        return addButton(container, text, bounds, font, text, enabled);
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds,
                                    final String action,
                                    final boolean enabled) {
        return addButton(container, text, bounds, defaultFont, action, enabled);
    }

    public static JButton addButton(final Container container,
                                    final String text,
                                    final Rectangle bounds,
                                    final Font font,
                                    final String action,
                                    final boolean enabled) {
        JButton button = new JButton(text);
        button.setEnabled(enabled);
        button.setFont(font);
        button.setBounds(bounds);
        button.setActionCommand(action);
        container.add(button);

        return button;
    }

    public static JComboBox addComboBox(final Container container,
                                        final ComboBoxModel model,
                                        final Rectangle bounds) {
        return addComboBox(container, model, bounds, defaultFont);
    }

    public static JComboBox addComboBox(final Container container,
                                        final ComboBoxModel model,
                                        final Rectangle bounds,
                                        final Font font) {
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(model);
        comboBox.setFont(font);
        comboBox.setBounds(bounds);
        container.add(comboBox);

        return comboBox;
    }

    public static JTextField addTextField(final Container container,
                                          final int columns,
                                          final Rectangle bounds) {
        return addTextField(
                container,
                columns,
                bounds,
                defaultFont,
                SwingConstants.LEADING
        );
    }

    public static JTextField addTextField(final Container container,
                                          final int columns,
                                          final Rectangle bounds,
                                          final Font font) {
        return addTextField(
                container,
                columns,
                bounds,
                font,
                SwingConstants.LEADING
        );
    }

    public static JTextField addTextField(final Container container,
                                          final int columns,
                                          final Rectangle bounds,
                                          final int alignment) {
        return addTextField(
                container,
                columns,
                bounds,
                defaultFont,
                alignment
        );
    }

    public static JTextField addTextField(final Container container,
                                          final int columns,
                                          final Rectangle bounds,
                                          final Font font,
                                          final int alignment) {
        JTextField textField = new JTextField();
        textField.setColumns(columns);
        textField.setBounds(bounds);
        textField.setFont(font);
        textField.setHorizontalAlignment(alignment);
        container.add(textField);

        return textField;
    }
}
