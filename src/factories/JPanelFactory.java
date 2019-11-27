package factories;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JPanelFactory {

    private final JPanel panel;
    
    private JPanelFactory(LayoutManager layout) {
        panel = new JPanel(layout);
    }
    
    private JPanelFactory() {
        this(null);
    }
    
    public static JPanelFactory createDefaultPanel() {
        return createDefaultPanel(null);
    }

    public static JPanelFactory createDefaultPanel(LayoutManager layout) {
        return new JPanelFactory(layout).setBackground(Color.WHITE);
    }
    
    public static JPanelFactory createDefaultMigPanel(String layout) {
        return createDefaultMigPanel(layout, null);
    }
    
    public static JPanelFactory createDefaultMigPanel(String layout, String column) {
        return createDefaultMigPanel(layout, column, null);
    }
    
    public static JPanelFactory createDefaultMigPanel(String layout, String column, String row) {
        return createDefaultPanel(new MigLayout(layout, column, row));
    }

    public JPanelFactory setBorder(Border border) {
        panel.setBorder(border);
        return this;
    }

    public JPanelFactory setBackground(Color color) {
        panel.setBackground(color);
        return this;
    }

    public JPanelFactory setSize(Dimension dimension) {
        panel.setSize(dimension);
        return this;
    }

    public JPanelFactory add(Component comp) {
        panel.add(comp);
        return this;
    }

    public JPanelFactory add(Component comp, String str) {
        panel.add(comp, str);
        return this;
    }
    
    public JPanel build() {
        return panel;
    }
}
