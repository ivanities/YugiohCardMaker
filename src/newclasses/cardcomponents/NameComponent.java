package newclasses.cardcomponents;

import cardproperties.Frame;
import cardproperties.Rarity;
import newclasses.card.Card;
import newfont.FontFilename;
import newfont.NameFont;
import newfont.YugiohFont;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static cardproperties.Coordinates.COORDINATES;

public class NameComponent implements CardComponent {

    private static final float DEFAULT_WIDTH_SCALE = 1;
    private static final float SCALE = 1.14583333333f;
    private static final int MAX_WIDTH = 610;
    private static final String KEY = "Name";
    private static final Map<FontFilename, YugiohFont> FONTS = buildFonts();

    private static Map<FontFilename, YugiohFont> buildFonts() {
        return new HashMap<FontFilename, YugiohFont>() {{
            put(FontFilename.NAME, new NameFont(FontFilename.NAME));
            put(FontFilename.NAME_WHITE, new NameFont(FontFilename.NAME_WHITE));
            put(FontFilename.NAME_YELLOW, new NameFont(FontFilename.NAME_YELLOW));
            put(FontFilename.NAME_RED, new NameFont(FontFilename.NAME_RED));
        }};
    }

    private YugiohFont getNameFont(Rarity rarity, Frame frame) {
        if (Rarity.ULTRA.equals(rarity) || Rarity.ULTIMATE.equals(rarity)) {
            if (Frame.EXCEED.equals(frame)) {
                return FONTS.get(FontFilename.NAME_RED);
            }
            return FONTS.get(FontFilename.NAME_YELLOW);
        }
        else if (Rarity.SUPER.equals(rarity)
                || Rarity.SECRET.equals(rarity)
                || Frame.EXCEED.equals(frame)
                || Frame.DARK_SYNCHRO.equals(frame)
                || Frame.WICKED_GOD.equals(frame)) {
            return FONTS.get(FontFilename.NAME_WHITE);
        }
        return FONTS.get(FontFilename.NAME);
    }

    private float getWidthScale(String name, YugiohFont font) {
        int maxWidth = MAX_WIDTH;
        int currentWidth = 0;
        int extraPixels = 0;

        for(char c : name.toCharArray()){
            BufferedImage img = font.getCharacterImage(c);
            currentWidth += (int) (img.getWidth() * SCALE);
            extraPixels++;
        }

        maxWidth -= extraPixels;

        if (currentWidth > maxWidth) {
            return ((float) maxWidth / (float) currentWidth);
        }
        return DEFAULT_WIDTH_SCALE;
    }

    @Override
    public void drawImage(Graphics2D g, Card card) {
        String name = card.getName();
        YugiohFont font = getNameFont(card.getRarity(), card.getFrame());
        Point point = COORDINATES.get(KEY);

        float widthScale = getWidthScale(name, font);
        int x = point.x;
        int y = point.y;
        int width;
        int height;

        for (char c : name.toCharArray()) {
            BufferedImage img = font.getCharacterImage(c);
            width = (int) (img.getWidth() * SCALE * widthScale);
            height = (int) (img.getHeight() * SCALE);

            g.drawImage(img, x, y, width, height, null);

            x += (int) ((img.getWidth() * SCALE * widthScale) + 1);
        }
    }
}
