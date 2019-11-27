package font;

public class AnimeAttackDefenseFont extends YugiohFont {

    private static final float SCALE = 1.2f;
    private static final int INDEX_OFFSET = 48;

    private static final String ATK_EVEN = "AtkEven";
    private static final String DEF_EVEN = "DefEven";
    private static final int X_CAPITAL_UNICODE = 88;
    private static final int X_UNICODE = 120;
    private static final int QUESTION_MARK_UNICODE = 63;
    private static final int X = 58;
    private static final int QUESTION_MARK = 59;

    public AnimeAttackDefenseFont(String filePath) {
        super(filePath, INDEX_OFFSET, SCALE);
    }

    @Override
    public int getUnicodeValue(char c) {
        int unicodeValue = super.getUnicodeValue(c);
        if (unicodeValue == X_UNICODE || unicodeValue == X_CAPITAL_UNICODE) {
            return X;
        }
        else if (unicodeValue == QUESTION_MARK_UNICODE) {
            return QUESTION_MARK;
        }
        return unicodeValue;
    }

    @Override
    public int getOffsetX(String key, int length, int size) {
        if (key.equals(ATK_EVEN) || key.equals(DEF_EVEN)) {
            return (length / 2 - 1) * size;
        }
        else {
            return ((length - 1) / 2) * size;
        }
    }
}
