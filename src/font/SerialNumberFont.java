package font;

public class SerialNumberFont extends YugiohFont {

    private static final float SCALE = 1f;
    private static final int INDEX_OFFSET = 48;

    public SerialNumberFont(String filePath) {
        super(filePath, INDEX_OFFSET, SCALE);
    }
}
