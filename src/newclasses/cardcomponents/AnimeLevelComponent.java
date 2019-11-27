package newclasses.cardcomponents;

import cardproperties.Frame;
import constants.Scales;
import files.Image;
import newclasses.card.MonsterCard;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.getAnimeCoordinateY;
import static cardproperties.Level.THIRTEEN;
import static constants.Coordinates.LEVEL_13_ANIME_GAP;
import static constants.Coordinates.LEVEL_ANIME_GAP;

public class AnimeLevelComponent implements CardComponent<MonsterCard> {

    @Override
    public void drawImage(Graphics2D g, MonsterCard card) {
        int level = card.getLevel();
        Frame frame = card.getFrame();

        BufferedImage img = Image.getAnimeLevelImage(frame);
        int gap = getGapForLevel(level);
        int startX = Image.getAnimeLevelX(level, frame, gap);
        int x;
        int y = getAnimeCoordinateY("Rank");

        for (int i = 0; i < level; ++i) {
            x = startX + (i * (gap + Scales.LEVEL_ANIME));
            g.drawImage(img, x, y, null);
        }
    }

    private int getGapForLevel(int level) {
        return level == THIRTEEN ? LEVEL_13_ANIME_GAP : LEVEL_ANIME_GAP;
    }
}
