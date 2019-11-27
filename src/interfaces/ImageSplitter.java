package interfaces;

import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageSplitter {

    private static final int SEPARATOR = 0xffed0000;

    public static Map<Character, BufferedImage> split(BufferedImage fontMap, char[] characterMap) throws IOException {
        int start_y = 0;

        ArrayList<Rectangle> rectangles = new ArrayList<>();
        ArrayList<Rectangle> current_row;

        while (true) {
            if (start_y >= fontMap.getHeight()) {
                break;
            }
            current_row = getRow(fontMap, start_y);

            if (current_row.isEmpty()) {
                break;
            }
            rectangles.addAll(current_row);
            start_y += getMaxHeight(current_row) + 1;
        }

        BufferedImage[] split_result = new BufferedImage[rectangles.size()];

        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle rec = rectangles.get(i);
            split_result[i] = ImageUtils.toCompatibleImage(fontMap.getSubimage(rec.x, rec.y, rec.width, rec.height));
        }

        Map<Character, BufferedImage> charMap = new HashMap<>();

        for (int i = 0; i < characterMap.length; i++) {
            charMap.put(characterMap[i], split_result[i]);
        }
        return charMap;
    }

    public static BufferedImage[] split(BufferedImage fontMap) throws IOException{
        int start_y = 0;

        ArrayList<Rectangle> rectangles = new ArrayList<>();
        ArrayList<Rectangle> current_row;

        while (true) {
            if (start_y >= fontMap.getHeight()) {
                break;
            }
            current_row = getRow(fontMap, start_y);

            if (current_row.isEmpty()) {
                break;
            }
            rectangles.addAll(current_row);
            start_y += getMaxHeight(current_row) + 1;
        }

        BufferedImage[] split_result = new BufferedImage[rectangles.size()];

        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle rec = rectangles.get(i);
            split_result[i] = ImageUtils.toCompatibleImage(fontMap.getSubimage(rec.x, rec.y, rec.width, rec.height));
        }
        return split_result;
    }

    private static ArrayList<Rectangle> getRow(BufferedImage fontMap, int start_y){
        ArrayList<Rectangle> rectangles = new ArrayList<>();

        for (int x = 0; x < fontMap.getWidth(); x++) {
            Rectangle current_rectangle = getRectangle(fontMap, x, start_y);

            if (current_rectangle == null) {
                break;
            }
            else {
                rectangles.add(current_rectangle);
                x = current_rectangle.x + current_rectangle.width;
            }
        }
        return rectangles;
    }

    private static Rectangle getRectangle(BufferedImage fontMap, int start_x, int start_y){
        for (int x = start_x; x < fontMap.getWidth(); x++) {
            if (fontMap.getRGB(x, start_y) == SEPARATOR) {
                --x;
                for (int y = start_y; y < fontMap.getHeight(); y++) {
                    if (fontMap.getRGB(x, y) == SEPARATOR) {
                        --y;
                        return new Rectangle(start_x, start_y, (x + 1) - start_x, (y + 1) - start_y);
                    }
                }
            }
        }
        return null;
    }

    private static int getMaxHeight(ArrayList<Rectangle> rectangles){
        int maxHeight = 0;

        for (Rectangle rectangle : rectangles) {
            maxHeight = Math.max(maxHeight, rectangle.height);
        }
        return maxHeight;
    }
}
