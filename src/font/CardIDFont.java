package font;

public class CardIDFont extends YugiohFont {

    private static final char[] CHARACTER_MAP =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz- ".toCharArray();

    private static final float SCALE = 17/29f;
    private static final int INDEX_OFFSET = 48;
    private static final char DASH = '-';
    private static final char ZERO = '0';
    private static final char A_CAPITAL = 'A';
    private static final char Z_CAPITAL = 'Z';
    private static final char A = 'a';
    private static final char Z = 'z';

    public CardIDFont(String filePath) {
        super(filePath, INDEX_OFFSET, SCALE);
    }

    @Override
    public int getUnicodeValue(char c) {
        int unicodeValue = (int) c;
        if(unicodeValue == 45){
            unicodeValue = 110;
        }
        else if(unicodeValue < ZERO || unicodeValue > Z){
            unicodeValue = 111;
        }
        else if(A_CAPITAL <= unicodeValue && unicodeValue <= Z_CAPITAL){
            unicodeValue -= 7;
        }
        else if(A <= unicodeValue){
            unicodeValue -= 13;
        }
        return super.getUnicodeValue(c);
    }
}
