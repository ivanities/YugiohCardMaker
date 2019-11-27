package newclasses.toolbar;

import factories.IconFactory;
import factories.JButtonFactory;
import newclasses.CardView;

import javax.swing.*;

public class EditToolBar extends JToolBar {

    private CardView parent;

    private JButton btnUndo;
    private JButton btnRedo;
    private JButton btnCopy;
    private JButton btnPaste;
    private JButton btnCut;

    public EditToolBar(CardView parent) {
        this.parent = parent;

        add(getBtnUndo());
        add(getBtnRedo());
        add(getBtnCopy());
        add(getBtnPaste());
        add(getBtnCut());

        setFloatable(false);
    }

    private JButton getBtnUndo() {
        if (btnUndo == null) {
            btnUndo = JButtonFactory.createToolBarButton(IconFactory.UNDO)
                    .setToolTipText("Undo")
                    .build();
        }
        return btnUndo;
    }

    private JButton getBtnRedo() {
        if (btnRedo == null) {
            btnRedo = JButtonFactory.createToolBarButton(IconFactory.REDO)
                    .setToolTipText("Redo")
                    .build();
        }
        return btnRedo;
    }

    private JButton getBtnCopy() {
        if (btnCopy == null) {
            btnCopy = JButtonFactory.createToolBarButton(IconFactory.REFRESH)
                    .setToolTipText("Copy")
                    .build();
        }
        return btnCopy;
    }

    private JButton getBtnPaste() {
        if (btnPaste == null) {
            btnPaste = JButtonFactory.createToolBarButton(IconFactory.REFRESH)
                    .setToolTipText("Paste")
                    .build();
        }
        return btnPaste;
    }

    private JButton getBtnCut() {
        if (btnCut == null) {
            btnCut = JButtonFactory.createToolBarButton(IconFactory.REFRESH)
                    .setToolTipText("Cut")
                    .build();
        }
        return btnCut;
    }

}
