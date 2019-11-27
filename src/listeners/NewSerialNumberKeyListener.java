package listeners;

import cardproperties.Limitation;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NewSerialNumberKeyListener implements KeyListener{

    private final JComboBox<Limitation> cboSerialNumber;

    public NewSerialNumberKeyListener(JComboBox<Limitation> cboSerialNumber) {
        this.cboSerialNumber = cboSerialNumber;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!cboSerialNumber.getEditor().getItem().equals("")) {
            if (((DefaultComboBoxModel) cboSerialNumber.getModel()).getIndexOf(cboSerialNumber.getEditor().getItem()) > -1) {
                cboSerialNumber.setSelectedItem("0");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
