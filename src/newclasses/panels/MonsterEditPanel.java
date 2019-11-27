package newclasses.panels;

import cardproperties.Ability;
import cardproperties.Attribute;
import cardproperties.Level;
import cardproperties.MonsterType;
import characterLimits.JTextFieldCharacterLimit;
import factories.*;
import net.miginfocom.swing.MigLayout;
import newclasses.CardController;
import newclasses.ColorConstants;
import newclasses.card.MonsterCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MonsterEditPanel extends JPanel {

    private CardController controller;

    private JLabel lblCardDescription = createLabel("Card Description:");
    private JLabel lblCardBase = createLabel("Card Frame:");
    private JLabel lblLevel;

    private JPanel pnlAtkDef;
    private JPanel pnlCardDescription;

    private JButton btnMaterials;

    private JComboBox<Ability> cboAbility;
    private JComboBox<Attribute> cboAttribute;
    private JComboBox<Integer> cboLevel;
    private JComboBox<MonsterType> cboMonsterType;

    private JCheckBox chkEffect;

    private JScrollPane scrCardDescription;

    private JTextArea txtCardDescription;

    private JTextField txtAtk;
    private JTextField txtDef;
    private JTextField txtMaterials;

    public MonsterEditPanel(CardController controller) {
        this.controller = controller;
        setLayout(new MigLayout("fillx, insets 5 0 0 0, gap 5", "[min!][fill][min!][fill][min!]"));
        setBackground(ColorConstants.GRAY);

        JLabel lblAttribute = createLabel("Attribute:");
        lblAttribute.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAttribute.setMinimumSize(lblCardBase.getPreferredSize());

        add(getLblLevel(), "gap 5");
        add(getCboLevel());
        add(lblAttribute, "right");
        add(getCboAttribute());
        add(JButtonFactory.createDefaultButton(null).build(), "gapright 5, wrap");

        add(createLabel("Monster Type:"), "gap 5");
        add(getCboMonsterType());
        add(createLabel("Ability:"), "right");
        add(getCboAbility());

        add(createLabel("Materials:"), "newline, gap 5");
        add(getTxtMaterials(), "span 3");
        add(getBtnMaterialSelect(), "");

        add(getPnlCardDescription(), "newline, span, grow");

        add(createLabel("ATK/DEF:"), "skip 2, gap 0, right");
        add(getPnlAtkDef());
    }

    private JPanel getPnlAtkDef() {
        if (pnlAtkDef == null) {
            pnlAtkDef = JPanelFactory.createDefaultMigPanel("fillx, insets 0", "[fill][min!][fill]")
                    .setBackground(ColorConstants.GRAY)
                    .add(getTxtAtk())
                    .add(createLabel("/"))
                    .add(getTxtDef())
                    .build();
        }
        return pnlAtkDef;
    }

    private JTextField getTxtAtk() {
        if (txtAtk == null) {
            txtAtk = JTextFieldFactory.createDefaultSmallTextField().build();
            txtAtk.setDocument(new JTextFieldCharacterLimit(6, 6));
            txtAtk.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtAtk;
    }

    private JTextField getTxtDef() {
        if (txtDef == null) {
            txtDef = JTextFieldFactory.createDefaultSmallTextField().build();
            txtDef.setDocument(new JTextFieldCharacterLimit(6, 6));
            txtDef.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtDef;
    }

    private JPanel getPnlCardDescription() {
        if (pnlCardDescription == null) {
            pnlCardDescription = new JPanel(new MigLayout("fillx, insets 0, gap 5", "[min!][fill][min!]"));
            pnlCardDescription.setBackground(ColorConstants.LIGHT_GRAY);
            pnlCardDescription.add(lblCardDescription, "gap 5");
            pnlCardDescription.add(getTbpMonsterDesc(), "span 1 2");
            pnlCardDescription.add(JButtonFactory.createDefaultButton(null).build(), "gapright 5, wrap");
            pnlCardDescription.add(getChkEffect(), "top");
        }
        return pnlCardDescription;
    }

    private JCheckBox getChkEffect() {
        if (chkEffect == null) {
            chkEffect = new JCheckBox("Has Effect");
            chkEffect.addActionListener(controller.getGenerateListener());
            chkEffect.addChangeListener(e -> {
                if (chkEffect.isSelected()) {
                    cboAbility.setModel(new DefaultComboBoxModel<>(Ability.getEffect()));
                }
                else {
                    cboAbility.setModel(new DefaultComboBoxModel<>(Ability.getNonEffect()));
                }
            });
        }
        return chkEffect;
    }

    private JTabbedPane getTbpMonsterDesc() {
        JTabbedPane tbp = new JTabbedPane();
        tbp.add(getScrCardDescription());
        tbp.setTitleAt(0, "Monster Description");
        return tbp;
    }

    private JScrollPane getScrCardDescription() {
        if (scrCardDescription == null) {
            scrCardDescription = new JScrollPane(getTxtCardDescription());
            scrCardDescription.setMinimumSize(new Dimension(0, 176));
        }
        return scrCardDescription;
    }

    private JTextArea getTxtCardDescription() {
        if (txtCardDescription == null) {
            txtCardDescription = new JTextArea();
            txtCardDescription.setLineWrap(true);
            txtCardDescription.setWrapStyleWord(true);
            txtCardDescription.setFont(new Font("Tahoma", 0, 11));
            txtCardDescription.getDocument().addDocumentListener((controller.getGenerateListener()));
        }
        return txtCardDescription;
    }

    private JComboBox<Ability> getCboAbility() {
        if (cboAbility == null) {
            cboAbility = JComboBoxFactory.<Ability>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Ability.getNonEffect()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboAbility;
    }

    private JComboBox<Attribute> getCboAttribute() {
        if (cboAttribute == null) {
            cboAttribute = JComboBoxFactory.<Attribute>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Attribute.values()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboAttribute;
    }

    protected JComboBox<Integer> getCboLevel() {
        if (cboLevel == null) {
            cboLevel = JComboBoxFactory.<Integer>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Level.getAllPositiveLevels()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboLevel;
    }

    protected JLabel getLblLevel() {
        if (lblLevel == null) {
            lblLevel = createLabel("Level:");
            lblLevel.setMinimumSize(lblCardDescription.getPreferredSize());
        }
        return lblLevel;
    }

    private JComboBox<MonsterType> getCboMonsterType() {
        if (cboMonsterType == null) {
            cboMonsterType = JComboBoxFactory.<MonsterType>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(MonsterType.values()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboMonsterType;
    }

    private JTextField getTxtMaterials() {
        if (txtMaterials == null) {
            txtMaterials = JTextFieldFactory.createDefaultTextField().build();
            txtMaterials.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtMaterials;
    }

    private JButton getBtnMaterialSelect() {
        if (btnMaterials == null) {
            btnMaterials = JButtonFactory.createDefaultButton(IconFactory.SEARCH_CARD).build();
            btnMaterials.addActionListener(this::selectMaterial);
        }
        return btnMaterials;
    }

    private void selectMaterial(ActionEvent event) {
    }

    public void populateCardFromUI(MonsterCard card) {
        card.setAttribute(getCboAttribute().getItemAt(getCboAttribute().getSelectedIndex()));
        card.setLevel(getCboLevel().getItemAt(getCboLevel().getSelectedIndex()));
        card.setMonsterType(getCboMonsterType().getItemAt(getCboMonsterType().getSelectedIndex()));
        card.setAbility(getCboAbility().getItemAt(getCboAbility().getSelectedIndex()));
        card.setHasEffect(getChkEffect().isSelected());
        card.setDescription(getTxtCardDescription().getText());
        card.setAtk(getTxtAtk().getText());
        card.setDef(getTxtDef().getText());
        card.setMaterials(getTxtMaterials().getText());
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("", Font.BOLD, 12));
        return lbl;
    }
}
