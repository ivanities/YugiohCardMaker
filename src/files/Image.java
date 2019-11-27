package files;

import cardproperties.Frame;
import cardproperties.Level;
import constants.Coordinates;
import constants.FilePaths;
import constants.Scales;
import utils.MathUtils;

import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.getRegularCoordinateX;
import static cardproperties.Frame.DARK_SYNCHRO;
import static cardproperties.Frame.EXCEED;
import static utils.ImageUtils.loadImage;
import static utils.ImageUtils.toCompatibleImage;

public class Image {

    // Levels //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final BufferedImage STAR          = toCompatibleImage(loadImage(FilePaths.STAR));
    private static final BufferedImage RANK          = toCompatibleImage(loadImage(FilePaths.RANK));
    private static final BufferedImage NEGATIVE_STAR = toCompatibleImage(loadImage(FilePaths.NEGATIVE_STAR));

    private static final BufferedImage STAR_ANIME          = toCompatibleImage(loadImage(FilePaths.STAR_ANIME));
    private static final BufferedImage RANK_ANIME          = toCompatibleImage(loadImage(FilePaths.RANK_ANIME));
    private static final BufferedImage NEGATIVE_STAR_ANIME = toCompatibleImage(loadImage(FilePaths.NEGATIVE_STAR_ANIME));

    // Pendulums ///////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final BufferedImage PENDULUM_SCALE_BLUE = toCompatibleImage(loadImage(FilePaths.SCALE_BLUE));
    private static final BufferedImage PENDULUM_SCALE_RED = toCompatibleImage(loadImage(FilePaths.SCALE_RED));

    private static final BufferedImage PENDULUM_SCALE_ANIME = toCompatibleImage(loadImage(FilePaths.SCALE_ANIME));

    public static BufferedImage getLevelImage(Frame frame) {
        switch (frame) {
            case DARK_SYNCHRO:
                return NEGATIVE_STAR;
            case EXCEED:
                return RANK;
            default:
                return STAR;
        }
    }

    public static BufferedImage getAnimeLevelImage(Frame frame) {
        switch (frame) {
            case DARK_SYNCHRO:
                return NEGATIVE_STAR_ANIME;
            case EXCEED:
                return RANK_ANIME;
            default:
                return STAR_ANIME;
        }
    }

    public static int getRegularLevelX(int level, Frame frame, int currentStar) {
        if (level == Level.THIRTEEN) {
            return getRegularCoordinateX("Level13") - getCurrentStarPos(currentStar);
        }
        else if (frame.equals(DARK_SYNCHRO)
                || frame.equals(EXCEED)) {
            return getRegularCoordinateX("Rank") + getCurrentStarPos(currentStar);
        }
        else {
            return getRegularCoordinateX("Level") - getCurrentStarPos(currentStar);
        }
    }

    public static int getAnimeLevelX(int level, Frame frame, int gap) {
        if (frame.equals(EXCEED) && level <= Level.TEN) {
            return Coordinates.RANK_ANIME_X;
        }
        switch (level) {
            case Level.THIRTEEN:
            case Level.TWELVE:
                return Coordinates.LEVEL_12_ANIME_X;
            case Level.ELEVEN:
                return Coordinates.LEVEL_11_ANIME_X;
            case Level.TEN:
                return Coordinates.LEVEL_10_ANIME_X;
            case Level.NINE:
                return Coordinates.LEVEL_9_ANIME_X;
            default:
                if (MathUtils.isEven(level)) {
                    return Coordinates.LEVEL_EVEN_ANIME_X - ((level / 2 - 1) * (gap + Scales.LEVEL_ANIME));
                }
                else {
                    return Coordinates.LEVEL_ODD_ANIME_X - (((level - 1) / 2) * (gap + Scales.LEVEL_ANIME));
                }
        }
    }

    private static int getCurrentStarPos(int currentStar) {
        return currentStar * (Coordinates.LEVEL_GAP + Scales.LEVEL);
    }

    public static BufferedImage getPendulumScaleBlue() {
        return PENDULUM_SCALE_BLUE;
    }

    public static BufferedImage getPendulumScaleRed() {
        return PENDULUM_SCALE_RED;
    }

    public static BufferedImage getPendulumScaleAnime() {
        return PENDULUM_SCALE_ANIME;
    }
}
