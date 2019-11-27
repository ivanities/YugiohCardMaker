package factories;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * factories.SplitButton class that provides a drop down menu when the right side arrow
 * is clicked.
 * Written by Edward Scholl (edscholl@atwistedweb.com)- use as you wish, but
 * a acknowlegement would be appreciated if you use this...
 * @author Edward Scholl
 */
public class SplitButton extends JButton implements ActionListener{
    private JButton mainButton, dropDownButton;
    private JPopupMenu dropDownMenu;

    /**
     * Default Constructor that creates a blank button with a down facing arrow.
     */
    public SplitButton() {
        this(" ");
    }

    /**
     * Creates a button with the specified text  and a down facing arrow.
     * @param text String
     */
    public SplitButton(String text) {
        this(new JButton(text), SwingConstants.SOUTH);
    }

    /**
     * Creates a button with the specified text
     * and a arrow in the specified direction.
     * @param text String
     * @param orientation int
     */
    public SplitButton(String text, int orientation) {
        this(new JButton(text), orientation);
    }

    /**
     * Passes in the button to use in the left hand side, with the specified
     * orientation for the arrow on the right hand side.
     * @param mainButton JButton
     * @param orientation int
     */
    public SplitButton(JButton mainButton, int orientation) {
        super();
        this.mainButton = mainButton;

        this.dropDownButton  = new BasicArrowButton(orientation);
        dropDownButton.addActionListener(this);

        this.setBorderPainted(false);
        this.dropDownButton.setBorderPainted(false);
        this.mainButton.setBorderPainted(false);

        this.setPreferredSize(new Dimension(75, 34));
        this.setMaximumSize(new Dimension(75, 34));
        this.setMinimumSize(new Dimension(200, 34));

        this.setLayout(new BorderLayout());
        this.setMargin(new Insets(-3, -3,-3,-3));

        this.add(mainButton, BorderLayout.CENTER);
        this.add(dropDownButton, BorderLayout.EAST);
    }

    /**
     * Sets the popup menu to show when the arrow is clicked.
     * @param menu JPopupMenu
     */
    public void setMenu(JPopupMenu menu) {
        this.dropDownMenu = menu;
    }

    /**
     * returns the main (left hand side) button.
     * @return JButton
     */
    public JButton getMainButton() {
        return mainButton;
    }

    /**
     * gets the drop down button (with the arrow)
     * @return JButton
     */
    public JButton getDropDownButton() {
        return dropDownButton;
    }

    /**
     * gets the drop down menu
     * @return JPopupMenu
     */
    public JPopupMenu getMenu() {
        return dropDownMenu;
    }

    /**
     * action listener for the arrow button- shows / hides the popup menu.
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        if(this.dropDownMenu == null){
            return;
        }
        if(!dropDownMenu.isVisible()){
            Point p = this.getLocationOnScreen();
            dropDownMenu.setLocation( (int) p.getX(),
                    (int) p.getY() + this.getHeight());
            dropDownMenu.setVisible(true);
        }else{
            dropDownMenu.setVisible(false);
        }
    }

    /**
     * adds a action listener to this button (actually to the left hand side
     * button, and any left over surrounding space.  the arrow button will not
     * be affected.
     * @param al ActionListener
     */
    public void addActionListener(ActionListener al){
        this.mainButton.addActionListener(al);
        this.addActionListener(al);
    }

    public static void test(){
        JFrame frame = new JFrame("Simple Split Button Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar("tb");
        JButton sbButton = new JButton("sb");
//        sbButton.setBackground(Color.BLACK);
//        sbButton.setContentAreaFilled(false);
        SplitButton sb = new SplitButton(sbButton, SwingConstants.SOUTH);

        toolBar.add(new JButton("test button"));
        toolBar.add(sb);


        p.add(new JLabel("factories.SplitButton test"), BorderLayout.CENTER);

        JPopupMenu testMenu = new JPopupMenu("test menu");
        testMenu.add(addMI("menuItem1"));
        testMenu.add(addMI("menuItem2"));
        sb.setMenu(testMenu);

        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        frame.getContentPane().add(p, BorderLayout.CENTER);

        frame.setSize(200, 100);
        frame.show();

    }

    private static JMenuItem addMI(String text){
        JMenuItem mi = new JMenuItem(text);
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
            }
        });

        return mi;
    }

    public static void main(String[] args){
        //<editor-fold defaultState="collapsed" desc=" Look and feel setting code (Windows) ">
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SplitButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        test();
    }
}
