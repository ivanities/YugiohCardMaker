package newclasses.cardcomponents;

import cardproperties.Attribute;
import cardproperties.Coordinates;
import cardproperties.Frame;
import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.SpellTrapCard;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimeAttributeComponent implements CardComponent<Card> {

    private static final String SPELL_KEY = "AttributeSpell";
    private static final String MONSTER_KEY = "AttributeMonster";
    private static final String MONSTER_12_KEY = "AttributeMonster12";
    private static final String MONSTER_13_KEY = "AttributeMonster13";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        if (card instanceof MonsterCard) {
            drawImage(g, (MonsterCard) card);
        }
        else {
            drawImage(g, (SpellTrapCard) card);
        }
    }

    private void drawImage(Graphics2D g, MonsterCard card) {
        int level = Math.abs((card.getLevel()));
        drawImage(g, card.getAttribute(), level);
    }

    private void drawImage(Graphics2D g, SpellTrapCard card) {
        Attribute attribute = getSpellTrapAttribute(card.getFrame());
        drawImage(g, attribute, 0);
    }

    private Attribute getSpellTrapAttribute(Frame frame) {
        return Frame.SPELL.equals(frame) ? Attribute.SPELL : Attribute.TRAP;
    }

    private void drawImage(Graphics2D g, Attribute attribute, int level) {
        Point point = Coordinates.ANIME_COORDINATES.get(getKey(attribute, level));
        BufferedImage img = attribute.getAnimeImage();
        ImageUtils.drawImage(g, img, point);
    }

    private String getKey(Attribute attribute, int level) {
        if (isSpellOrTrap(attribute)) {
            return SPELL_KEY;
        }
        else if (level == 13) {
            return MONSTER_13_KEY;
        }
        else if (level == 12) {
            return MONSTER_12_KEY;
        }
        return MONSTER_KEY;
    }

    private boolean isSpellOrTrap(Attribute attribute) {
        return attribute.equals(Attribute.SPELL) || attribute.equals(Attribute.TRAP);
    }
}
