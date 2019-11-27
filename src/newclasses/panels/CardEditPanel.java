package newclasses.panels;

import cardproperties.*;
import cardproperties.Frame;
import characterLimits.JTextFieldCharacterLimit;
import characterLimits.NewSerialNumberLimit;
import constants.FilePaths;
import factories.IconFactory;
import factories.JButtonFactory;
import factories.JComboBoxFactory;
import factories.JTextFieldFactory;
import listeners.NewSerialNumberKeyListener;
import net.miginfocom.swing.MigLayout;
import newclasses.CardController;
import newclasses.ColorConstants;
import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static cardproperties.Frame.DARK_SYNCHRO;
import static cardproperties.Frame.EXCEED;

public class CardEditPanel extends JPanel {

    private CardController controller;
    private JButton btnUpload;

    private JComboBox<Frame> cboCardFrame;
    private JComboBox<Rarity> cboCardRarity;
    private JComboBox<Edition> cboCirculation;
    private JComboBox<Limitation> cboSerialNumber;

    private JFileChooser pictureChooser;

    private JPanel pnlCardId;
    private MonsterEditPanel pnlMonsterEdit;
    private SpellTrapEditPanel pnlSpellTrapEdit;

    private JTabbedPane cardEditPane;

    private final JTextField txtPictureUrl = JTextFieldFactory.createDefaultTextField().setEditable(false).build();
    private JTextField txtName;
    private JTextField txtCardId1;
    private JTextField txtCardId2;
    private JTextField txtYear;
    private JTextField txtCreator;

    private final ItemListener levelImageListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                JComboBox<Integer> cbo = getPnlMonsterEdit().getCboLevel();

                if (e.getItem() == DARK_SYNCHRO) {
                    int item = cbo.getItemAt(cbo.getSelectedIndex());
                    cbo.setModel(new DefaultComboBoxModel<>(Level.getAllNegativeLevels()));
                    cbo.setSelectedItem(-item);
                }
                else if (cbo.getModel().getElementAt(1) < 0) {
                    int item = cbo.getItemAt(cbo.getSelectedIndex());
                    cbo.setModel(new DefaultComboBoxModel<>(Level.getAllPositiveLevels()));
                    cbo.setSelectedItem(-item);
                }
            }
        }
    };

    private final ItemListener levelLabelListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == EXCEED) {
                    getPnlMonsterEdit().getLblLevel().setText("Rank:");
                }
                else if (!getPnlMonsterEdit().getLblLevel().getText().equals("Level:")) {
                    getPnlMonsterEdit().getLblLevel().setText("Level:");
                }
            }
        }
    };

    private final ItemListener cardChangeListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JComboBox<Frame> cbo = (JComboBox<Frame>) e.getSource();
            Frame frame = cbo.getItemAt(cbo.getSelectedIndex());

            Card card = controller.getCard();

            if (frame.equals(Frame.SPELL) || frame.equals(Frame.TRAP)) {
                if (!(card instanceof SpellTrapCard)) {
                    controller.setCard(new SpellTrapCard(card));
                    controller.refreshComponents();
                }
            }
            else {
                if (controller.isPendulum() && !(card instanceof PendulumMonsterCard)) {
                    controller.setCard(new PendulumMonsterCard(card));
                    controller.refreshComponents();
                }
                else if (!(card instanceof MonsterCard)) {
                    controller.setCard(new MonsterCard(card));
                    controller.refreshComponents();
                }
            }
        }
    };

    public CardEditPanel(CardController controller) {
        this.controller = controller;
        setLayout(new MigLayout("fillx, insets 0, gap 5, hidemode 1", "[min!][fill][min!][fill][min!]"));
        setBackground(ColorConstants.DARK_GRAY);

        JLabel lblCardDescription = createLabel("Card Description:");

        add(createLabel("Name:"), "gap 5");
        add(getTxtName(), "span 3, gaptop 5, wrap");

        add(createLabel("Picture URL:"), "gap 5");
        add(txtPictureUrl, "span 3");
        add(getBtnUpload(), "gapright 5, wrap");

        add(createLabel("Card Rarity:"), "gap 5");
        add(getCboCardRarity());
        add(createLabel("Card Frame:"), "right");
        add(getCboCardFrame(), "wrap");

        add(createLabel("Circulation:"), "gap 5");
        add(getCboCirculation());
        add(createLabel("Card ID:"), "right");
        add(getPnlCardId(), "wrap");

        add(getEditTabbedPane(), "spanx, growx, wrap");

        JLabel lblSerialNumber = createLabel("Serial Number:");
        lblSerialNumber.setMinimumSize(lblCardDescription.getPreferredSize());

        add(lblSerialNumber, "gap 5");
        add(getCboSerialNumber(), "span 3, wrap");

        add(createLabel("Year:"), "gap 5");
        add(getTxtYear());
        add(createLabel("Creator:"), "right");
        add(getTxtCreator(), "wrap");
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public JTextField getTxtName() {
        if (txtName == null) {
            txtName = JTextFieldFactory.createDefaultTextField().build();
            txtName.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtName;
    }

    private JTabbedPane getEditTabbedPane() {
        if (cardEditPane == null) {
            cardEditPane = new JTabbedPane();
            cardEditPane.addTab("Monster Edit", getPnlMonsterEdit());
            cardEditPane.addTab("SpellTrap Edit", getPnlSpellTrapEdit());
        }
        return cardEditPane;
    }

    private MonsterEditPanel getPnlMonsterEdit() {
        if (pnlMonsterEdit == null) {
            pnlMonsterEdit = new MonsterEditPanel(controller);
        }
        return pnlMonsterEdit;
    }

    private SpellTrapEditPanel getPnlSpellTrapEdit() {
        if (pnlSpellTrapEdit == null) {
            pnlSpellTrapEdit = new SpellTrapEditPanel(controller);
        }
        return pnlSpellTrapEdit;
    }

    private JPanel getPnlCardId() {
        if (pnlCardId == null) {
            pnlCardId = new JPanel(new MigLayout("fillx, insets 0", "[fill][min!][fill]"));
            pnlCardId.setBackground(ColorConstants.DARK_GRAY);
            pnlCardId.add(getTxtCardId1());
            pnlCardId.add(createLabel("-"));
            pnlCardId.add(getTxtCardId2());
        }
        return pnlCardId;
    }

    private JTextField getTxtCardId1() {
        if (txtCardId1 == null) {
            txtCardId1 = JTextFieldFactory.createDefaultSmallTextField().build();
            txtCardId1.setDocument(new JTextFieldCharacterLimit(4, 4));
            txtCardId1.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtCardId1;
    }

    private JTextField getTxtCardId2() {
        if (txtCardId2 == null) {
            txtCardId2 = JTextFieldFactory.createDefaultSmallTextField().build();
            txtCardId2.setDocument(new JTextFieldCharacterLimit(5, 5));
            txtCardId2.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtCardId2;
    }

    private JButton getBtnUpload() {
        if (btnUpload == null) {
            btnUpload = JButtonFactory.createDefaultButton(IconFactory.UPLOAD)
            .addActionListener(controller.getGenerateListener())
            .addActionListener(e -> getPicture())
            .build();
        }
        return btnUpload;
    }

    private void getPicture() {
        JFileChooser chooser = getPictureChooser();
        int option = chooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                BufferedImage image = ImageUtils.toCompatibleImage(ImageIO.read(selectedFile));
                controller.getCard().setImages(image);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            txtPictureUrl.setText(selectedFile.getName());
        }
    }

    private JFileChooser getPictureChooser() {
        if (pictureChooser == null) {
            pictureChooser = new JFileChooser(new File(FilePaths.IMAGES));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("png images", "png");
            pictureChooser.setFileFilter(filter);
            pictureChooser.setDialogTitle("Picture Chooser");
            pictureChooser.setAcceptAllFileFilterUsed(false);
        }
        return pictureChooser;
    }

    private JComboBox<Frame> getCboCardFrame() {
        if (cboCardFrame == null) {
            cboCardFrame = JComboBoxFactory.<Frame>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Frame.values()))
                    .addActionListener(controller.getGenerateListener())
                    .addItemListener(levelLabelListener)
                    .addItemListener(levelImageListener)
                    .addItemListener(cardChangeListener)
                    .build();
        }
        return cboCardFrame;
    }

    private JComboBox<Rarity> getCboCardRarity() {
        if (cboCardRarity == null) {
            cboCardRarity = JComboBoxFactory.<Rarity>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Rarity.values()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboCardRarity;
    }

    private JComboBox<Edition> getCboCirculation() {
        if (cboCirculation == null) {
            cboCirculation = JComboBoxFactory.<Edition>createDefaultComboBox()
                    .setModel(new DefaultComboBoxModel<>(Edition.values()))
                    .addActionListener(controller.getGenerateListener())
                    .build();
        }
        return cboCirculation;
    }

    private JComboBox<Limitation> getCboSerialNumber() {
        if (cboSerialNumber == null) {
            cboSerialNumber = JComboBoxFactory.<Limitation>createDefaultEditableComboBox()
                    .setModel(new DefaultComboBoxModel<>(Limitation.values()))
                    .build();
            ((JTextField) cboSerialNumber.getEditor().getEditorComponent()).setDocument(new NewSerialNumberLimit(8, 0, cboSerialNumber));
            cboSerialNumber.getEditor().getEditorComponent().addKeyListener(new NewSerialNumberKeyListener(cboSerialNumber));
            ((JTextField) cboSerialNumber.getEditor().getEditorComponent()).getDocument().addDocumentListener((controller.getGenerateListener()));
        }
        return cboSerialNumber;
    }

    private JTextField getTxtYear() {
        if (txtYear == null) {
            txtYear = JTextFieldFactory.createDefaultSmallTextField().build();
            txtYear.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtYear;
    }

    private JTextField getTxtCreator() {
        if (txtCreator == null) {
            txtCreator = JTextFieldFactory.createDefaultTextField().build();
            txtCreator.getDocument().addDocumentListener(controller.getGenerateListener());
        }
        return txtCreator;
    }

    public void populateCardFromUI(Card card) {
        card.setName(getTxtName().getText());
        card.setRarity(getCboCardRarity().getItemAt(getCboCardRarity().getSelectedIndex()));
        card.setFrame(getCboCardFrame().getItemAt(getCboCardFrame().getSelectedIndex()));
        card.setCardID(getTxtCardId1().getText() + "-" + getTxtCardId2().getText());
        card.setEdition(getCboCirculation().getItemAt(getCboCirculation().getSelectedIndex()));
        card.setSerialCode(((JTextField) getCboSerialNumber().getEditor().getEditorComponent()).getText());
        card.setCreator(getTxtCreator().getText());
        card.setYear(getTxtYear().getText());

        if (card instanceof MonsterCard) {
            getPnlMonsterEdit().populateCardFromUI((MonsterCard) card);

            if (card instanceof PendulumMonsterCard) {
                getPnlSpellTrapEdit().populateCardFromUI((PendulumMonsterCard) card);
            }
        }
        else if (card instanceof SpellTrapCard) {
            getPnlSpellTrapEdit().populateCardFromUI((SpellTrapCard) card);
        }
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("", Font.BOLD, 12));
        return lbl;
    }
}
