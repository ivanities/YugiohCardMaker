package newclasses.cardcomponents;

import cardproperties.Coordinates;
import newclasses.card.Card;
import newclasses.card.PendulumMonsterCard;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PictureComponent implements CardComponent {

    private static final String KEY = "Picture";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        boolean isPendulum = card instanceof PendulumMonsterCard;
        BufferedImage img = getImage(card, isPendulum);

        Point point = Coordinates.getCoordinates(false, isPendulum).get(KEY);
        ImageUtils.drawImage(g, img, point);
    }

    private BufferedImage getImage(Card card, boolean isPendulum) {
        return isPendulum ? card.getPendulumImage() : card.getRegularImage();
    }
}
