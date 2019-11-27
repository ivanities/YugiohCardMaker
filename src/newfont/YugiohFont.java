package newfont;

import interfaces.ImageSplitter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static constants.FilePaths.FONTS;

public abstract class YugiohFont {

    static final char BULLET = '\u25cf';

    private final Map<Character, BufferedImage> font;
    private final float scale;

    public YugiohFont(FontFilename filename, float scale) {
        this.font = createFont(getPackageName() + File.separator + filename.getName(), getCharacterMap());
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public BufferedImage getCharacterImage(char key) {
        return font.get(key);
    }

    private static Map<Character, BufferedImage> createFont(String filePath, char[] characterMap) {
        try {
            return ImageSplitter.split(ImageIO.read(new File(FONTS + filePath)), characterMap);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    abstract char[] getCharacterMap();

    abstract String getPackageName();
}
