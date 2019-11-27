package newclasses.toolbar;

import factories.*;
import net.miginfocom.swing.MigLayout;
import newclasses.CardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YugiohMenuBar extends JMenuBar {

    private JButton btnSearch;

    private JMenu menuFile;
    private JMenu menuEdit;
    private JMenu menuView;
    private JMenu menuToolbars;
    private JMenu menuHelp;

    private JMenuItem mitFileView;

    private JTextField txtSearch;

    private JToolBar tbrFile;

    private CardController controller;

    public YugiohMenuBar(CardController controller) {
        this.controller = controller;
        initialise();
    }
 
    private void initialise() {
        setLayout(new MigLayout("insets 0, gap -1", "[][][][][grow][]"));
        add(getMenuFile());
        add(getMenuEdit());
        add(getMenuView());
        add(getMenuHelp());
        add(Box.createHorizontalGlue());
        add(getBtnSearch(), "gaptop 1");
        add(getTxtSearch(), "gapright 1, gaptop 1");
        setBorder(null);
    }
    
    private JMenu getMenuFile() {
        if (menuFile == null) {
            menuFile = JMenuFactory.createDefaultMenu("File")
                    .add(createYgoMenuItem("Open", IconFactory.OPEN))
                    .add(createYgoMenuItem("Open Recent", IconFactory.OPEN))
                    .addSeparator()
                    .add(createYgoMenuItem("Save", IconFactory.SAVE))
                    .add(createYgoMenuItem("Save As...", IconFactory.SAVE))
                    .add(createYgoMenuItem("Save All", IconFactory.SAVE_ALL))
                    .addSeparator()
                    .add(createYgoMenuItem("Print...", IconFactory.PRINT, new PrintListener()))
                    .add(createYgoMenuItem("Print Batch", IconFactory.PRINT_BATCH))
                    .build();
        }
        return menuFile;
    }
    
    private JMenu getMenuEdit() {
        if (menuEdit == null) {
            menuEdit = JMenuFactory.createDefaultMenu("Edit")
                    .add(createYgoMenuItem("Undo"))
                    .add(createYgoMenuItem("Redo"))
                    .add(createYgoMenuItem("Cut"))
                    .add(createYgoMenuItem("Copy"))
                    .add(createYgoMenuItem("Paste"))
                    .add(createYgoMenuItem("Delete"))
                    .build();
        }
        return menuEdit;
    }

    private JMenu getMenuView() {
        if (menuView == null) {
            menuView = JMenuFactory.createDefaultMenu("View")
                    .add(getMenuToolbars())
                    .add(createYgoMenuItem("Show Only Editor"))
                    .add(createYgoMenuItem("Full Screen"))
                    .build();
        }
        return menuView;
    }
    
    private JMenu getMenuToolbars() {
        if (menuToolbars == null) {
            menuToolbars = JMenuFactory.createDefaultMenu("Toolbars")
                    .add(getMitFileView())
                    .add(createYgoMenuItem("Edit"))
                    .add(createYgoMenuItem("Style"))
                    .build();
        }
        return menuToolbars;
    }
    
    private JMenu getMenuHelp() {
        if (menuHelp == null) {
            menuHelp = JMenuFactory.createDefaultMenu("Help")
                    .add(createYgoMenuItem("About"))
                    .build();
        }
        return menuHelp;
    }

    private JButton getBtnSearch() {
        if (btnSearch == null) {
            btnSearch = JButtonFactory.createToolBarButton(IconFactory.SEARCH_CARD)
                    .setMinimumSize(new Dimension(22, 22))
                    .setMaximumSize(new Dimension(22, 22))
                    .setBorder(null)
                    .build();
        }
        btnSearch.setOpaque(true);
        return btnSearch;
    }

    private JTextField getTxtSearch() {
        if (txtSearch == null) {
            txtSearch = JTextFieldFactory.createTextField(new Dimension(285, 20)).build();
        }
        return txtSearch;
    }
    
    private JMenuItem createYgoMenuItem(String name) {
        return createYgoMenuItem(name, null);
    }
    
    private JMenuItem createYgoMenuItem(String text, Icon icon) {
        return createYgoMenuItem(text, icon, null);
    }

    private JMenuItem createYgoMenuItem(String text, Icon icon, ActionListener listener) {
        return JMenuItemFactory.createMenuItem(text, icon)
                .addActionListener(listener)
                .build();
    }

    private void alert(String text) {
        JOptionPane.showMessageDialog(getTopLevelAncestor(), text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JMenuItem getMitFileView() {
        if (mitFileView == null) {
            mitFileView = new JCheckBoxMenuItem("File");
//            mitFileView.addItemListener(e -> {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    parent.getTbrFile().add(getTbrFile());
//                }
//                else {
//                    parent.getTbrFile().remove(getTbrFile());
//                }
//                validate();
//                invalidate();
//                revalidate();
//            });
        }
        return mitFileView;
    }

    private JToolBar getTbrFile() {
        if (tbrFile == null) {
            tbrFile = new JToolBar();
            tbrFile.add(JButtonFactory.createToolBarButton(IconFactory.OPEN).build());
            tbrFile.add(JButtonFactory.createToolBarButton(IconFactory.CREATE_FOLDER).build());
            tbrFile.add(JButtonFactory.createToolBarButton(IconFactory.SAVE).build());
            tbrFile.add(JButtonFactory.createToolBarButton(IconFactory.SAVE_ALL).build());
            tbrFile.add(JButtonFactory.createToolBarButton(IconFactory.PRINT).build());
            tbrFile.add(JButtonFactory.createToolBarButton(IconFactory.PRINT_BATCH).build());
//            tbrFile.addPropertyChangeListener(evt -> tbrFile.setOrientation(parent.getTbrFile().getOrientation()));
        }
        return tbrFile;
    }

    private class PrintListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = controller.getCard().getName();

            if (name == null || name.isEmpty()) {
                alert("you have not input a name");
                return;
            }
            controller.printCard(name);
        }
    }
}
