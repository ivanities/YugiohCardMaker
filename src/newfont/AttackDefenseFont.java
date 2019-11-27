package newfont;

public class AttackDefenseFont extends YugiohFont {

    private static final String PACKAGE_NAME = "atk_def_text";
    private static final float SCALE = 1.14583333333f;
    private static final char[] CHARACTER_MAP = "0123456789X?".toCharArray();

    public AttackDefenseFont(FontFilename filename) {
        super(filename, SCALE);
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
