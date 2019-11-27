package newfont;

public class CardIDFont extends YugiohFont {

    private static final String PACKAGE_NAME = "card_number_font";
    private static final float SCALE = 17/29f;
    private static final char[] CHARACTER_MAP = (
            "0123456789" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz- "
    ).toCharArray();

    public CardIDFont(FontFilename filename) {
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
