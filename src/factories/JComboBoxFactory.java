package factories;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

public class JComboBoxFactory<T> {

    private final JComboBox<T> comboBox;

    private JComboBoxFactory() {
        comboBox = new JComboBox<>();
    }
    
    public static <T> JComboBoxFactory<T> createDefaultComboBox() {
        return JComboBoxFactory.createComboBox(DimensionFactory.DEFAULT_TEXT_FIELD, false);
    }
    
    public static <T> JComboBoxFactory<T> createDefaultEditableComboBox() {
        return JComboBoxFactory.createComboBox(DimensionFactory.DEFAULT_TEXT_FIELD, true);
    }
    
    private static <T> JComboBoxFactory<T> createComboBox(Dimension dimension, boolean editable) {
        return new JComboBoxFactory<T>()
                .setBorder(BorderFactory.createLineBorder(Color.GRAY))
                .setSize(dimension)
                .setMinimumSize(dimension)
                .setEditable(editable);
//                .setUI();
    }
    
    public JComboBoxFactory<T> setBorder(Border border) {
        comboBox.setBorder(border);
        return this;
    }
    
    public JComboBoxFactory<T> setEditable(boolean editable) {
        comboBox.setEditable(editable);
        return this;
    }

    public JComboBoxFactory<T> setSize(Dimension dimension) {
        comboBox.setSize(dimension);
        return this;
    }
    
    public JComboBoxFactory<T> setPreferredSize(Dimension dimension) {
        comboBox.setPreferredSize(dimension);
        return this;
    }
    
    public JComboBoxFactory<T> setMinimumSize(Dimension dimension) {
        comboBox.setMinimumSize(dimension);
        return this;
    }
    
    public JComboBoxFactory<T> setMaximumSize(Dimension dimension) {
        comboBox.setMaximumSize(dimension);
        return this;
    }
    
    public JComboBoxFactory<T> setUI() {
        comboBox.setUI(ColorArrowUI.createUI(comboBox));
        return this;
    }
    
    public JComboBoxFactory<T> setOrientationRightToLeft() {
        return setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
    
    private JComboBoxFactory<T> setComponentOrientation(ComponentOrientation o) {
        comboBox.setComponentOrientation(o);
        return this;
    }

    public JComboBoxFactory<T> addActionListener(ActionListener l) {
        comboBox.addActionListener(l);
        return this;
    }

    public JComboBoxFactory<T> addItemListener(ItemListener l) {
        comboBox.addItemListener(l);
        return this;
    }

    public JComboBoxFactory<T> setModel(ComboBoxModel<T> aModel) {
        comboBox.setModel(aModel);
        return this;
    }

    public JComboBoxFactory<T> setDocument(Document doc) {
        getEditorComponent().setDocument(doc);
        return this;
    }

    public JComboBoxFactory<T> addKeyListener(KeyListener l) {
        comboBox.getEditor().getEditorComponent().addKeyListener(l);
        return this;
    }

    public JComboBoxFactory<T> addDocumentListener(DocumentListener l) {
        getDocument().addDocumentListener(l);
        return this;
    }

    private Document getDocument() {
        return getEditorComponent().getDocument();
    }

    private JTextField getEditorComponent() {
        return ((JTextField) comboBox.getEditor().getEditorComponent());
    }

    public JComboBox<T> build() {
        return comboBox;
    }
}


