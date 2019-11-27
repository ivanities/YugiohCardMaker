package newclasses.card;

import cardproperties.Edition;
import cardproperties.Frame;
import cardproperties.Rarity;
import constants.Scales;
import utils.ImageUtils;

import java.awt.image.BufferedImage;

public abstract class Card {

    private String name;
    private BufferedImage regularImage;
    private BufferedImage animeImage;
    private BufferedImage pendulumImage;
    private String cardID;
    private String description;
    private String serialCode;
    private Rarity rarity;
    private String creator;
    private String year;
    private Frame frame;
    private Edition edition;

    public Card() {
    }

    public Card(Card card) {
        regularImage = card.getRegularImage();
        animeImage = card.getAnimeImage();
        pendulumImage = card.getPendulumImage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getRegularImage() {
        return regularImage;
    }

    public void setRegularImage(BufferedImage regularImage) {
        this.regularImage = regularImage;
    }

    public BufferedImage getAnimeImage() {
        return animeImage;
    }

    public void setAnimeImage(BufferedImage animeImage) {
        this.animeImage = animeImage;
    }

    public BufferedImage getPendulumImage() {
        return pendulumImage;
    }

    public void setPendulumImage(BufferedImage pendulumImage) {
        this.pendulumImage = pendulumImage;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public BufferedImage getImage(boolean isAnime, boolean isPendulum) {
        if (isAnime) {
            return animeImage;
        }
        else if (isPendulum) {
            return pendulumImage;
        }
        else {
            return regularImage;
        }
    }

    public void setImages(BufferedImage image) {
        regularImage = ImageUtils.resize(image, Scales.PICTURE);
        animeImage = ImageUtils.resize(image, Scales.PICTURE_ANIME_Y);
        pendulumImage = ImageUtils.resize(image, Scales.PENDULUM_PICTURE);
    }
}
