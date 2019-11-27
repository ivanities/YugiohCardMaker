package font;

public class NameFont extends YugiohFont {

    private static final float SCALE = 1.14583333333f;
    private static final int INDEX_OFFSET = 32;

    private static final float width_scale = 1;
    private static final int final_width = 610;
    private static final int current_width = 0;
    private static final int extra_bit = 0;

    public NameFont(String filePath) {
        super(filePath, INDEX_OFFSET, SCALE);
    }
}
