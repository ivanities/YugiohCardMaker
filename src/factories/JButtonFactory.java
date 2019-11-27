package factories;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class JButtonFactory {

    private final JButton button;

    private JButtonFactory() {
        button = new JButton();
    }
    
    public static JButtonFactory createToolBarButton(Icon icon) {
        return createButton(icon, DimensionFactory.TOOLBAR_BUTTON);
    }
    
    public static JButtonFactory createDefaultButton(Icon icon) {
        return createButton(icon, DimensionFactory.DEFAULT_BUTTON);
    }
    
    public static JButtonFactory createButton(Icon icon, Dimension dimension) {
        return new JButtonFactory()
                .setBackground(null)
                .setSize(dimension)
                .setPreferredSize(dimension)
                .setMinimumSize(dimension)
                .setMaximumSize(dimension)
                .setFocusPainted(false)
                .setIcon(icon);
    }
    
    public JButtonFactory setBorder(Border border) {
        button.setBorder(border);
        return this;
    }

    public JButtonFactory setIcon(Icon icon) {
        button.setIcon(icon);
        return this;
    }

    public JButtonFactory setSize(Dimension dimension) {
        button.setSize(dimension);
        return this;
    }
    
    public JButtonFactory setPreferredSize(Dimension dimension) {
        button.setPreferredSize(dimension);
        return this;
    }
    
    public JButtonFactory setMinimumSize(Dimension dimension) {
        button.setMinimumSize(dimension);
        return this;
    }
    
    public JButtonFactory setMaximumSize(Dimension dimension) {
        button.setMaximumSize(dimension);
        return this;
    }
    
    public JButtonFactory setBackground(Color color) {
        button.setBackground(color);
        return this;
    }

    public JButtonFactory setFocusPainted(boolean focusPainted) {
        button.setFocusPainted(focusPainted);
        return this;
    }

    public JButtonFactory addActionListener(ActionListener l) {
        button.addActionListener(l);
        return this;
    }

    public JButtonFactory setToolTipText(String text) {
        button.setToolTipText(text);
        return this;
    }

    public JButtonFactory setOpaque(boolean isOpaque) {
        button.setOpaque(isOpaque);
        return this;
    }

    public JButton build() {
        return button;
    }
}
