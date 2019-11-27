package newclasses.cardcomponents;

import cardproperties.CopyRightSymbol;
import cardproperties.Frame;
import constants.Scales;
import interfaces.Font;
import net.coobird.thumbnailator.Thumbnails;
import newclasses.card.Card;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static cardproperties.Coordinates.COORDINATES;

public class CreatorComponent implements CardComponent {

    private static final String KEY = "creator";
    private static final float SCALE = Scales.CREATOR;

    @Override
    public void drawImage(Graphics2D g, Card card) {
        String creator = getCreatorDisplay(card);
        Frame frame = card.getFrame();

        Point point = COORDINATES.get(KEY);
        Font font = useWhiteFont(frame) ? Font.CARD_ID_FONT_WHITE : Font.CARD_ID_FONT;

        int x = 0;
        for (char c : creator.toCharArray()) {
            BufferedImage img = font.getCharacterImage(c);
            BufferedImage thumbnail = getThumbnail(img);

            x += (int)(img.getWidth() * SCALE);
            g.drawImage(thumbnail, (point.x - x), point.y, null);
            x += 1;
        }

        addCopyRightSign(g, frame, x + 2);
    }

    private String getCreatorDisplay(Card card) {
        String creator = card.getYear() + " " + card.getCreator();
        return new StringBuilder(creator).reverse().toString();
    }

    private BufferedImage getThumbnail(BufferedImage img) {
        BufferedImage thumbnail = null;
        try {
            thumbnail = Thumbnails.of(img)
                    .scale(SCALE)
                    .asBufferedImage();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return thumbnail;
    }

    private void addCopyRightSign(Graphics2D g, Frame frame, int x) {
        Point point = COORDINATES.get(KEY);
        BufferedImage img = useWhiteFont(frame) ? CopyRightSymbol.WHITE.getImage() : CopyRightSymbol.BLACK.getImage();

        x += img.getWidth();
        g.drawImage(img, (point.x - x), point.y, null);
    }

    private boolean useWhiteFont(Frame frame) {
        return Frame.DARK_FRAMES.contains(frame);
    }
}
