package characterLimits;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldCharacterLimit extends PlainDocument{
    
    private boolean active = true;
    
    private final int numberLimit;
    private final int textLimit;
    
    public JTextFieldCharacterLimit(int numberLimit, int textLimit){
        this.numberLimit = numberLimit;
        this.textLimit = textLimit;
    }
    
    @Override
    public void insertString(int offset, String str, AttributeSet attributeSet) throws BadLocationException{
        if (active) {
            if(str.matches(".*\\d+.*") && (getLength() + str.length()) <= numberLimit){
                super.insertString(offset, str, attributeSet);
            }
            else if(!str.matches(".*\\d+.*") && (getLength() + str.length()) <= textLimit){
                super.insertString(offset, str, attributeSet);
            }
        }
        else {
            super.insertString(offset, str, attributeSet);
        }
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}
