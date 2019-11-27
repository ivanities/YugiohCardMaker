package cardproperties;

import java.util.stream.IntStream;

public enum NewLevel {

    ZERO, ONE, TWO, THREE,
    FOUR, FIVE, SIX, SEVEN,
    EIGHT, NINE, TEN, ELEVEN,
    TWELVE, THIRTEEN;

    private static final int[] POSITIVE_LEVELS = IntStream.rangeClosed(0, 13).toArray();
    private static final int[] NEGATIVE_LEVELS = IntStream.rangeClosed(-13, 0).toArray();

    public boolean isEqualTo(int level) {
       return level == ordinal();
    }
}
