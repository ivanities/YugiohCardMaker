package font;

public class EffectFont extends YugiohFont {

    private static final float SCALE = 1.14583333333f;
    private static final int INDEX_OFFSET = 32;

    private static final char BULLET = '\u25cf';
    private static final char BULLET_UNICODE = 127;

    public EffectFont(String filePath) {
        super(filePath, INDEX_OFFSET, SCALE);
    }

    @Override
    public int getUnicodeValue(char c) {
        if (c == BULLET) {
            return BULLET_UNICODE;
        }
        return super.getUnicodeValue(c);
    }
}
