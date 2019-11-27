package newclasses;

import cardproperties.Frame;
import constants.FilePaths;
import listeners.NewGenerateListener;
import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;
import newclasses.cardcomponents.CardComponent;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardController {

    private final NewGenerateListener generateListener;

    private CardGenerator generator;
    private CardModel model;
    private CardView view;
    private BufferedImage finalImage;

    private Map<Boolean, CardComponentProvider> componentProviders;

    CardController(CardModel model, CardView view) {
        this.model = model;
        this.view = view;
        this.generator = new CardGenerator();
        this.generateListener = new NewGenerateListener(this);
        finalImage = ImageUtils.createNewCompatibleImage(826, 1204, Transparency.TRANSLUCENT);
        Graphics2D g = finalImage.createGraphics();
        g.setBackground(Color.WHITE);
        ImageUtils.setRenderingHints(g);
        g.dispose();
        initializeComponentProviders();
    }

    public boolean isPendulum() {
        return model.isPendulum();
    }

    public Card getCard() {
        return model.getCard();
    }

    public void setCard(Card card) {
        model.setCard(card);
    }

    public CardView getView() {
        return view;
    }

    private void initializeComponentProviders() {
        componentProviders = new HashMap<>();
        componentProviders.put(false, new RegularComponentProvider());
        componentProviders.put(true, new AnimeComponentProvider());
        refreshComponents();
    }

    public void refreshComponents() {
        refreshComponents(componentProviders.get(model.isAnime()));
    }

    private void refreshComponents(CardComponentProvider componentProvider) {
        List<CardComponent> components = new ArrayList<>();
        components.addAll(componentProvider.getCommonComponents());
        components.addAll(getTypeComponents(componentProvider));

        if (model.showAdditionalDetails()) {
            components.addAll(componentProvider.getAdditionalDetailsComponents());
        }
        generator.setComponents(components);
    }

    private List<CardComponent> getTypeComponents(CardComponentProvider componentProvider) {
        List<CardComponent> components = new ArrayList<>();
        if (model.getCard() instanceof MonsterCard) {
            components.addAll(componentProvider.getMonsterComponents());

            if (model.getCard() instanceof PendulumMonsterCard) {
                components.addAll(componentProvider.getPendulumComponents());
            }
        }
        else if (model.getCard() instanceof SpellTrapCard) {
            components.addAll(componentProvider.getSpellTrapComponents());
        }
        return components;
    }

    public void animeButtonAction(ActionEvent event) {
        model.setAnime(!model.isAnime());
        refreshButtonAction(event);
    }

    public void additionalDetailsButtonAction(ActionEvent event) {
        model.setShowAdditionalDetails(!model.showAdditionalDetails());
        refreshButtonAction(event);
    }

    public void pendulumButtonAction(ActionEvent event) {
        model.setPendulum(!model.isPendulum());
        setPendulumCard();
        refreshButtonAction(event);
    }

    private void setPendulumCard() {
        if (model.isPendulum()) {
            model.setCard(new PendulumMonsterCard(model.getCard()));
        }
        else if (Frame.SPELL.equals(model.getCard().getFrame()) || Frame.TRAP.equals(model.getCard().getFrame())) {
            model.setCard(new SpellTrapCard(model.getCard()));
        }
        else {
            model.setCard(new MonsterCard(model.getCard()));
        }
    }

    public void refreshButtonAction(ActionEvent event) {
        refreshComponents();
        generate();
    }

    public void generate() {
        populateCardFromUI();
        try {
            finalImage = generator.generate(model.getCard());
            view.getPnlCardPreview().setCardImage(finalImage);
            view.getPnlCardPreview().getLblCardPreview().repaint();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printCard(String name) {
        populateCardFromUI();
        try {
            String fileName = name.toLowerCase().replace(' ', '_');
            ImageIO.write(finalImage, "PNG", new File(getSavePath() + fileName + ".png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSavePath() {
        return model.isAnime() ? FilePaths.ANIME_TEMP_DIR : FilePaths.TEMP_DIR;
    }

    private void populateCardFromUI() {
        view.getCardEditPanel().populateCardFromUI(model.getCard());
    }

    public NewGenerateListener getGenerateListener() {
        return generateListener;
    }
}
