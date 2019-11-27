package factories;

import javax.swing.*;

public class JMenuFactory {

    private final JMenu menu;

    private JMenuFactory() {
        menu = new JMenu();
    }
    
    private JMenuFactory(String name) {
        menu = new JMenu(name);
    }

    public static JMenuFactory createDefaultMenu(String name) {
        return createMenu(name);
    }

    public static JMenuFactory createMenu(String name) {
        return new JMenuFactory(name).setOpaque(false);
    }
    
    public static JMenuFactory createDefaultMenu() {
        return createMenu();
    }

    public static JMenuFactory createMenu() {
        return new JMenuFactory();
    }
    
    public JMenuFactory add(JMenuItem menuItem) {
        menu.add(menuItem);
        return this;
    }
    
    public JMenuFactory addSeparator() {
        menu.addSeparator();
        return this;
    }

    public JMenuFactory setIcon(Icon icon) {
        menu.setIcon(icon);
        return this;
    }
    
    public JMenuFactory setOpaque(boolean opaque) {
        menu.setOpaque(opaque);
        return this;
    }

    public JMenu build() {
        return menu;
    }
}
