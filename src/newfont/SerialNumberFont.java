package newfont;

public class SerialNumberFont extends YugiohFont {

    private static final String PACKAGE_NAME = "serial_number_text";
    private static final float SCALE = 1f;
    private static final char[] CHARACTER_MAP = "0123456789".toCharArray();

    public SerialNumberFont(FontFilename filename) {
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
