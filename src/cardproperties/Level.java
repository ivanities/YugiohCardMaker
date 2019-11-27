package cardproperties;

public class Level {

    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int ELEVEN = 11;
    public static final int TWELVE = 12;
    public static final int THIRTEEN = 13;

    private static final Integer[] POSITIVE_LEVELS = new Integer[] { ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX,
            SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN };

    private static final Integer[] NEGATIVE_LEVELS = new Integer[] { ZERO, -ONE, -TWO, -THREE, -FOUR, -FIVE, -SIX,
            -SEVEN, -EIGHT, -NINE, -TEN, -ELEVEN, -TWELVE, -THIRTEEN };

    public static Integer[] getAllPositiveLevels() {
        return POSITIVE_LEVELS;
    }
    
    public static Integer[] getAllNegativeLevels() {
        return NEGATIVE_LEVELS;
    }
}
