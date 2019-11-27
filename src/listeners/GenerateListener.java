package listeners;

import newyugiohcardmaker.NewYugiohCardMaker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateListener implements ItemListener, DocumentListener{

    private NewYugiohCardMaker cardMaker;
    private boolean isActive = true;

    public GenerateListener(NewYugiohCardMaker cardMaker) {
        this.cardMaker = cardMaker;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if ((e.getStateChange() == ItemEvent.SELECTED
                || e.getItem() instanceof JCheckBox)
                && isActive) {
            try {
                cardMaker.generate();
            }
            catch (IOException ex) {
                Logger.getLogger(NewYugiohCardMaker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            cardMaker.generate();
        }
        catch (IOException ex) {
            Logger.getLogger(NewYugiohCardMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            cardMaker.generate();
        }
        catch (IOException ex) {
            Logger.getLogger(NewYugiohCardMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
