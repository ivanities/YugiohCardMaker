package newclasses.description;

import cardproperties.Frame;
import constants.Dimensions;
import newclasses.description.font.DescriptionFont;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DescriptionBuilderV2 {

    enum BuilderType {
        MONSTER,
        SPELL_TRAP,
        PENDULUM
    }

    private static DescriptionBuilderV2 monsterDescBuilder;
    private static DescriptionBuilderV2 spellTrapDescBuilder;

    private static final String BLANK_SPACE = " ";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final int MONSTER_LINE_NUMBER = 6;
    private static final int SPELL_TRAP_LINE_NUMBER = 8;
    private static final int PENDULUM_LINE_NUMBER = 5;
    private static final int TEXT_BOX_WIDTH_1_LINE = 116;
    private static final int MONSTER_WIDTH = 697;
    private static final int MONSTER_HEIGHT = 155;
    private static final int SPELL_TRAP_WIDTH = 697;
    private static final int SPELL_TRAP_HEIGHT = 207;

    private final int DEFAULT_LINE_NUMBER;

    private DescriptionFont font;
    private DescriptionFont materialFont;
    private BufferedImage descriptionImage;
    private Graphics2D g;
    private int lineNumber;
    private int materialLineNumber;
    private int lineHeight;
    private int textBoxHeight;
    private int textBoxWidth;
    private int fontSize;
    private int effectFontScaleThin;
    private BuilderType builderType;
    private List<List<String>> materialLines;
    private boolean showMaterials;

    private DescriptionBuilderV2(BuilderType builderType, int lineNumber, int textBoxHeight, int textBoxWidth) {
        this.builderType = builderType;
        DEFAULT_LINE_NUMBER = lineNumber;
        this.textBoxHeight = textBoxHeight;
        this.textBoxWidth = textBoxWidth;
    }

    public static DescriptionBuilderV2 getMonsterDescBuilder() {
        if (monsterDescBuilder == null) {
            monsterDescBuilder = new DescriptionBuilderV2(
                    BuilderType.MONSTER,
                    MONSTER_LINE_NUMBER,
                    MONSTER_HEIGHT,
                    MONSTER_WIDTH
            );
        }
        return monsterDescBuilder;
    }

    public static DescriptionBuilderV2 getSpellTrapDescBuilder() {
        if (spellTrapDescBuilder == null) {
            spellTrapDescBuilder = new DescriptionBuilderV2(
                    BuilderType.SPELL_TRAP,
                    SPELL_TRAP_LINE_NUMBER,
                    SPELL_TRAP_HEIGHT,
                    SPELL_TRAP_WIDTH
            );
        }
        return spellTrapDescBuilder;
    }

    public static DescriptionBuilderV2 getPendulumDescBuilder() {
        if (spellTrapDescBuilder == null) {
            spellTrapDescBuilder = new DescriptionBuilderV2(
                    BuilderType.PENDULUM,
                    PENDULUM_LINE_NUMBER,
                    Dimensions.TEXT_BOX_PENDULUM_HEIGHT,
                    Dimensions.TEXT_BOX_PENDULUM_WIDTH
            );
        }
        return spellTrapDescBuilder;
    }

    private void resetVariables() {
        lineNumber = 1;
        fontSize = 0;
        effectFontScaleThin = 0;
        materialLines = null;
        showMaterials = true;
        materialLineNumber = 1;
        descriptionImage = ImageUtils.createNewCompatibleImage(textBoxWidth, textBoxHeight, Transparency.TRANSLUCENT);
        g = descriptionImage.createGraphics();
        ImageUtils.setRenderingHints(g);
    }

    public BufferedImage getDescriptionImage(Frame frame, String description) {
        resetVariables();

        List<List<String>> descriptionLines = getFormattedDescription(frame, description, null, false);
        drawLines(descriptionLines, 0, 0, font);

        return descriptionImage;
    }

    public BufferedImage getDescriptionWithMaterialsImage(Frame frame, String description, String materials) {
        resetVariables();

        List<List<String>> descriptionLines = getFormattedDescription(frame, description, materials, true);
        int y = drawLines(materialLines, 0, 0, materialFont);
        drawLines(descriptionLines, 0, y, font);

        return descriptionImage;
    }

    private int drawLines(List<List<String>> lines, int x, int y, DescriptionFont font) {
        for (List<String> line : lines) {
            if (lines.indexOf(line) == lines.size() - 1) {
                drawLine(line, x, y, true, font);
            }
            else {
                drawLine(line, x, y, false, font);
            }
            y += lineHeight;
        }
        return y;
    }

    private void drawLine(List<String> line, int x, int y, boolean lastLine, DescriptionFont font) {
        int[] blankSpaces = getBlankSpaceScaledWidthArray(line, font);

        int i = 0;
        for (String word : line) {
            for (char c : word.toCharArray()) {
                BufferedImage img = font.getCharacterImage(c);
                g.drawImage(img, x, y, null);
                x += img.getWidth();
            }
            if (!lastLine) {
                if (i != line.size() - 1) {
                    x += blankSpaces[i];
                }
            }
            else {
                x += getBlankSpaceWidth();
            }
            i++;
        }
    }

    private int[] getBlankSpaceScaledWidthArray(List<String> line, DescriptionFont font) {
        int blankSpaceLeft = textBoxWidth - getLineWidthNoBlankSpaces(line, font);
        int length = line.size() - 1 == 0 ? 1 : line.size() - 1;
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

    private int getLineWidthNoBlankSpaces(List<String> line, DescriptionFont font) {
        int lineWidth = 0;
        for (String word : line) {
            lineWidth += getWordWidth(word, font);
        }
        return lineWidth;
    }

    private List<List<String>> getFormattedDescription(Frame frame, String descriptionString, String materialDescriptionString, boolean showMaterials, boolean isMaterialDescription) {
        if (isMaterialDescription) {
            return getFormattedMaterialDescription(frame, descriptionString);
        }
        return getFormattedDescription(frame, descriptionString, materialDescriptionString, showMaterials);
    }

    private List<List<String>> getFormattedDescription(Frame frame, String descriptionString, String materialDescriptionString, boolean showMaterials) {
        lineNumber = getDefaultLineNumberForMaterials();
        if (showMaterials && materialDescriptionString != null) {
            materialLines = null;
            materialLines = getFormattedMaterialDescription(frame, materialDescriptionString);
            setMaterialFont(frame);
            effectFontScaleThin = 0;
            lineNumber++;
        }
        return getFormattedText(frame, descriptionString, materialDescriptionString, false);
    }

    private List<List<String>> getFormattedMaterialDescription(Frame frame, String materialDescriptionString) {
        lineNumber = 0;
        return getFormattedText(frame, materialDescriptionString, null, true);
    }

    private List<List<String>> getFormattedText(Frame frame, String descriptionString, String materialDescriptionString, boolean isMaterialDescription) {
        List<String> description = getDescriptionAsList(descriptionString);
        int currentLineWidth = 0;
        int sum = 0;
        setFont(frame);

        if (!isMaterialDescription) {
            sum += textBoxWidth * lineNumber;
        }

        for (int i = 0; i < description.size(); i++) {
            if (canAddWord(description.get(i), currentLineWidth, font)) {
                currentLineWidth += getWordWidth(description.get(i), font) + getBlankSpaceWidth();
            }
            else {
                if (!description.get(i).equals(NEW_LINE_SEPARATOR)) {
                    // here concurrency by adding NEW_LINE_SEPARATOR moves i to previous word
                    description.add(i, NEW_LINE_SEPARATOR);
                }
                sum += textBoxWidth;
                lineNumber++;
                currentLineWidth = 0;
            }
        }
        sum += currentLineWidth == 0 ? 0 : currentLineWidth - getBlankSpaceWidth();

        if (sum > getTotalWidth(isMaterialDescription)) {
            if (effectFontScaleThin < 4) {
                effectFontScaleThin++;
                return getFormattedDescription(frame, descriptionString, materialDescriptionString, false, isMaterialDescription);
            }
            else {
                effectFontScaleThin = 0;
            }

            if (isMaterialDescription) {
                materialLineNumber++;
            }
            else {
                fontSize++;
            }
            return getFormattedDescription(frame, descriptionString, materialDescriptionString, this.showMaterials, isMaterialDescription);
        }
        return convertToLines(description);
    }

    private List<String> getDescriptionAsList(String description) {
        List<String> descriptionList = new ArrayList<>();
        String[] descriptionArray = description.split(NEW_LINE_SEPARATOR);

        for (int i = 0; i < descriptionArray.length; i++) {
            String[] words = descriptionArray[i].split(BLANK_SPACE);
            Collections.addAll(descriptionList, words);
            if (i < descriptionArray.length - 1) {
                descriptionList.add(NEW_LINE_SEPARATOR);
            }
        }
        return descriptionList;
    }

    private boolean canAddWord(String word, int currentLineWidth, DescriptionFont font) {
        return !(word.equals("\n") || getWordWidth(word, font) + currentLineWidth > textBoxWidth);
    }

    private int getWordWidth(String word, DescriptionFont font) {
        int width = 0;

        for (char c : word.toCharArray()) {
            BufferedImage img = font.getCharacterImage(c);
            width += img.getWidth();
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
        return font.getCharacterImage(BLANK_SPACE.charAt(0)).getWidth();
    }

    private int getWidth() {
        return font.getCharacterImage(BLANK_SPACE.charAt(0)).getWidth();
    }

    private int getTotalWidth(boolean isMaterialDescription) {
        if (isMaterialDescription) {
            return materialLineNumber * textBoxWidth;
        }
        else {
            if (lineNumber >= DEFAULT_LINE_NUMBER) {
                int value = DEFAULT_LINE_NUMBER + font.getMaxLineNumber();

                if (font.getMaxLineNumber() >= 3 && BuilderType.SPELL_TRAP.equals(builderType)) {
                    value += font.getMaxLineNumber() - 2;
                }
                return textBoxWidth * value;
            }
            else {
                return textBoxWidth * DEFAULT_LINE_NUMBER;
            }
        }
    }

    private DescriptionFont getFont(Frame frame) {
        String type = DescriptionFont.EFFECT;
        if (Frame.NORMAL.equals(frame)) {
            type = DescriptionFont.NORMAL;
        }
        return DescriptionFont.getFont(type, fontSize, effectFontScaleThin);
    }

    private void setFont(Frame frame) {
        font = getFont(frame);
        lineHeight = font.getLineHeight();
    }

    private void setMaterialFont(Frame frame) {
        materialFont = getFont(frame);
        lineHeight = materialFont.getLineHeight();
    }

    private int getDefaultLineNumberForMaterials() {
        if (materialLines != null) {
            return materialLines.size();
        }
        return 0;
    }
}

//    private List<List<String>> getFormattedDescription(String descriptionString, String materialDescriptionString, boolean showMaterials) {
//        lineNumber = getDefaultLineNumberForMaterials();
//        if (showMaterials && materialDescriptionString != null) {
//            materialLines = null;
//            materialLines = getFormattedMaterialDescription(materialDescriptionString);
//            setMaterialFont();
//            effectFontScaleThin = 0;
//            lineNumber++;
//        }
//        List<String> description = getDescriptionAsList(descriptionString);
//        int currentLineWidth = 0;
//        int sum = 0;
//        setFont();
//
//        sum += textBoxWidth * lineNumber;
//
//        for (int i = 0; i < description.size(); i++) {
//            if (canAddWord(description.get(i), currentLineWidth, font)) {
//                currentLineWidth += getWordWidth(description.get(i), font) + getBlankSpaceWidth();
//            }
//            else {
//                if (!description.get(i).equals(NEW_LINE_SEPARATOR)) {
//                    // here concurrency by adding NEW_LINE_SEPARATOR moves i to previous word
//                    description.add(i, NEW_LINE_SEPARATOR);
//                }
//                sum += textBoxWidth;
//                lineNumber++;
//                currentLineWidth = 0;
//            }
//        }
//        sum += currentLineWidth == 0 ? 0 : currentLineWidth - getBlankSpaceWidth();
//
//        if (sum > getTotalWidth(false)) {
//            if (effectFontScaleThin < 4) {
//                effectFontScaleThin++;
//                return getFormattedDescription(descriptionString, materialDescriptionString, false);
//            }
//            else {
//                effectFontScaleThin = 0;
//            }
//            fontSize++;
//            return getFormattedDescription(descriptionString, materialDescriptionString, this.showMaterials);
//        }
//        return convertToLines(description);
//    }
//
//    private List<List<String>> getFormattedMaterialDescription(String descriptionString) {
//        lineNumber = 0;
//        List<String> description = getDescriptionAsList(descriptionString);
//        int currentLineWidth = 0;
//        int sum = 0;
//        setFont();
//
//        for (int i = 0; i < description.size(); i++) {
//            if (canAddWord(description.get(i), currentLineWidth, font)) {
//                currentLineWidth += getWordWidth(description.get(i), font) + getBlankSpaceWidth();
//            }
//            else {
//                if (!description.get(i).equals(NEW_LINE_SEPARATOR)) {
//                    // here concurrency by adding NEW_LINE_SEPARATOR moves i to previous word
//                    description.add(i, NEW_LINE_SEPARATOR);
//                }
//                sum += textBoxWidth;
//                lineNumber++;
//                currentLineWidth = 0;
//            }
//        }
//        sum += currentLineWidth == 0 ? 0 : currentLineWidth - getBlankSpaceWidth();
//
//        if (sum > getTotalWidth(true)) {
//            if (effectFontScaleThin < 4) {
//                effectFontScaleThin++;
//                return getFormattedMaterialDescription(descriptionString);
//            }
//            else {
//                effectFontScaleThin = 0;
//            }
//            materialLineNumber++;
//            return getFormattedMaterialDescription(descriptionString);
//        }
//        return convertToLines(description);
//    }