package newfont;

public class PendulumScaleFont extends YugiohFont {

    private static final String PACKAGE_NAME = "pendulum_font\\scale";
    private static final float SCALE = 1f;
    private static final char[] CHARACTER_MAP = "0123456789".toCharArray();

    public PendulumScaleFont() {
        super(FontFilename.PENDULUM, SCALE);
    }

    @Override
    char[] getCharacterMap() {
        return CHARACTER_MAP;
    }

    @Override
    String getPackageName() {
        return PACKAGE_NAME;
    }
}
