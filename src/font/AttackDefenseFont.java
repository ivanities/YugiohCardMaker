package font;

public class AttackDefenseFont extends YugiohFont {

    private static final float SCALE = 1.14583333333f;
    private static final int INDEX_OFFSET = 48;

    private static final char QUESTION_MARK = '?';
    private static final char X_CAPITAL = 'X';
    private static final char X = 'x';
    private static final int QUESTION_MARK_UNICODE = 59;
    private static final int X_UNICODE = 58;

    public AttackDefenseFont(String filePath) {
        super(filePath, INDEX_OFFSET, SCALE);
    }

    @Override
    public int getUnicodeValue(char c) {
        if (c == X || c == X_CAPITAL) {
            return X_UNICODE;
        }
        else if (c == QUESTION_MARK) {
            return QUESTION_MARK_UNICODE;
        }
        return super.getUnicodeValue(c);
    }
}
