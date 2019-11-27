package newclasses.cardcomponents;

import cardproperties.Edition;
import cardproperties.EyeOfAnubisHologram;
import newclasses.card.Card;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.COORDINATES;

public class SerialTagComponent implements CardComponent {

    private static final String KEY = "eyeOfAnubisHologram";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        Point point = COORDINATES.get(KEY);
        BufferedImage img = getSerialTag(card.getEdition());
        ImageUtils.drawImage(g, img, point);
    }

    private BufferedImage getSerialTag(Edition edition) {
        if (edition.equals(Edition.UNLIMITED)) {
            return EyeOfAnubisHologram.SILVER.getHologram();
        }
        return EyeOfAnubisHologram.GOLD.getHologram();
    }
}
