package newclasses.description.font;

import java.awt.image.BufferedImage;

public class PendulumScaleFont extends Font {

    private static final PendulumScaleFont regularFont = new PendulumScaleFont(1);
    private static final PendulumScaleFont animeFont = new PendulumScaleFont(32/28f);

    private PendulumScaleFont(float scale) {
        super("pendulum_font\\scale\\pendulum_scale_font.png", scale, 48);
    }

    public static PendulumScaleFont getRegularFont() {
        return regularFont;
    }

    public static PendulumScaleFont getAnimeFont() {
        return animeFont;
    }

    @Override
    public int getOffsetX(String key, int length, int size) {
        return 0;
    }

    @Override
    public BufferedImage getCharacterImage(char c) {
        int unicodeValue = (int) c;
        return getFont()[unicodeValue - getOffsetIndex()];
    }
}
