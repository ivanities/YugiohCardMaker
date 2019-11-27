package factories;

import javax.swing.*;
import java.awt.event.ActionListener;

public class JMenuItemFactory {

    private final JMenuItem menuItem;
    
    public static JMenuItemFactory createMenuItem() {
        return createMenuItem((String) null);
    }
    
    public static JMenuItemFactory createMenuItem(String text) {
        return createMenuItem(text, null);
    }
    
    public static JMenuItemFactory createMenuItem(Icon icon) {
        return createMenuItem(null, icon);
    }
    
    public static JMenuItemFactory createMenuItem(String text, Icon icon) {
        return new JMenuItemFactory(text, icon);
    }
    
    public static JMenuItemFactory createMenuItem(Action action) {
        return new JMenuItemFactory(null, null, action);
    }
    
    private JMenuItemFactory(String text, Icon icon) {
        menuItem = new JMenuItem(text, icon);
    }
    
    private JMenuItemFactory(String text, Icon icon, Action action) {
        menuItem = new JMenuItem(text, icon);
        menuItem.setAction(action);
    }
    
    public JMenuItemFactory setText(String text) {
        menuItem.setText(text);
        return this;
    }

    public JMenuItemFactory setIcon(Icon icon) {
        menuItem.setIcon(icon);
        return this;
    }
    
    public JMenuItemFactory setAction(Action action) {
        menuItem.setAction(action);
        return this;
    }

    public JMenuItemFactory addActionListener(ActionListener actionListener) {
        menuItem.addActionListener(actionListener);
        return this;
    }

    public JMenuItem build() {
        return menuItem;
    }
}
