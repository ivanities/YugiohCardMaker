package newclasses.description;

import interfaces.Font;
import newclasses.timeUtils.StopWatch;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DescriptionBuilder {

    private static final GraphicsConfiguration gfxConfig = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

    private Graphics2D g;
    private BufferedImage descriptionImage;

    private static final int DEFAULT_LINE_START_NUMBER = 1;

    private static final int DEFAULT_SPELL_TRAP_LINE_NUMBER = 8;
    private static final int DEFAULT_MONSTER_LINE_NUMBER = 6;
    private final int DEFAULT_LINE_NUMBER;

    private static final int DEFAULT_SPELL_TRAP_TEXT_BOX_HEIGHT = 517;
    private static final int DEFAULT_MONSTER_TEXT_BOX_HEIGHT = 387;
    private static final int DEFAULT_TEXT_BOX_WIDTH = 1741; // Also width of 1 line
    private final int DEFAULT_HEIGHT;
    private final int DEFAULT_WIDTH;
//    private static final int DEFAULT_HEIGHT = 322;
//    private static final int DEFAULT_WIDTH = 1449; // Also width of 1 line

    private static final int MONSTER_RESIZE_WIDTH = 697;
    private static final int MONSTER_RESIZE_HEIGHT = 155;

    private static final int SPELL_TRAP_RESIZE_WIDTH = 697;
    private static final int SPELL_TRAP_RESIZE_HEIGHT = 207;

    private static final int HEIGHT_INCREMENT = 65;
    private static final int WIDTH_INCREMENT = 292; // Actually 292.416020672px
    private static final int BLANK_SPACE_WIDTH = 12;

    private Font font = Font.NORMAL_FONT;

    private int lineNumber;
    private int height;
    private int width;
    private int resizeWidth;
    private int resizeHeight;

    private float effectFontScaleThin = 1;

    private static DescriptionBuilder monsterDescBuilder;
    private static DescriptionBuilder spellTrapDescBuilder;

    public DescriptionBuilder(int lineNumber, int height, int width, int resizeWidth, int resizeHeight) {
        DEFAULT_LINE_NUMBER = lineNumber;
        DEFAULT_HEIGHT = height;
        DEFAULT_WIDTH = width;
        this.resizeWidth = resizeWidth;
        this.resizeHeight = resizeHeight;
    }

    public static DescriptionBuilder getMonsterDescrBuilder() {
        if (monsterDescBuilder == null) {
            monsterDescBuilder = new DescriptionBuilder(
                    DEFAULT_MONSTER_LINE_NUMBER ,
                    DEFAULT_MONSTER_TEXT_BOX_HEIGHT,
                    DEFAULT_TEXT_BOX_WIDTH,
                    MONSTER_RESIZE_WIDTH,
                    MONSTER_RESIZE_HEIGHT
            );
        }
        return monsterDescBuilder;
    }

    public static DescriptionBuilder getSpellTrapDescrBuilder() {
        if (spellTrapDescBuilder == null) {
            spellTrapDescBuilder = new DescriptionBuilder(
                    DEFAULT_SPELL_TRAP_LINE_NUMBER,
                    DEFAULT_SPELL_TRAP_TEXT_BOX_HEIGHT,
                    DEFAULT_TEXT_BOX_WIDTH,
                    SPELL_TRAP_RESIZE_WIDTH,
                    SPELL_TRAP_RESIZE_HEIGHT
            );
        }
        return spellTrapDescBuilder;
    }

    private void resetVariables() {
        lineNumber = DEFAULT_LINE_START_NUMBER;
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
//        effectFontScaleThin = 1;
    }

    public void setEffectFontScaleThin(float effectFontScaleThin) {
        this.effectFontScaleThin = effectFontScaleThin;
    }

    public BufferedImage getDescriptionImage(String description) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        resetVariables();
        decrementValues();
//        incrementValues();
//        incrementValues();
//        incrementValues();
//        incrementValues();
        List<List<String>> lines = getFormattedDescription(description);
        descriptionImage = gfxConfig.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g = descriptionImage.createGraphics();
        ImageUtils.setRenderingHints(g);

        int x = 0;
        int y = 0;

//        addDescriptionFont(g, card.getDescription(), false, "", NORMAL_DESCRIPTION_FONT, Frame.NORMAL.getFrame());

        for (List<String> line : lines) {
//            TODO add materials textbox for fusion, synchro etc.
            if (lines.indexOf(line) == 0 || lines.indexOf(line) == lines.size() - 1) {
//            if (lines.indexOf(line) == lines.size() - 1) {
                drawLine(line, x, y, false);
            }
            else {
                drawLine(line, x, y, true);
            }
            y += HEIGHT_INCREMENT;
        }
        stopWatch.printElapsedTime("description generation");

//        return descriptionImage;

        return getScaledImage();
    }

    private BufferedImage getScaledImage() {
        boolean resizing = true;
        while (resizing) {
            int newWidth = (int) (descriptionImage.getWidth() * 0.5);
            int newHeight = (int) (descriptionImage.getHeight() * 0.5);

            if (newWidth > resizeWidth) {
                descriptionImage = ImageUtils.resize(descriptionImage, newWidth, newHeight);
            }
            else {
                resizing = false;
            }
        }

        descriptionImage = ImageUtils.resize(descriptionImage, resizeWidth, resizeHeight);
        return descriptionImage;
    }

    private void drawLine(List<String> line, int x, int y, boolean firstLine) {
        int[] blankSpaces = getBlankSpaceScaledWidthArray(line);

        for (String word : line) {
            for (char c : word.toCharArray()) {
                BufferedImage img = font.getCharacterImage(c);
                if (c == '\u25CF') {
                    g.drawImage(img, x, y, img.getWidth() + 2, img.getHeight(), null);
                    x += img.getWidth() + 2;
                }
                else {
                    g.drawImage(img, x, y, (int) (img.getWidth() * effectFontScaleThin) + 2, img.getHeight(), null);
                    x += (int) (img.getWidth() * effectFontScaleThin) + 2;
                }
//                TODO remove any scaling as much as possible! store multiple fonts of different sizes
//                g.drawImage(img, x, y, null);
//                x += img.getWidth();
//                TODO use below to create different scaled versions of the font
//                g.drawImage(img, x, y, (int) (img.getWidth() * 0.75) + 2, img.getHeight(), null);
//                x += (int) (img.getWidth() * 0.75) + 2;
            }
            if (firstLine) {
                if (line.indexOf(word) != line.size() - 1) {
                    x += blankSpaces[line.indexOf(word)];
                }
            }
            else {
                x += getBlankSpaceWidth();
            }
        }
    }

    private int[] getBlankSpaceScaledWidthArray(List<String> line) {
        int blankSpaceLeft = width - getLineWidthNoBlankSpaces(line);

        int length = line.size() -1 == 0 ? 1 : line.size() - 1;

        int newBlankSpaceWidth = (int) Math.floor(blankSpaceLeft/(length));

        int[] blankSpaceWidthArray = new int[length];
        for (int i = 0; i < blankSpaceWidthArray.length; i++)  {
            blankSpaceWidthArray[i] = newBlankSpaceWidth;
        }

        int difference = blankSpaceLeft - (newBlankSpaceWidth * (length));

        while (difference != 0) {
            for (int i = 0; i < blankSpaceWidthArray.length; i++)  {
                blankSpaceWidthArray[i]++;
                difference--;
                if (difference == 0) {
                    break;
                }
            }
        }
        return blankSpaceWidthArray;
    }

    private int getLineWidthNoBlankSpaces(List<String> line) {
        int lineWidth = 0;
        for (String word : line) {
            lineWidth += getWordWidth(word);
        }
        return lineWidth;
    }

    private List<List<String>> getFormattedDescription(String descriptionString) {
        List<String> description = getDescriptionAsList(descriptionString);
        int currentLineWidth = 0;
        int sum = 0;

        for (int i = 0; i < description.size(); i++) {
            if (canAddWord(description.get(i), currentLineWidth)) {
                currentLineWidth += getWordWidth(description.get(i)) + getBlankSpaceWidth();
            }
            else {
                if (!description.get(i).equals("\n")) {
                    // here concurrency by adding "/n" moves i to previous word
                    description.add(i, "\n");
                }
                sum += width;
                currentLineWidth = 0;
            }
        }
        sum += currentLineWidth == 0 ? 0 : currentLineWidth - getBlankSpaceWidth();

        if (sum > getTotalWidth()) {
            if (lineNumber > 5) {
                if (effectFontScaleThin > 0.75) {
                    effectFontScaleThin -= 0.05;
                    return getFormattedDescription(descriptionString);
                } else {
                    effectFontScaleThin = 1;
                }
            }
            lineNumber++;
            if (lineNumber > DEFAULT_LINE_NUMBER) {
                incrementValues();
            }
            return getFormattedDescription(descriptionString);
        }
        return convertToLines(description);
    }

    private List<String> getDescriptionAsList(String description) {
        List<String> descriptionList = new ArrayList<>();
        String[] descriptionArray = description.split("\n");

        for (int i = 0; i < descriptionArray.length; i++) {
            String[] words = descriptionArray[i].split(" ");
            for (String word : words) {
                descriptionList.add(word);
            }
            if (i < descriptionArray.length - 1) {
                descriptionList.add("\n");
            }
        }
        return descriptionList;
    }

    private boolean canAddWord(String word, int currentLineWidth) {
        return !(word.equals("\n") || getWordWidth(word) + currentLineWidth > width);
    }

    private int getWordWidth(String word) {
        int width = 0;

        for (char c : word.toCharArray()) {
            BufferedImage img = font.getCharacterImage(c);
            if (c == '\u25CF') {
                width += img.getWidth() + 2;
            }
            else {
                width += (img.getWidth() * effectFontScaleThin) + 2;
            }
        }
        return width;
    }

    private List<List<String>> convertToLines(List<String> description) {
        List<List<String>> lines = new ArrayList<>();
        int i = 0;

        for (String word : description) {
            if (lines.size() == i) {
                lines.add(new ArrayList<>());
            }
            if (!word.equals("\n")) {
                lines.get(i).add(word);
            }
            else {
                i++;
            }
        }
        return lines;
    }

    private int getBlankSpaceWidth() {
        return (int) (BLANK_SPACE_WIDTH * effectFontScaleThin);
    }

    private int getTotalWidth() {
        if (lineNumber > DEFAULT_LINE_NUMBER) {
            return width * lineNumber;
        }
        else {
            return width * DEFAULT_LINE_NUMBER;
        }
    }

    private void incrementValues() {
        height += HEIGHT_INCREMENT;
        width += WIDTH_INCREMENT;
    }

    private void decrementValues() {
        height -= HEIGHT_INCREMENT;
        width -= WIDTH_INCREMENT;
    }
}