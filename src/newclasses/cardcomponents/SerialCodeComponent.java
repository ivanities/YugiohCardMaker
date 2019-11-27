package newclasses.cardcomponents;

import cardproperties.Frame;
import interfaces.Font;
import newclasses.card.Card;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.COORDINATES;

public class SerialCodeComponent implements CardComponent {

    private static final String NUMBER_KEY = "serialNumber";
    private static final String TEXT_KEY = "serialText";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        String serialCode = card.getSerialCode();
        Frame frame = card.getFrame();

        if (!serialCode.equals("")) {
            if (serialCode.matches("[0-9]+")) {
                drawSerialNumber(g, serialCode, frame);
            }
            else {
                drawSerialText(g, serialCode, frame);
            }
        }
    }

    private void drawSerialNumber(Graphics2D g, String serialNumber, Frame frame) {
        Point point = COORDINATES.get(NUMBER_KEY);
        Font font = useWhiteFont(frame) ? Font.SERIAL_NUMBER_FONT_WHITE : Font.SERIAL_NUMBER_FONT;

        int x = 0;
        int y = point.y;

        for (char c : serialNumber.toCharArray()) {
            BufferedImage img = font.getCharacterImage(c);

            g.drawImage(img, (point.x + x), y, null);
            x += img.getWidth() + 2;
        }
    }

    private void drawSerialText(Graphics2D g, String serialText, Frame frame) {
        Point point = COORDINATES.get(TEXT_KEY);
        Font font = useWhiteFont(frame) ? Font.CARD_ID_FONT_WHITE : Font.CARD_ID_FONT;
        float scale = font.getScale();

        int x = 0;
        int y = point.y;

        for (char c : serialText.toCharArray()) {
            BufferedImage img = font.getCharacterImage(c);

            g.drawImage(img, (point.x + x), y, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
            x += (int) (img.getWidth() * scale) + 1;
        }
    }

    private boolean useWhiteFont(Frame frame) {
        return frame.equals(Frame.EXCEED)
                || frame.equals(Frame.DARK_SYNCHRO)
                || frame.equals(Frame.WICKED_GOD);
    }
}
