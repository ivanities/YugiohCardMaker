package characterLimits;

import cardproperties.Limitation;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class SerialNumberLimit extends JTextFieldCharacterLimit{
    
    private final JComboBox<Limitation> cboSerialNumber;
    
    public SerialNumberLimit(int numberLimit, int textLimit, JComboBox<Limitation> cboSerialNumber) {
        super(numberLimit, textLimit);
        this.cboSerialNumber = cboSerialNumber;
    }
    
    @Override
    public void insertString(int offset, String str, AttributeSet attributeSet) throws BadLocationException{
        if(((DefaultComboBoxModel)cboSerialNumber.getModel()).getIndexOf(str) > -1){
            setActive(false);
            super.insertString(offset, str, attributeSet);
            setActive(true);
        }
        else if (((DefaultComboBoxModel)cboSerialNumber.getModel()).getIndexOf(cboSerialNumber.getSelectedItem()) > -1) {
            cboSerialNumber.setSelectedItem(str);
        }
        else {
            super.insertString(offset, str, attributeSet);
        }
    }
    
}
