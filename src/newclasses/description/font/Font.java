package newclasses.description.font;

import interfaces.ImageSplitter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static constants.FilePaths.FONTS;

public abstract class Font {

    private final BufferedImage[] font;
    private final String filename;
    private final float scale;
    private final int offsetIndex;

    protected Font(String filename, float scale, int offsetIndex) {
        font = createFont(filename);
        this.filename = filename;
        this.scale = scale;
        this.offsetIndex = offsetIndex;
    }

    public String getFilename() {
        return filename;
    }

    public BufferedImage[] getFont() {
        return font;
    }

    public float getScale() {
        return scale;
    }

    public int getOffsetIndex() {
        return offsetIndex;
    }

    private BufferedImage[] createFont(String filePath) {
        try {
            return ImageSplitter.split(ImageIO.read(new File(FONTS + filePath)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract int getOffsetX(String key, int length, int size);

    public abstract BufferedImage getCharacterImage(char c);
}
