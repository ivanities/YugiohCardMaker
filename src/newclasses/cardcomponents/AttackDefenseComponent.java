package newclasses.cardcomponents;

import constants.Coordinates;
import constants.FilePaths;
import constants.Scales;
import interfaces.Font;
import newclasses.card.Card;
import newclasses.card.MonsterCard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AttackDefenseComponent implements CardComponent {

    private static final interfaces.Font FONT = Font.REGULAR_ATK_DEF_FONT;
    private static final BufferedImage ATK_DEF_BAR = loadImage(FilePaths.ATK_DEF_BAR);
    private static final BufferedImage ATK_LABEL = loadImage(FilePaths.ATK);
    private static final BufferedImage DEF_LABEL = loadImage(FilePaths.DEF);
    private static final int SPACE = 19;
    private static final int DEFAULT_DIGITS = 4;
    private static final int DEFAULT_DIGIT_WIDTH = 18 + 1;

    @Override
    public void drawImage(Graphics2D g, Card card) {
        MonsterCard monsterCard = (MonsterCard) card;
        String atk = monsterCard.getAtk();
        String def = monsterCard.getDef();
        final int DIGITS = Math.max(atk.length(), def.length());

        drawAtkDefBar(g);
        addAtkDefValue(g, def, Coordinates.ATK_DEF_X);
        int startX = addAtkDefLabel(g, DIGITS, Coordinates.ATK_DEF_X, DEF_LABEL);
        addAtkDefValue(g, atk, startX - SPACE);
        addAtkDefLabel(g, DIGITS, startX - SPACE, ATK_LABEL);
    }

    private void drawAtkDefBar(Graphics2D g) {
        g.drawImage(ATK_DEF_BAR, 63, 1092, ATK_DEF_BAR.getWidth(), ATK_DEF_BAR.getHeight(), null);
    }

    private void addAtkDefValue(Graphics2D g, String atkDef, int startX) {
        for(int i = atkDef.length() - 1; i >= 0; i--) {
            BufferedImage img = FONT.getCharacterImage(atkDef.charAt(i));

            startX -= Scales.ATK_DEF_X;
            g.drawImage(img, startX, Coordinates.ATK_DEF_Y, Scales.ATK_DEF_X, Scales.ATK_DEF_Y, null);
            startX -= 1;
        }
    }

    private int addAtkDefLabel(Graphics2D g, int digits, int startX, BufferedImage label) {
        int numberOfDigitsMissing = digits < DEFAULT_DIGITS ? DEFAULT_DIGITS : digits;
        startX -= label.getWidth() + 1 + numberOfDigitsMissing * DEFAULT_DIGIT_WIDTH;
        g.drawImage(label, startX, Coordinates.ATK_DEF_Y, label.getWidth(), label.getHeight(), null);

        return startX;
    }

    private static BufferedImage loadImage(String filepath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filepath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
