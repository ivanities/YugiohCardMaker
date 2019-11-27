package newclasses.old;

import cardproperties.Attribute;
import cardproperties.Frame;
import cardproperties.Level;
import constants.Coordinates;
import constants.FilePaths;
import constants.Scales;
import newclasses.CardController;
import newclasses.description.font.Font;
import newclasses.description.font.PendulumScaleFont;
import utils.ImageUtils;
import utils.MathUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.*;
import static files.Image.getAnimeLevelImage;
import static files.Image.getAnimeLevelX;
import static interfaces.Font.ATK_DEF;

public class NewAnimeCardGeneratorOld extends NewCardGeneratorOld {

    public NewAnimeCardGeneratorOld(CardController controller) {
        super(controller);
    }

    @Override
    protected void addLevel(int level, Frame frame) {
        BufferedImage img = getAnimeLevelImage(frame);
        int gap = level == Level.THIRTEEN ? Coordinates.LEVEL_13_ANIME_GAP : Coordinates.LEVEL_ANIME_GAP;
        int startX = getAnimeLevelX(level, frame, gap);
        int x;

        for (int i = 0; i < level; ++i) {
            x = startX + (i * (gap + Scales.LEVEL_ANIME));
            g.drawImage(img, x, getAnimeCoordinateY("Rank"), null);
        }
    }

    @Override
    protected void addAtkDef(String atk, String def) {
        drawAtkDef(atk, "Atk");
        drawAtkDef(def, "Def");
    }

    private void drawAtkDef(String value, String key) {
        final int size = 64;

        boolean isEven = MathUtils.isEven(value.length());
        key += isEven ? "Even" : "Odd";

        int x = getAnimeCoordinateX(key) - ATK_DEF.getOffsetX(isEven, value.length(), size);
        for (int i = 0; i < value.length(); i++) {
            BufferedImage img = ATK_DEF.getCharacterImage(value.charAt(i));
            // TODO add code coverage for 5 and 6 digits, store thinner variants of font
//            g.drawImage(img, x, getAnimeCoordinateY(key), null);
            int width = (int) (img.getWidth() * ATK_DEF.getScale());
            int height = (int) (img.getHeight() * ATK_DEF.getScale());

            ImageUtils.drawImage(g, img, x, getAnimeCoordinateY(key), width, height);
            x += size;
        }
    }

    @Override
    protected void addAttribute(Attribute attribute, int level) {
        if (attribute.equals(Attribute.SPELL) || attribute.equals(Attribute.TRAP)) {
            drawImage(attribute, "AttributeSpell");
        }
        else if (level == 13) {
            drawImage(attribute, "AttributeMonster13");
        }
        else if (level == 12) {
            drawImage(attribute, "AttributeMonster12");
        }
        else {
            drawImage(attribute, "AttributeMonster");
        }
    }

    @Override
    protected void addPendulumScales() {
        Point point = PENDULUM_COORDINATES.get("PendulumScaleAnime");
        ImageUtils.drawImage(g, files.Image.getPendulumScaleAnime(), point);
    }

    @Override
    public void addPendulumScaleValue(String pendulumScaleValue) {
        Font font = PendulumScaleFont.getAnimeFont();
        final float SCALE = font.getScale();
        Point pointBlueScale;
        Point pointRedScale;
        int startBlueX;
        int startRedX;
        int startY = cardproperties.Coordinates.getPendulumCoordinateY("PendulumScaleAnimeBlueValueOdd");

        if (Integer.parseInt(pendulumScaleValue) > 9) {
            pointBlueScale = PENDULUM_COORDINATES.get("PendulumScaleAnimeBlueValueEven");
            pointRedScale = PENDULUM_COORDINATES.get("PendulumScaleAnimeRedValueEven");
        }
        else {
            pointBlueScale = PENDULUM_COORDINATES.get("PendulumScaleAnimeBlueValueOdd");
            pointRedScale = PENDULUM_COORDINATES.get("PendulumScaleAnimeRedValueOdd");
        }

        startBlueX = (int) pointBlueScale.getX();
        startRedX = (int) pointRedScale.getX();

        for(int i = 0; i < pendulumScaleValue.length(); i++){
            BufferedImage img = font.getCharacterImage(pendulumScaleValue.charAt(i));

            g.drawImage(img, startBlueX, startY, (int) (img.getWidth() * SCALE), (int) (img.getHeight() * SCALE), null);
            g.drawImage(img, startRedX, startY, (int) (img.getWidth() * SCALE), (int) (img.getHeight() * SCALE), null);
            startBlueX += img.getWidth();
            startRedX +=  img.getWidth();
        }
    }

    @Override
    public String getSavePath() {
        return FilePaths.ANIME_TEMP_DIR;
    }
}
