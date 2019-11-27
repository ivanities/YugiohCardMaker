package newclasses.imageUtils;

import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class PixelUtils {

    public static BufferedImage img1 = ImageUtils.loadImage("resources\\testMergePixels\\handOfNepthys.png");
    public static BufferedImage img2 = ImageUtils.loadImage("resources\\testMergePixels\\effectCard.png");

    public static int[][] pixels1 = convertTo2DWithoutUsingGetRGB(img1);
    public static int[][] pixels2= convertTo2DWithoutUsingGetRGB(img2);

    public static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();

        image = newImage;

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

//    public static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
//
//        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
//
//        Graphics2D g = newImage.createGraphics();
//        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
//        g.dispose();
//
//        image = newImage;
//
//        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//        final int width = image.getWidth();
//        final int height = image.getHeight();
//        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
//
//        int[][] result = new int[height][width];
//        int[] totalPixels = new int[height * width];
//        if (hasAlphaChannel) {
//            final int pixelLength = 4;
//
//            int w = image.getWidth();
//            int h = image.getHeight();
//
//            for (int i = 0; i < h; i++) {
//                for (int j = 0; j < w; j++) {
//                    int pixel = image.getRGB(j, i);
//
//                    int argb = 0;
//                    int red = (pixel >> 16) & 0xFF;
//                    int green = (pixel >> 8) & 0xFF;
//                    int blue = (pixel) & 0xFF;
//
//                    totalPixels[height * width + j] = share1(red, green, blue);
//                }
//            }
//
//            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
//                int argb = 0;
//                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
//                argb += ((int) pixels[pixel + 1] & 0xff); // blue
//                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
//                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
//                result[row][col] = argb;
//                col++;
//                if (col == width) {
//                    col = 0;
//                    row++;
//                }
//            }
//        } else {
//            final int pixelLength = 3;
//            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
//                int argb = 0;
//                argb += -16777216; // 255 alpha
//                argb += ((int) pixels[pixel] & 0xff); // blue
//                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
//                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
//                result[row][col] = argb;
//                col++;
//                if (col == width) {
//                    col = 0;
//                    row++;
//                }
//            }
//        }
//
//        return result;
//    }

//    public static int[][] mergePixels(int[][] pixels1, int[][] pixels2) {
//        int w = pixels1.length;
//        int h = pixels2[0].length;
//        int[][] newPixels = new int[w][h];
//
//        for(int y=0; y<h; y++) {
//            for (int x = 0; x < w; x++) {
//                if ((y > 98 && y < 98 + 632)
//                        && (x > 219 && x < 219 + 632)) {
//                    newPixels[x][y] = pixels1[x][y];
//                }
//                else {
//                    newPixels[x][y] = pixels2[x][y];
//                }
//            }
//        }
//
//        return newPixels;
//    }

    public static int[][] mergePixels(int[][] pixels1, int[][] pixels2) {
        for(int y=98; y < 98 + 632 ; y++) {
            for (int x = 219; x < 219 + 632; x++) {
                pixels2[x][y] = pixels1[x][y];
            }
        }
        return pixels2;
    }

    public static BufferedImage convertPixelsToImage(int[][] pixels) {
        int h = pixels.length;
        int w = pixels[0].length;

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                image.setRGB(x, y, pixels[y][x]);
            }
        }
        return image;
    }

    private static BufferedImage convert3BytePixelsToImage(int[][] data, int width, int height) {
        byte[] p = new byte[width*height*3];
        for(int i =0; i< height; ++i) {
            for(int j=0; j< width; ++j) {
                int pixelIndex = ((i*height)+j);
                Color c = new Color(data[i][j]);
                p[(pixelIndex*3)] = (byte) c.getRed();
                p[(pixelIndex*3) + 1] = (byte) c.getBlue();
                p[(pixelIndex*3) + 2] = (byte) c.getGreen();
            }
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        image.getRaster().setDataElements(0, 0, width, height, p);
        return image;
    }



    private static void copySrcIntoDstAt(final BufferedImage src,
                                         final BufferedImage dst, final int dx, final int dy) {
        int[] srcbuf = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
        int[] dstbuf = ((DataBufferInt) dst.getRaster().getDataBuffer()).getData();
        int width = src.getWidth();
        int height = src.getHeight();
        int dstoffs = dx + dy * dst.getWidth();
        int srcoffs = 0;
        for (int y = 0 ; y < height ; y++ , dstoffs+= dst.getWidth(), srcoffs += width ) {
            System.arraycopy(srcbuf, srcoffs , dstbuf, dstoffs, width);
        }
    }


}
