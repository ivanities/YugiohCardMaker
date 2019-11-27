package newclasses.panels;

import cardproperties.Level;
import cardproperties.Property;
import factories.JButtonFactory;
import factories.JComboBoxFactory;
import net.miginfocom.swing.MigLayout;
import newclasses.CardController;
import newclasses.ColorConstants;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;

import javax.swing.*;
import java.awt.*;

public class SpellTrapEditPanel extends JPanel {

    private CardController controller;

    private JLabel lblCardDescription = createLabel("Card Description:");
    private JLabel lblCardBase = createLabel("Card Frame:");
    private JLabel lblProperty;
    private JLabel lblPendulumScale;

    private JPanel pnlCardDescription;

    private JComboBox<Integer> cboPendulumScale;
    private JComboBox<Property> cboProperty;

    private JCheckBox chkTrapMonster;

    private JScrollPane scrCardDescription;

    private JTextArea txtCardDescription;

    public SpellTrapEditPanel(CardController controller) {
        this.controller = controller;
        setLayout(new MigLayout("fillx, insets 5 0 0 0, gap 5", "[min!][fill][min!][fill][min!]"));
        setBackground(ColorConstants.GRAY);

        getLblProperty().setHorizontalAlignment(SwingConstants.RIGHT);
        getLblProperty().setMinimumSize(lblCardBase.getPreferredSize());

        add(getLblPendulumScale(), "gap 5");
        add(getCboPendulumScale());
        add(getLblProperty(), "right");
        add(getCboProperty());
        add(JButtonFactory.createDefaultButton(null).build(), "gapright 5, wrap");

        add(getPnlCardDescription(), "newline, span, grow");
    }

    private JPanel getPnlCardDescription() {
        if (pnlCardDescription == null) {
            pnlCardDescription = new JPanel(new MigLayout("fillx, insets 0, gap 5", "[min!][fill][min!]"));
            pnlCardDescription.setBackground(ColorConstants.LIGHT_GRAY);
            pnlCardDescription.add(lblCardDescription, "gap 5");
            pnlCardDescription.add(getTbpSpellTrapDesc(), "span 1 2");
            pnlCardDescription.add(JButtonFactory.createDefaultButton(null).build(), "gapright 5, wrap");
            pnlCardDescription.add(getChkTrapMonster(), "top");
        }
        return pnlCardDescription;
    }

    private JCheckBox getChkTrapMonster() {
        if (chkTrapMonster == null) {
            chkTrapMonster = new JCheckBox("Trap Monster");
            chkTrapMonster.addActionListener(controller.getGenerateListener());
        }
        return chkTrapMonster;
    }

    private JTabbedPane getTbpSpellTrapDesc() {
        JTabbedPane tbp = new JTabbedPane();
        tbp.add(getScrCardDescription());
        tbp.setTitleAt(0, "SpellTrap Description");
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

    private JComboBox<Integer> getCboPendulumScale() {
        if (cboPendulumScale == null) {
            cboPendulumScale = JComboBoxFactory.<Integer>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Level.getAllPositiveLevels()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboPendulumScale;
    }

    private JLabel getLblProperty() {
        if (lblProperty == null) {
            lblProperty = createLabel("Property:");
        }
        return lblProperty;
    }

    private JLabel getLblPendulumScale() {
        if (lblPendulumScale == null) {
            lblPendulumScale = createLabel("Pendulum Scale:");
            lblPendulumScale.setMinimumSize(lblCardDescription.getPreferredSize());
        }
        return lblPendulumScale;
    }

    private JComboBox<Property> getCboProperty() {
        if (cboProperty == null) {
            cboProperty = JComboBoxFactory.<Property>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Property.values()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboProperty;
    }

    public void populateCardFromUI(PendulumMonsterCard card) {
        card.setPendulumScale(getCboPendulumScale().getItemAt(getCboPendulumScale().getSelectedIndex()));
        card.setPendulumDescription(getTxtCardDescription().getText());
    }

    public void populateCardFromUI(SpellTrapCard card) {
        card.setProperty(getCboProperty().getItemAt(getCboProperty().getSelectedIndex()));
        card.setDescription(getTxtCardDescription().getText());
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("", Font.BOLD, 12));
        return lbl;
    }
}
