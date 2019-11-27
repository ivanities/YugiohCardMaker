package newclasses;

import net.miginfocom.swing.MigLayout;
import newclasses.card.MonsterCard;
import newclasses.panels.CardEditPanel;
import newclasses.panels.CardPreviewPanel;
import newclasses.toolbar.FileToolBar;
import newclasses.toolbar.StyleToolBar;
import newclasses.toolbar.YugiohMenuBar;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardView extends JFrame {

    private CardController controller;

    private CardEditPanel pnlCardEdit;
    private CardPreviewPanel pnlCardPreview;
    private YugiohMenuBar yugiohMenuBar;
    private FileToolBar fileToolBar;
    private StyleToolBar styleToolBar;

    public CardView() throws IOException {
        super("Yugioh Card Maker");
        controller = new CardController(new CardModel(new MonsterCard()), this);
        setLayout(new MigLayout("fill, gap 0", "[min!][]"));
        add(getYugiohMenuBar(), "north");
        add(getFileToolBar(), "north, wrap");
        add(getStyleToolBar(), "top");
        add(getSplMain(), "grow");
        setSize(918, 586);
        setMinimumSize(new Dimension(918, 586));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JSplitPane getSplMain() throws IOException {
        JSplitPane splMain = new JSplitPane();
        splMain.setLeftComponent(getCardEditPanel());
        splMain.setRightComponent(getPnlCardPreview());
        splMain.setEnabled(true);
        splMain.setBorder(BasicBorders.getSplitPaneDividerBorder());
        return splMain;
    }

    private YugiohMenuBar getYugiohMenuBar() {
        if (yugiohMenuBar == null) {
            yugiohMenuBar = new YugiohMenuBar(controller);
        }
        return yugiohMenuBar;
    }

    private FileToolBar getFileToolBar() {
        if (fileToolBar == null) {
            fileToolBar = new FileToolBar();
        }
        return fileToolBar;
    }

    private StyleToolBar getStyleToolBar() {
        if (styleToolBar == null) {
            styleToolBar = new StyleToolBar(controller);
        }
        return styleToolBar;
    }

    public CardEditPanel getCardEditPanel() {
        if (pnlCardEdit == null) {
            pnlCardEdit = new CardEditPanel(controller);
        }
        return pnlCardEdit;
    }

    public CardPreviewPanel getPnlCardPreview() throws IOException {
        if (pnlCardPreview == null) {
            pnlCardPreview = new CardPreviewPanel();
        }
        return pnlCardPreview;
    }

    public static void main(String[] args) throws IOException {
        //<editor-fold defaultState="collapsed" desc=" Look and feel setting code (Windows) ">
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        CardView view = new CardView();
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }
}