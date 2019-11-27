package newfont;

public class EditionFont extends YugiohFont {

    private static final String PACKAGE_NAME = "Edition_font";
    private static final float SCALE = 18/24f;
    private static final char[] CHARACTER_MAP = (
            "0123456789" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz- "
    ).toCharArray();

    public EditionFont(FontFilename filename) {
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
