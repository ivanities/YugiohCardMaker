package newclasses.old;

import cardproperties.Attribute;
import cardproperties.Frame;
import cardproperties.interfaces.PendulumStyleImage;
import cardproperties.interfaces.StyleImage;
import newclasses.CardController;
import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static cardproperties.Coordinates.getCoordinates;

public abstract class NewCardGeneratorOld {

    protected Graphics2D g;
    protected BufferedImage finalImage;
    protected CardController controller;

    public NewCardGeneratorOld(CardController controller) {
        finalImage = ImageUtils.createNewCompatibleImage(826, 1204, Transparency.TRANSLUCENT);
        g = finalImage.createGraphics();
        g.setBackground(Color.WHITE);
        ImageUtils.setRenderingHints(g);
        this.controller = controller;
    }

    protected void addAttribute(Attribute attribute, int level) throws IOException {
    }

    protected void addPicture(BufferedImage image) throws IOException {
        drawImage("Picture", image);
    }

    protected void addLevel(int level, Frame frame) throws IOException {
    }

    protected void addAtkDef(String atk, String def) throws IOException {
    }

    protected void addPendulumScales() {
    }

    protected void addPendulumScaleValue(String pendulumScaleValue) {
    }

    protected void addCardFrame(Frame frame) {
        drawImage(frame, "Frame");
    }

//    public boolean isAnime() {
//        return controller.isAnime();
//    }
//
//    public boolean isPendulum() {
//        return controller.isPendulum();
//    }

    public boolean isAnime() {
        return false;
    }

    public boolean isPendulum() {
        return false;
    }

    protected void drawImage(StyleImage styleImage, String key, int width) throws IOException {
        drawImage(key, ImageUtils.createThumbnail(width, findImage(styleImage)));
    }

    protected void drawImage(StyleImage styleImage, String key) {
        drawImage(key, findImage(styleImage));
    }

    private void drawImage(String key, BufferedImage image) {
        Point point = getCoordinates(isAnime(), isPendulum()).get(key);
        ImageUtils.drawImage(g, image, point);
    }

    private BufferedImage findImage(StyleImage styleImage) {
        if (styleImage instanceof PendulumStyleImage) {
            return getPendulumImage((PendulumStyleImage) styleImage);
        }
        return getImage(styleImage);
    }

    private BufferedImage getPendulumImage(PendulumStyleImage pendulumStyleImage) {
        if (isPendulum() && isAnime()) {
            return pendulumStyleImage.getAnimePendulumImage();
        }
        else if (!isPendulum()) {
            return getImage(pendulumStyleImage);
        }
        return pendulumStyleImage.getPendulumImage();
    }

    private BufferedImage getImage(StyleImage styleImage) {
        if (isAnime()) {
            return styleImage.getAnimeImage();
        }
        return styleImage.getImage();
    }

    public BufferedImage generate(Card card) throws IOException {
        // TODO find out why picture quality is worse
        g.clearRect(0, 0, 826, 1204);

        addPicture(card.getImage(isAnime(), isPendulum()));
        addCardFrame(card.getFrame());

        if (card instanceof MonsterCard) {
            addAttribute(((MonsterCard) card).getAttribute(), Math.abs(((MonsterCard) card).getLevel()));
            addLevel(Math.abs(((MonsterCard) card).getLevel()), card.getFrame());
            addAtkDef(((MonsterCard) card).getAtk(), ((MonsterCard) card).getDef());

            if (card instanceof PendulumMonsterCard) {
                addPendulumScales();
                addPendulumScaleValue(String.valueOf(((PendulumMonsterCard) card).getPendulumScale()));
            }
        }
        else if (card instanceof SpellTrapCard) {
            Attribute attribute = card.getFrame().equals(Frame.SPELL) ? Attribute.SPELL : Attribute.TRAP;
            addAttribute(attribute, 0);
        }

        return finalImage;
    }

    public void printCard(String cardName) throws IOException {
        String name = cardName.toLowerCase().replace(' ', '_');
        ImageIO.write(finalImage, "PNG", new File(getSavePath() + name + ".png"));
    }

    public abstract String getSavePath();
}
