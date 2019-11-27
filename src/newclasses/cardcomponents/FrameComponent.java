package newclasses.cardcomponents;

import cardproperties.Coordinates;
import cardproperties.Frame;
import newclasses.card.Card;
import newclasses.card.PendulumMonsterCard;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FrameComponent implements CardComponent {

    private static final String KEY = "Frame";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        boolean isPendulum = card instanceof PendulumMonsterCard;
        BufferedImage image = getFrameImage(card.getFrame(), isPendulum);

        Point point = Coordinates.getCoordinates(false, isPendulum).get(KEY);
        ImageUtils.drawImage(g, image, point);
    }

    private BufferedImage getFrameImage(Frame frame, boolean isPendulum) {
        if (isPendulum) {
            return frame.getPendulumImage();
        }
        else {
            return frame.getImage();
        }
    }
}
