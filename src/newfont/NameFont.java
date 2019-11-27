package newfont;

public class NameFont extends YugiohFont {

    private static final String PACKAGE_NAME = "name";
    private static final float SCALE = 1.14583333333f;
    private static final char[] CHARACTER_MAP = (
            " !\"#$%&'()*+,-./0123456789:;<=>?@" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`" +
            "abcdefghijklmnopqrstuvwxyz{|}~" +
            BULLET
    ).toCharArray();

    public NameFont(FontFilename filename) {
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
