package factories;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;

public class JTextFieldFactory {
    
    private final JTextField textField;

    private JTextFieldFactory() {
        textField = new JTextField();
    }
    
    public static JTextFieldFactory createDefaultTextField() {
        return createTextField(DimensionFactory.DEFAULT_TEXT_FIELD);
    }
    
    public static JTextFieldFactory createDefaultSmallTextField() {
        return createTextField(DimensionFactory.DEFAULT_TEXT_FIELD_SMALL);
    }
    
    public static JTextFieldFactory createTextField(Dimension dimension) {
        return new JTextFieldFactory()
                .setBorder(BorderFactory.createLineBorder(Color.GRAY))
                .setSize(dimension)
                .setMinimumSize(dimension);
    }
    
    public JTextFieldFactory setBorder(Border border) {
        textField.setBorder(border);
        return this;
    }

    public JTextFieldFactory setSize(Dimension dimension) {
        textField.setSize(dimension);
        return this;
    }
    
    public JTextFieldFactory setPreferredSize(Dimension dimension) {
        textField.setPreferredSize(dimension);
        return this;
    }
    
    public JTextFieldFactory setMinimumSize(Dimension dimension) {
        textField.setMinimumSize(dimension);
        return this;
    }
    
    public JTextFieldFactory setMaximumSize(Dimension dimension) {
        textField.setMaximumSize(dimension);
        return this;
    }

    public JTextFieldFactory setEditable(boolean editable) {
        textField.setEditable(editable);
        return this;
    }

    public JTextFieldFactory setDocument(Document doc) {
        textField.setDocument(doc);
        return this;
    }

    public JTextFieldFactory addDocumentListener(DocumentListener l) {
        getDocument().addDocumentListener(l);
        return this;
    }

    private Document getDocument() {
        return textField.getDocument();
    }
    
    public JTextField build() {
        return textField;
    }
}
