package font;

import interfaces.ImageSplitter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static constants.FilePaths.FONTS;

public abstract class YugiohFont {

    private static final YugiohFont NAME_FONT = new NameFont("name\\name_font.png");
    private static final YugiohFont NAME_WHITE_FONT = new NameFont("name\\name_white_font.png");
    private static final YugiohFont NAME_YELLOW_FONT = new NameFont("name\\name_yellow_font_shelfed_for_now.png");
    private static final YugiohFont NAME_RED_FONT = new NameFont("name\\name_red_font.png");
    private static final YugiohFont ATK_DEF_FONT = new AttackDefenseFont("atk_def_text\\new_atk_def_font.png");
    private static final YugiohFont ANIME_ATK_DEF_FONT = new AnimeAttackDefenseFont("anime_atk_def_text\\atk_def_font.png");

    private final int indexOffset;
    private final float scale;
    private final BufferedImage[] font;

    public YugiohFont(String filePath, int indexOffset, float scale) {
        this.font = createFont(filePath);
        this.indexOffset = indexOffset;
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public BufferedImage getCharacterImage(char c) {
        return font[getUnicodeValue(c) - indexOffset];
    }

    public int getOffsetX(String key, int length, int size) {
        throw new UnsupportedOperationException();
    }

    public int getUnicodeValue(char c) {
        return (int) c;
    }

    private static BufferedImage[] createFont(String filePath) {
        try {
            return ImageSplitter.split(ImageIO.read(new File(FONTS + filePath)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
