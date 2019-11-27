package utils;

import cardproperties.interfaces.StyleImage;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    private static final GraphicsConfiguration GRAPHICS_CONFIG = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

    public static BufferedImage createThumbnail(int width, BufferedImage image) throws IOException {
        return createThumbnail(image, (double) width/image.getWidth());
    }

    private static BufferedImage createThumbnail(BufferedImage image, double scale) throws IOException {
        return Thumbnails.of(image)
                .scale(scale)
                .asBufferedImage();
    }

    public static BufferedImage toCompatibleImage(BufferedImage image) {
        if (image.getColorModel().equals(GRAPHICS_CONFIG.getColorModel())) {
            return image;
        }
        BufferedImage newImage = createCompatibleImage(image, image.getWidth(), image.getHeight());

        Graphics2D g2d = newImage.createGraphics();
        setRenderingHints(g2d);
        drawImage(g2d, image, image.getWidth(), image.getHeight());
        g2d.dispose();

        return newImage;
    }

    public static BufferedImage toCompatibleImage(BufferedImage image, int scaleX, int scaleY) {
        if (image.getColorModel().equals(GRAPHICS_CONFIG.getColorModel())) {
            return image;
        }
        BufferedImage new_image = createCompatibleImage(image, scaleX, scaleY);

        Graphics2D g2d = new_image.createGraphics();
        setRenderingHints(g2d);
        g2d.drawRenderedImage(image, getScaleTransform((float) scaleX/image.getWidth(), (float) scaleY/image.getHeight()));
        g2d.dispose();

        return new_image;
    }

    private static AffineTransform getScaleTransform(float scaleX, float scaleY) {
        AffineTransform transform = new AffineTransform();
        transform.scale(scaleX, scaleY);
        return transform;
    }

    public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
        return GRAPHICS_CONFIG.createCompatibleImage(width, height, image.getTransparency());
    }

    public static BufferedImage createNewCompatibleImage(int width, int height, int transparency) {
        return GRAPHICS_CONFIG.createCompatibleImage(width, height, transparency);
    }

    public static BufferedImage loadImage(String filePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(sanitiseFilePath(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static String sanitiseFilePath(String filePath) {
        return filePath.toLowerCase().replace(' ', '_');
    }

    public static void setRenderingHints(Graphics2D g2d) {
        ///////////////////////////////////////////////////ANTI-ALIAS///////////////////////////////////////////////////
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ///////////////////////////////////////////////////ANTI-ALIAS///////////////////////////////////////////////////
    }

    public static BufferedImage resize(BufferedImage img, int newSize) {
        return resize(img, newSize, newSize);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        BufferedImage newImg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = newImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, img.getWidth(), img.getHeight(), null);
        g.dispose();
        return newImg;
    }

    public static void drawImage(Graphics2D g2d, BufferedImage image, Point point) {
        g2d.drawImage(image, (int) point.getX(), (int) point.getY(), null);
    }

    public static void drawImage(Graphics2D g2d, BufferedImage image, Point point, int scale) {
        drawImage(g2d, image, (int) point.getX(), (int) point.getY(), scale, scale);
    }

    public static void drawImage(Graphics2D g2d, BufferedImage image) {
        drawImage(g2d, image, image.getWidth(), image.getHeight());
    }

    public static void drawImage(Graphics2D g2d, BufferedImage image, int width, int height) {
        drawImage(g2d, image, 0, 0, width, height);
    }

    public static void drawImage(Graphics2D g2d, BufferedImage image, int posX, int posY, int width, int height) {
        g2d.drawImage(image, posX, posY, width, height, null);
    }

    public static BufferedImage createCompatibleImage(String dirPath, StyleImage styleImage) {
        return toCompatibleImage(loadImage(dirPath + styleImage.getDisplayString()  + ".png"));
    }
}
