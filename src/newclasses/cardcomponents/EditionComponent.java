package newclasses.cardcomponents;

import cardproperties.Edition;
import cardproperties.Frame;
import constants.Scales;
import interfaces.Font;
import newclasses.card.Card;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.COORDINATES;

public class EditionComponent implements CardComponent {

    private static final String KEY = "Edition";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        Frame frame = card.getFrame();
        Edition edition = card.getEdition();

        Point point = COORDINATES.get(KEY);
        Font font = useWhiteFont(frame) ? Font.EDITION_FONT_WHITE : Font.EDITION_FONT;
        String display = edition.getDisplayString();
        int x = 0;
        float scale;

        for(int i = 0; i < display.length(); i++){
            BufferedImage img = font.getCharacterImage(display.charAt(i));
            scale = editionNumberCheck(font, display, i);

            g.drawImage(img, (point.x + x), point.y, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
            x += (int) (img.getWidth() * scale) + 1;
        }
    }

    private float editionNumberCheck(Font font, String edition, int i) {
        if (i != 0 && edition.substring(i-1, i).matches("[0-9]+")) {
            return Scales.EDITION_SMALL_SCALE;
        }
        else if (i != 0 && i != 1 && edition.substring(i-2, i-1).matches("[0-9]+")) {
            return Scales.EDITION_SMALL_SCALE;
        }
        else {
            return font.getScale();
        }
    }

    private boolean useWhiteFont(Frame frame) {
        return frame.equals(Frame.EXCEED)
                || frame.equals(Frame.DARK_SYNCHRO)
                || frame.equals(Frame.WICKED_GOD);
    }
}
