package listeners;

import newclasses.CardController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class NewGenerateListener implements ActionListener, ItemListener, DocumentListener {

    private CardController controller;
    private boolean isActive = true;

    public NewGenerateListener(CardController controller) {
        this.controller = controller;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if ((e.getStateChange() == ItemEvent.SELECTED
                || e.getItem() instanceof JCheckBox)
                && isActive) {
            generate();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        generate();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        generate();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        generate();
    }

    private void generate() {
        controller.generate();
    }
}

