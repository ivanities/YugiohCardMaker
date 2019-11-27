package newclasses.description.font;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class DescriptionFont extends Font {

    public static final String NORMAL = "NORMAL";
    public static final String EFFECT = "EFFECT";

    private static final char BULLET_POINT = '\u25cf';
    private static final int[] SIZES = new int[] {0, 1, 2, 3, 4};
    private static final int[] LINE_HEIGHTS = new int[] {25, 22, 19, 17, 15, 14};
    private static final int[] THINNESS = new int[] {0, 1, 2, 3, 4};
    private static final Map<String, Map<Integer, Map<Integer, DescriptionFont>>> FONT_MAP = new HashMap<>();

    private final int LINE_HEIGHT;
    private final int MAX_LINE_NUMBER;

    private DescriptionFont(String filename, int lineHeight, int maxLineNumber) {
        super(filename, 1.14583333333f, 32);
        LINE_HEIGHT = lineHeight;
        MAX_LINE_NUMBER = maxLineNumber;
    }

    public int getLineHeight() {
        return LINE_HEIGHT;
    }

    public int getMaxLineNumber() {
        return MAX_LINE_NUMBER;
    }

    @Override
    public int getOffsetX(String key, int length, int size) {
        return 0;
    }

    @Override
    public BufferedImage getCharacterImage(char c) {
        int unicodeValue = (int) c;
        if (c == BULLET_POINT) {
            unicodeValue = 127;
        }
        return getFont()[unicodeValue - getOffsetIndex()];
    }

    public static void initializeNormalFontMap() {
        Map<Integer, Map<Integer, DescriptionFont>> fontMap = new HashMap<>();
        for (int size : SIZES) {
            Map<Integer, DescriptionFont> thinFonts = new HashMap<>();
            for (int thinSize : THINNESS) {
                DescriptionFont font = createFont(createNormalFilename(size, thinSize), LINE_HEIGHTS[size], size);
                thinFonts.put(thinSize, font);
            }
            fontMap.put(size, thinFonts);
        }
        FONT_MAP.put(NORMAL, fontMap);
    }

    public static void initializeFontMap() {
        Map<Integer, Map<Integer, DescriptionFont>> fontMap = new HashMap<>();
        for (int size : SIZES) {
            Map<Integer, DescriptionFont> thinFonts = new HashMap<>();
            for (int thinSize : THINNESS) {
                DescriptionFont font = createFont(createFilename(size, thinSize), LINE_HEIGHTS[size], size);
                thinFonts.put(thinSize, font);
            }
            fontMap.put(size, thinFonts);
        }
        FONT_MAP.put(EFFECT, fontMap);
    }

    private static String createFilename(int size, int thinSize) {
        return "fontsize_" + size + "\\" + "effect_description_font_" + thinSize + ".png";
    }

    private static String createNormalFilename(int size, int thinSize) {
        return "fontsize_" + size + "\\" + "normal_description_font_" + thinSize + ".png";
    }

    private static DescriptionFont createFont(String filepath, int lineHeight, int maxLineNumber) {
        return new DescriptionFont("description_text\\" + filepath, lineHeight, maxLineNumber);
    }

    public static DescriptionFont getFont(String type, int size, int thinSize) {
        return FONT_MAP.get(type).get(size).get(thinSize);
    }
}
