package newclasses.cardcomponents;

import newclasses.card.PendulumMonsterCard;
import newfont.PendulumScaleFont;
import newfont.YugiohFont;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.PENDULUM_COORDINATES;

public class PendulumScalesComponent implements CardComponent<PendulumMonsterCard> {

    private static final String BLUE_SCALE_VALUE_KEY = "PendulumScaleBlueValue";
    private static final String RED_SCALE_VALUE_KEY = "PendulumScaleRedValue";
    private static final String BLUE_SCALE_KEY = "PendulumScaleBlue";
    private static final String RED_SCALE_KEY = "PendulumScaleRed";
    private static final YugiohFont FONT = new PendulumScaleFont();
    private static final float SCALE = FONT.getScale();

    @Override
    public void drawImage(Graphics2D g, PendulumMonsterCard card) {
        String value = String.valueOf(card.getPendulumScale());
        drawPendulumScales(g);
        drawPendulumScaleValue(g, value, BLUE_SCALE_VALUE_KEY);
        drawPendulumScaleValue(g, value, RED_SCALE_VALUE_KEY);
    }

    private void drawPendulumScales(Graphics2D g) {
        Point point = PENDULUM_COORDINATES.get(BLUE_SCALE_KEY);
        ImageUtils.drawImage(g, files.Image.getPendulumScaleBlue(), point);

        point = PENDULUM_COORDINATES.get(RED_SCALE_KEY);
        ImageUtils.drawImage(g, files.Image.getPendulumScaleRed(), point);
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
