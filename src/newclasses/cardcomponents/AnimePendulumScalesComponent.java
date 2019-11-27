package newclasses.cardcomponents;

import newclasses.card.Card;
import newclasses.card.PendulumMonsterCard;
import newfont.AnimePendulumScaleFont;
import newfont.YugiohFont;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.PENDULUM_COORDINATES;

public class AnimePendulumScalesComponent implements CardComponent {

    private static final String BLUE_SCALE_KEY = "PendulumScaleAnimeBlueValue";
    private static final String RED_SCALE_KEY = "PendulumScaleAnimeRedValue";
    private static final String SCALE_KEY = "PendulumScaleAnime";
    private static final YugiohFont FONT = new AnimePendulumScaleFont();
    private static final float SCALE = FONT.getScale();

    @Override
    public void drawImage(Graphics2D g, Card card) {
        String value = String.valueOf(((PendulumMonsterCard) card).getPendulumScale());
        drawPendulumScales(g);
        drawPendulumScaleValue(g, value, BLUE_SCALE_KEY);
        drawPendulumScaleValue(g, value, RED_SCALE_KEY);
    }

    private void drawPendulumScales(Graphics2D g) {
        Point point = PENDULUM_COORDINATES.get(SCALE_KEY);
        ImageUtils.drawImage(g, files.Image.getPendulumScaleAnime(), point);
    }

    private void drawPendulumScaleValue(Graphics2D g, String value, String key) {
        Point point = getPoint(value, key);
        int x = point.x;
        int y = point.y;

        for (char c : value.toCharArray()) {
            BufferedImage img = FONT.getCharacterImage(c);

            g.drawImage(img, x, y, (int) (img.getWidth() * SCALE), (int) (img.getHeight() * SCALE), null);
            x += img.getWidth();
        }
    }

    private Point getPoint(String pendulumScaleValue, String key) {
        if (Integer.parseInt(pendulumScaleValue) > 9) {
            return PENDULUM_COORDINATES.get(key + "Even");
        }
        return PENDULUM_COORDINATES.get(key + "Odd");
    }
}
