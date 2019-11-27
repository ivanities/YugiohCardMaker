package newclasses.description;

import constants.FilePaths;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FontMapBuilder {

    private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

    private static final String[] CHARACTERS = {" ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", "0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?", "@", "A", "B", "C",
            "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z", "[", "\\", "]", "^", "_", "`", "a", "b", "c", "d", "e", "f", "g", "h", "i",
            "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z", "{", "|", "}", "~", "\u25CF"};

    private static final int SEPARATOR = 0xffed0000;

    public static void main(String[] args) {
        float effectFontScaleThin = 1;
        DescriptionBuilder builder = DescriptionBuilder.getMonsterDescrBuilder();

        for (int j = 0; j < 5; j++) {
            BufferedImage finalImage = GFX_CONFIG.createCompatibleImage(1500, 100, Transparency.TRANSLUCENT);
            Graphics2D g = finalImage.createGraphics();
            ImageUtils.setRenderingHints(g);
            g.setColor(new Color(SEPARATOR));

            builder.setEffectFontScaleThin(effectFontScaleThin);
            BufferedImage croppedImg = getCroppedImage(builder.getDescriptionImage("!"));

            g.drawLine(croppedImg.getWidth(), 0, croppedImg.getWidth(), 1);
            g.drawLine(0, 1, croppedImg.getWidth(), 1);

            int x = croppedImg.getWidth() + 1;

            for (int i = 1; i < CHARACTERS.length; i++) {
                BufferedImage img = builder.getDescriptionImage(CHARACTERS[i]);
                croppedImg = getCroppedImage(img);

                int horiz_x = x + croppedImg.getWidth();
                int vert_y = croppedImg.getHeight();

                g.drawImage(croppedImg, x, 0, null);
                g.drawLine(x - 1, 0, x - 1, vert_y);
                g.drawLine(horiz_x, 0, horiz_x, vert_y);
                g.drawLine(x, vert_y, horiz_x, vert_y);

                x += croppedImg.getWidth() + 1;
            }

            g.dispose();
            saveImage(finalImage, "normal_description_font_" + j);
            effectFontScaleThin -= 0.05;
        }
    }

    private static void saveImage(BufferedImage img, String filename) {
        try {
            ImageIO.write(img, "PNG", new File(FilePaths.TEMP_DIR + "fontMap\\" + filename + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage getCroppedImage(BufferedImage image) {
        int width = getCroppedWidth(image);
        int height = getCroppedHeight(image);

        BufferedImage croppedImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        croppedImage.getGraphics().drawImage(image, 0, 0, null) ;

        return croppedImage ;
    }

    private static int getCroppedWidth(BufferedImage image) {
        return getCroppedWidth(image, image.getWidth(), image.getHeight());
    }

    private static int getCroppedHeight(BufferedImage image) {
        return getCroppedHeight(image, image.getWidth(), image.getHeight());
    }

    private static int getCroppedWidth(BufferedImage image, int width, int height) {
        int croppedWidth = 0;
        for (int x = 0; x < width; x++) {
            if (x > 50) {
                break;
            }
            for (int y = 0; y < height; y++) {
                if (y > 50) {
                    break;
                }
                if (!isTransparent(image, x, y)) {
                    croppedWidth = x + 1;
                    break;
                }
            }
        }
        return croppedWidth;
    }

    private static int getCroppedHeight(BufferedImage image, int width, int height) {
        int croppedHeight = 0;
        for (int y = 0; y < height; y++) {
            if (y > 30) {
                break;
            }
            for (int x = 0; x < width; x++) {
                if (x > 30) {
                    break;
                }
                if (!isTransparent(image, x, y)) {
                    croppedHeight = y + 1;
                    break;
                }
            }
        }
        return croppedHeight;
    }

    private static boolean isTransparent(BufferedImage img, int x, int y) {
        int pixel = img.getRGB(x,y);
        return (pixel >> 24) == 0x00;
    }
}
