package newclasses.cardcomponents;

import cardproperties.Attribute;
import cardproperties.Coordinates;
import cardproperties.Frame;
import constants.Scales;
import newclasses.card.Card;
import newclasses.card.MonsterCard;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AttributeComponent implements CardComponent {

    private static final String KEY = "Attribute";
    private static final int SCALE = Scales.ATTRIBUTE_SCALE;

    @Override
    public void drawImage(Graphics2D g, Card card) {
        Attribute attribute = getAttribute(card);
        Point point = Coordinates.COORDINATES.get(KEY);
        BufferedImage img = getImage(attribute);
        ImageUtils.drawImage(g, img, point);
    }

    private Attribute getAttribute(Card card) {
        if (card instanceof MonsterCard) {
            return ((MonsterCard) card).getAttribute();
        }
        return getSpellTrapAttribute(card.getFrame());
    }

    private Attribute getSpellTrapAttribute(cardproperties.Frame frame) {
        return Frame.SPELL.equals(frame) ? Attribute.SPELL : Attribute.TRAP;
    }

    private BufferedImage getImage(Attribute attribute) {
        BufferedImage img = null;
        try {
            img = ImageUtils.createThumbnail(SCALE, attribute.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
