package newclasses.cardcomponents;

import cardproperties.Frame;
import newclasses.card.MonsterCard;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.getRegularCoordinateY;
import static files.Image.getLevelImage;
import static files.Image.getRegularLevelX;

public class LevelComponent implements CardComponent<MonsterCard> {

    private static final String KEY = "Level";

    @Override
    public void drawImage(Graphics2D g, MonsterCard card) {
        int level = card.getLevel();
        Frame frame = card.getFrame();
        BufferedImage img = getLevelImage(frame);

        int x;
        int y = getRegularCoordinateY(KEY);

        for (int i = 0; i < level; i++) {
            x = getRegularLevelX(level, frame, i);
            g.drawImage(img, x, y, null);
        }
    }
}
