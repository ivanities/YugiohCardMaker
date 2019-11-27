package listeners;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SerialNumberKeyListener implements KeyListener{
    
    private final JComboBox cboSerialNumber;
    
    public SerialNumberKeyListener(JComboBox cboSerialNumber) {
        this.cboSerialNumber = cboSerialNumber;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!cboSerialNumber.getSelectedItem().equals("") && (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)) {
            if (((DefaultComboBoxModel) cboSerialNumber.getModel()).getIndexOf(cboSerialNumber.getSelectedItem()) > -1) {
                cboSerialNumber.setSelectedItem("0");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
