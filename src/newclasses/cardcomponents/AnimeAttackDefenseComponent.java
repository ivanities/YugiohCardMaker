package newclasses.cardcomponents;

import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newfont.AnimeAttackDefenseFont;
import utils.ImageUtils;
import utils.MathUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.getAnimeCoordinateX;
import static cardproperties.Coordinates.getAnimeCoordinateY;

public class AnimeAttackDefenseComponent implements CardComponent {

    private static final AnimeAttackDefenseFont FONT = new AnimeAttackDefenseFont();
    private static final String ATK_KEY = "Atk";
    private static final String DEF_KEY = "Def";
    private static final String EVEN_KEY = "Even";
    private static final String ODD_KEY = "Odd";
    private static final int WIDTH = 64;

    @Override
    public void drawImage(Graphics2D g, Card card) {
        MonsterCard monsterCard = (MonsterCard) card;
        drawAtk(g, monsterCard);
        drawDef(g, monsterCard);
    }

    private void drawAtk(Graphics2D g, MonsterCard monsterCard) {
        drawValue(g, monsterCard.getAtk(), ATK_KEY);
    }

    private void drawDef(Graphics2D g, MonsterCard monsterCard) {
        drawValue(g, monsterCard.getDef(), DEF_KEY);
    }

    private void drawValue(Graphics2D g, String value, String key) {
        boolean isEven = MathUtils.isEven(value.length());
        key += isEven ? EVEN_KEY : ODD_KEY;

        int x = getAnimeCoordinateX(key) - getOffsetX(isEven, value.length(), WIDTH);
        for (char c : value.toCharArray()) {
            BufferedImage img = FONT.getCharacterImage(c);
            // TODO add code coverage for 5 and 6 digits, store thinner variants of font
//            g.drawImage(img, x, getAnimeCoordinateY(key), null);
            int width = (int) (img.getWidth() * FONT.getScale());
            int height = (int) (img.getHeight() * FONT.getScale());

            ImageUtils.drawImage(g, img, x, getAnimeCoordinateY(key), width, height);
            x += WIDTH;
        }
    }

    public int getOffsetX(boolean isEven, int length, int size) {
        if (isEven) {
            return (length / 2 - 1) * size;
        }
        else {
            return ((length - 1) / 2) * size;
        }
    }
}
