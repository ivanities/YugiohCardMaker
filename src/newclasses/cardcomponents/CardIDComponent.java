package newclasses.cardcomponents;

import cardproperties.Frame;
import constants.Scales;
import interfaces.Font;
import newclasses.card.Card;
import newclasses.card.PendulumMonsterCard;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.COORDINATES;
import static cardproperties.Coordinates.PENDULUM_COORDINATES;

public class CardIDComponent implements CardComponent {

    private static final String KEY = "CardID";
    private static final float SCALE = Scales.CARD_ID;

    @Override
    public void drawImage(Graphics2D g, Card card) {
        String cardID = card.getCardID();
        Frame frame = card.getFrame();

        if (card instanceof PendulumMonsterCard) {
            drawPendulumCardID(g, cardID);
        }
        else {
            drawNormalCardID(g, cardID, frame);
        }
    }

    private void drawNormalCardID(Graphics2D g, String cardID, Frame frame) {
        Point point = COORDINATES.get(KEY);
        interfaces.Font font = useWhiteFont(frame) ? interfaces.Font.CARD_ID_FONT_WHITE : interfaces.Font.CARD_ID_FONT;

        int x = 0;
        int y = point.y;

        for(int i = cardID.length() - 1; i >= 0; i--){
            BufferedImage img = font.getCharacterImage(cardID.charAt(i));

            x += (int)(img.getWidth() * SCALE);
            g.drawImage(img, (point.x - x), y, (int)(img.getWidth() * SCALE), (int)(img.getHeight() * SCALE), null);
            x += 1;
        }
    }

    private void drawPendulumCardID(Graphics2D g, String cardID) {
        Point point = PENDULUM_COORDINATES.get(KEY);
        interfaces.Font font = Font.CARD_ID_FONT;

        int x = 0;
        int y = point.y;

        for(char c : cardID.toCharArray()){
            BufferedImage img = font.getCharacterImage(c);

            g.drawImage(img, (point.x + x), y, (int) (img.getWidth() * SCALE), (int) (img.getHeight() * SCALE), null);
            x += (int)(img.getWidth() * Scales.CARD_ID) + 1;
        }
    }

    private boolean useWhiteFont(Frame frame) {
        return frame.equals(Frame.EXCEED)
                || frame.equals(Frame.DARK_SYNCHRO)
                || frame.equals(Frame.WICKED_GOD);
    }
}
