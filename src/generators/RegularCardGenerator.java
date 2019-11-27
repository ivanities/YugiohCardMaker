package generators;

import cardproperties.Edition;
import cardproperties.Frame;
import cardproperties.Level;
import cardproperties.Property;
import constants.*;
import net.coobird.thumbnailator.Thumbnails;
import newyugiohcardmaker.Card;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegularCardGenerator extends CardGenerator {
    
    private static int defaultLineCount;
    private static float scale;
    private static float effectFontScaleThin = 1;
    private static int textBoxHeight;
    private static int textBoxWidth;
    private static int textBoxStartX;
    private static int textBoxStartY;
    private static int textBoxLineGap;

    public RegularCardGenerator() throws IOException{
        super();
        initialiseImages();
    }
    
    private void initialiseImages() throws IOException {
       imgStar = ImageIO.read(new File(FilePaths.STAR));
       imgRank = ImageIO.read(new File(FilePaths.RANK));
       imgNegativeStar = ImageIO.read(new File(FilePaths.NEGATIVE_STAR));
    }
    
    @Override
    public BufferedImage generate(Card card) throws IOException{
        final_image = ImageUtils.createNewCompatibleImage(826, 1204, Transparency.TRANSLUCENT);
        g = final_image.createGraphics();
        ImageUtils.setRenderingHints(g);

//        addCardBase(g, card.getFrame());
//        addDescriptionFont(g, card.getDescription(), false, card.getType(), EFFECT_DESCRIPTION_FONT, card.getFrame());

        addPicture(card.getImagePath());
        addCardBase(g, card.getFrame());
        addAttribute(g, card.getAttributeType(), card.getLevel());
        if (isPendulum) {
            addPendulumScales(g);
            addPendulumScaleValue(g, String.valueOf(card.getPendulumScale()));
            addDescriptionFont(g, card.getPendulumDescription(), true, card.getType(), EFFECT_DESCRIPTION_FONT, card.getFrame());
        }
        if (card.getType().equals(Frame.SPELL.getFrame()) || card.getType().equals(Frame.TRAP.getFrame())) {
            addDescriptionFont(g, card.getPendulumDescription(), false, card.getType(), EFFECT_DESCRIPTION_FONT, card.getFrame());
            addSpellTrapType(g, card.getType(), card.getSpellTrapProperty());
        }
        else {
            addLevel(card.getLevel(), card.getFrame());
            if (card.getFrame().equals(Frame.NORMAL.getFrame())) {
                addDescriptionFont(g, card.getDescription(), false, card.getType(), NORMAL_DESCRIPTION_FONT, card.getFrame());
            }
            else {
                addDescriptionFont(g, card.getDescription(), false, card.getType(), EFFECT_DESCRIPTION_FONT, card.getFrame());
            }
            addAtkDef(g, card.getAtk(), card.getDef());
            addMonsterType(g, card.getMonsterType(), card.getAbility(), card.getType(), card.hasEffect());
        }
        if (card.getFrame().equals(Frame.EXCEED.getFrame())
                || card.getFrame().equals(Frame.DARK_SYNCHRO.getFrame())
                || card.getFrame().equals(Frame.WICKED_GOD.getFrame())) {
            addCardID(g, card.getCardID(), CARD_ID_FONT_WHITE);
            addSerialLimit(g, card.getSerialNumber(), card.getFrame(), CARD_ID_FONT_WHITE);
            addName(g, card.getName(), card.getRarity(), card.getFrame());
            addCreator(g, card.getYear() + " " + card.getCreator(), card.getFrame(), CARD_ID_FONT_WHITE);
            addEdition(g, card.getEdition(), EDITION_FONT_WHITE);
        }
        else {
            addCardID(g, card.getCardID(), CARD_ID_FONT);
            addSerialLimit(g, card.getSerialNumber(), card.getFrame(), CARD_ID_FONT);
            addName(g, card.getName(), card.getRarity(), card.getFrame());
            addCreator(g, card.getYear() + " " + card.getCreator(), card.getFrame(), CARD_ID_FONT);
            addEdition(g, card.getEdition(), EDITION_FONT);
        }
        addSerialtag(card.getEdition());

        g.dispose();
        return final_image;
    }

    @Override
    protected void addPicture(String image_name) throws IOException {
        if (isPendulum) {
            drawImage(imgPendulumPicture, Coordinates.PENDULUM_PICTURE_X, Coordinates.PENDULUM_PICTURE_Y);
//            String f = FilePaths.resources.getFilePath("Dark.png");
        }
        else {
            drawImage(imgPicture, Coordinates.PICTURE_X, Coordinates.PICTURE_Y);
        }
    }

    @Override
    protected void addLevel(int level, String cardType) throws IOException {
        BufferedImage imgLevel = getLevelImage(Frame.getFrame(cardType));
        for (int i = 0; i < level; i++) {
            drawImage(imgLevel, getLevelStartX(level, cardType, i), Coordinates.LEVEL_Y, Scales.LEVEL);
        }
    }

    private int getLevelStartX(int level, String cardType, int currentStar) {
        if (level == Level.THIRTEEN) {
            return Coordinates.LEVEL_13_X - getCurrentStarPos(currentStar);
        }
        else if (cardType.equals(Frame.DARK_SYNCHRO.getFrame())
                || cardType.equals(Frame.EXCEED.getFrame())) {
            return Coordinates.RANK_X + getCurrentStarPos(currentStar);
        }
        else {
            return Coordinates.LEVEL_X - getCurrentStarPos(currentStar);
        }
    }
    
    private int getCurrentStarPos(int currentStar) {
        return currentStar * (Coordinates.LEVEL_GAP + Scales.LEVEL);
    }
       
    public BufferedImage flip(BufferedImage image){
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp  op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);
        return image;
    }
    
    @Override
    protected void addAtkDef(Graphics2D g, String atk, String def) throws IOException{
        final int SPACE = 19;
        BufferedImage AtkDefBar = ImageIO.read(new File(FilePaths.ATK_DEF_BAR));
        g.drawImage(AtkDefBar, 63, 1092, AtkDefBar.getWidth(), AtkDefBar.getHeight(), null);
        
        final int DIGITS = atk.length() > def.length() ? atk.length() : def.length();
        
        addAtkDefValue(g, def, Coordinates.ATK_DEF_X);
        int startX = addAtkDefLabel(DIGITS, Coordinates.ATK_DEF_X, FilePaths.DEF);
        addAtkDefValue(g, atk, startX - SPACE);
        addAtkDefLabel(DIGITS, startX - SPACE, FilePaths.ATK);
    }
    
    private int addAtkDefLabel(int digits, int startX, String filePath) throws IOException {
        final int DEFAULT_DIGITS = 4;
        final int DEFAULT_DIGIT_WIDTH = 18 + 1;
        int numberOfDigitsMissing;
        if (digits < DEFAULT_DIGITS) {
            numberOfDigitsMissing = DEFAULT_DIGITS;
        }
        else {
            numberOfDigitsMissing = digits;
        }
        BufferedImage atkDefLabel = ImageIO.read(new File(filePath));
        startX -= atkDefLabel.getWidth() + 1 + numberOfDigitsMissing * DEFAULT_DIGIT_WIDTH;
        g.drawImage(atkDefLabel, startX, Coordinates.ATK_DEF_Y, atkDefLabel.getWidth(), atkDefLabel.getHeight(), null);
        
        return startX;
    }
    
    private int addAtkDefValue(Graphics2D g, String atkDef, int startX) {
        for(int i = atkDef.length() - 1; i >= 0; i--){
            int unicode_decimal = (int)atkDef.charAt(i);
            if (unicode_decimal == 88 || unicode_decimal == 120) {
                unicode_decimal = 58;
            }
            if(unicode_decimal == 63){
                unicode_decimal = 59;
            }
            BufferedImage img = PENDULUM_SCALE_FONT[unicode_decimal - 48];

            startX -= Scales.ATK_DEF_X;
            g.drawImage(img, startX, Coordinates.ATK_DEF_Y, Scales.ATK_DEF_X, Scales.ATK_DEF_Y, null);
            startX -= 1;
        }
        return startX;
    }
    
    public void addSerialLimit(Graphics2D g, String serial_number, String base, BufferedImage[] font) throws IOException{
        if(serial_number.matches("[0-9]+")){
            addSerialNumber(g, serial_number, base);
        }
        else {
            addSerialText(g, serial_number, font);
        }
    }
    
    public void addSerialNumber(Graphics2D g, String serial_number, String base) throws IOException{
        if(!serial_number.equals("")){
            BufferedImage[] font;
            if (base.equals(Frame.EXCEED.getFrame())
                    || base.equals(Frame.DARK_SYNCHRO.getFrame())
                    || base.equals(Frame.WICKED_GOD.getFrame())) {
                font = SERIAL_NUMBER_FONT_WHITE;
            }
            else {
                font = SERIAL_NUMBER_FONT;
            }
            
            int x = 0;
            for(int i = 0; i < serial_number.length(); i++){
                int unicode_decimal = (int)serial_number.charAt(i);
                BufferedImage img = font[unicode_decimal - 48];

                g.drawImage(img, (38 + x), 1150, img.getWidth(), img.getHeight(), null);
                x += img.getWidth() + 2;
            }
        }
    }
    
    public void addSerialText(Graphics2D g, String serial_number, BufferedImage[] font) throws IOException{
        int x = 0;
        for(int i = 0; i < serial_number.length(); i++){
            int unicode_decimal = getUnicodeValueBaseFont(serial_number, i);
            BufferedImage img = font[unicode_decimal - 48];

            g.drawImage(img, (Coordinates.SERIAL_TEXT_X + x), Coordinates.SERIAL_TEXT_Y, (int)(img.getWidth() * Scales.SERIAL_TEXT), (int)(img.getHeight() * Scales.SERIAL_TEXT), null);
            x += (int)(img.getWidth() * Scales.SERIAL_TEXT) + 1;
        }
    }
    
    public void addName(Graphics2D g, String name, String rarity, String base) throws IOException{
        final BufferedImage[] Font;
        if (rarity.equals("Ultra Rare") || rarity.equals("Ultimate Rare")) {
            Font = NAME_FONT_YELLOW;
        }else if (rarity.equals("Super Rare") || rarity.equals("Secret Rare") || base.equals(Frame.EXCEED.getFrame())
                || base.equals(Frame.DARK_SYNCHRO.getFrame())
                || base.equals(Frame.WICKED_GOD.getFrame())) {
            Font = NAME_FONT_WHITE;
        }else {// rarity = "Common"
            Font = NAME_FONT_PLAIN;
        }
        int x = 0;
        float scale = 1.14583333333f;
        float width_scale = 1;
        int final_width = 610;
        int current_width = 0;
        int extra_bit = 0;
        
        for(int i = 0; i < name.length(); i++){
            int unicode_decimal = (int)name.charAt(i);
            BufferedImage img = Font[unicode_decimal - 32];
            current_width += (int)(img.getWidth() * scale);
            extra_bit++;
        }
        final_width = final_width - extra_bit;
        if (current_width > final_width) {
            width_scale = ((float)final_width/(float)current_width);
        }
        
        for(int i = 0; i < name.length(); i++){
            int unicode_decimal = (int)name.charAt(i);
            BufferedImage img = Font[unicode_decimal - 32];

            g.drawImage(img, (67 + x), 66, (int)(img.getWidth() * scale * width_scale), (int)(img.getHeight() * scale), null);
            x += (int)((img.getWidth() * scale * width_scale) + 1);
        }
    }
    
    public void addCardID(Graphics2D g, String cardID, BufferedImage[] font) throws IOException{
        if (isPendulum) {
            addPendulumCardID(g, cardID);
        }
        else {
            addNormalCardID(g, cardID, font);
        }
    }
    
    public void addNormalCardID(Graphics2D g, String cardID, BufferedImage[] font) throws IOException{
        int x = 0;
        for(int i = cardID.length() - 1; i >= 0; i--){
            int unicode_decimal = getUnicodeValueBaseFont (cardID, i);
            BufferedImage img = font[unicode_decimal - 48];

            x += (int)(img.getWidth() * Scales.CARD_ID);
            g.drawImage(img, (740 - x), 867, (int)(img.getWidth() * Scales.CARD_ID), (int)(img.getHeight() * Scales.CARD_ID), null);
            x += 1;
        }
    }
    
    public void addPendulumCardID(Graphics2D g, String cardID) throws IOException{
        int x = 0;
        for(int i = 0; i < cardID.length(); i++){
            int unicode_decimal = getUnicodeValueBaseFont (cardID, i);
            BufferedImage img = CARD_ID_FONT[unicode_decimal - 48];

            g.drawImage(img, (Coordinates.CARD_ID_PENDULUM_X + x), Coordinates.CARD_ID_PENDULUM_Y, (int)(img.getWidth() * Scales.CARD_ID), (int)(img.getHeight() * Scales.CARD_ID), null);
            x += (int)(img.getWidth() * Scales.CARD_ID) + 1;
        }
    }
    
    public void addMonsterType(Graphics2D g, String monster_type, String ability, String cardType, boolean has_effect) throws IOException{
        String full_monster_type = "[" + monster_type;
        if(!Frame.getUndisplayableBases().contains(Frame.getFrame(cardType))){
            full_monster_type += " / " + cardType;
        }
        if(!ability.equals("None")){
            full_monster_type += " / " + ability;
        }
        if(has_effect){
            full_monster_type += " / Effect";
        }
        full_monster_type += "]";
        int x = 0;
        final float SCALE_LOWER_CASE = 20/41f;
        final float SCALE_UPPER_CASE = 25/42f;
        final float SCALE_SQUARE_BRACKETS = 32/47f;
        
        final int START_X = 67;
        final int START_Y_SQUARE_BRACKETS = 904, START_Y_UPPER_CASE = 906, START_Y_LOWER_CASE = 910;
        
        float scale;
        int start_y;
        
        for(int i = 0; i < full_monster_type.length(); i++){
            int unicode_decimal = (int)full_monster_type.charAt(i);
            
            if(unicode_decimal == 47){
                unicode_decimal = 92;
            }else if((0 < unicode_decimal && unicode_decimal < 65)
                    || (unicode_decimal > 93 && unicode_decimal < 97)){
                unicode_decimal = 94;
            }else if(97 <= unicode_decimal && unicode_decimal <= 122){
                unicode_decimal -= 32;
            }
            
            if(full_monster_type.charAt(i) == '[' || full_monster_type.charAt(i) == ']'){
                scale = SCALE_SQUARE_BRACKETS;
                start_y = START_Y_SQUARE_BRACKETS;
            }else if(Character.isUpperCase(full_monster_type.charAt(i))){
                scale = SCALE_UPPER_CASE;
                start_y = START_Y_UPPER_CASE;
            }else{
                scale = SCALE_LOWER_CASE;
                start_y = START_Y_LOWER_CASE;
            }
                
            BufferedImage img = MONSTER_TYPE_FONT[unicode_decimal - 65];
            
            if (full_monster_type.charAt(i) == ' ') {
                x-=6;
            }

            g.drawImage(img, (START_X + x), start_y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += (int)(img.getWidth() * scale) + 2;
            if( i != full_monster_type.length() -1 && (full_monster_type.charAt(i) == '[' || full_monster_type.charAt(i+1) == ']')){
                x += 3;
            }
        }
    }
    
    public void addDescriptionFont(Graphics2D g, String description, boolean isPendulum, String cardType, BufferedImage[] font, String cardBase) throws IOException{
        if(description.equals("")){
            return;
        }
        boolean isSpellTrap = false;
        if (isPendulum) {
            defaultLineCount = LineCounts.DEFAULT_PENDULUM_LINE_COUNT;
            textBoxHeight = Dimensions.TEXT_BOX_PENDULUM_HEIGHT;
            textBoxWidth = Dimensions.TEXT_BOX_PENDULUM_WIDTH;
            textBoxLineGap = Dimensions.TEXT_BOX_DEFAULT_LINE_GAP;
            textBoxStartX = Coordinates.TEXT_BOX_PENDULUM_START_X;
            textBoxStartY = Coordinates.TEXT_BOX_PENDULUM_START_Y;
        }
        else if (cardType.equals(Frame.SPELL.getFrame()) || cardType.equals(Frame.TRAP.getFrame())) {
            defaultLineCount = LineCounts.DEFAULT_SPELL_LINE_COUNT;
            textBoxHeight = Dimensions.TEXT_BOX_DESCIPTION_HEIGHT;
            textBoxWidth = Dimensions.TEXT_BOX_DESCIPTION_WIDTH;
            textBoxLineGap = Dimensions.TEXT_BOX_DEFAULT_LINE_GAP;
            textBoxStartX = Coordinates.TEXT_BOX_DESCIPTION_START_X;
            textBoxStartY = Coordinates.TEXT_BOX_SPELL_START_Y;
            isSpellTrap = true;
        }
        else {
            defaultLineCount = LineCounts.DEFAULT_LINE_COUNT;
            textBoxHeight = Dimensions.TEXT_BOX_DESCIPTION_HEIGHT;
            textBoxWidth = Dimensions.TEXT_BOX_DESCIPTION_WIDTH;
            textBoxLineGap = Dimensions.TEXT_BOX_DEFAULT_LINE_GAP;
            textBoxStartX = Coordinates.TEXT_BOX_DESCIPTION_START_X;
            textBoxStartY = Coordinates.TEXT_BOX_DESCIPTION_START_Y;
            if (cardBase.equals(Frame.NORMAL.getFrame())) {
                textBoxStartY--;
            }
        }
        scale = getScale(defaultLineCount, isPendulum, isSpellTrap, cardBase);
        List<List<String>> lines = getFormattedDescription(defaultLineCount, description, getScale(defaultLineCount, isPendulum, isSpellTrap, cardBase), isPendulum, isSpellTrap, font, cardBase);
        final float SCALE = scale < getScale(defaultLineCount, isPendulum, isSpellTrap, cardBase) ? scale : getScale(defaultLineCount, isPendulum, isSpellTrap, cardBase);
        int x = 0;
        int y = 0;
        boolean isFlipped = true;
        
        for (List<String> line : lines) {
            int[] WhiteSpaces = null;
            if (line.size() != 1) {
                WhiteSpaces = getWhiteSpaceScaledWidthArray(line, SCALE, font);
            }
            for (String word : line) {
                final float originalScale = effectFontScaleThin;
                for (char c : word.toCharArray()) {
                    int unicode_decimal = (int) c;
                    if (c == '\u25cf') {
                        unicode_decimal = 127;
                        effectFontScaleThin = 1;
                    }
                    
                    BufferedImage img = font[unicode_decimal - 32];

                    if(unicode_decimal == 34){
                        if(!isFlipped){
                            isFlipped = true;
                        }else{
                            isFlipped = false;
                            img = flip(img);
                        }
                    }
                    ////////////////////////////////////////////////////////////
                    BufferedImage thumbnail = Thumbnails.of(img)
                            .scale(SCALE * effectFontScaleThin, SCALE)
                            .asBufferedImage();
                    g.drawImage(thumbnail, textBoxStartX + x, textBoxStartY + y, null);
                    
                    ////////////////////////////////////////////////////////////
//                    g.drawImage(img, textBoxStartX + x, textBoxStartY + y, (int)Math.ceil(img.getWidth() * SCALE * effectFontScaleThin), (int)Math.ceil(img.getHeight() * SCALE), null);
                    if (c == '\u25cf') {
                        unicode_decimal = 127;
                        effectFontScaleThin = originalScale;
                    }
                    x += (int)Math.ceil(img.getWidth() * SCALE * effectFontScaleThin);
                }
                if (line.indexOf(word) != line.size() - 1) {
                    if (lines.indexOf(line) != lines.size() - 1 && lines.get(lines.indexOf(line) + 1).get(0).equals("\u25cf")) {
                        x += getWhiteSpaceWidth(SCALE, font);
                    }
                    else if (line.size() != 1 && lines.indexOf(line) == lines.size() - 1 && getLineWidthNoWhiteSpaces(line, SCALE, font) < textBoxWidth * 0.75) {
                        x += getWhiteSpaceWidth(SCALE, font);
                    }
                    else if (getLineWidthNoWhiteSpaces(line, SCALE, font) < textBoxWidth * 0.75) {
                        x += getWhiteSpaceWidth(SCALE, font);
                    }
                    else {
                        x += WhiteSpaces[line.indexOf(word)];
                    }
                }
            }
            x = 0;
            if (cardBase.equals(Frame.NORMAL.getFrame())) {
                y += Math.ceil(55 * SCALE);
            }
            else {
                y += Math.ceil(65 * SCALE);
            }
        }
        effectFontScaleThin = 1;
    }
    
    private int[] getWhiteSpaceScaledWidthArray(List<String> line, float scale, BufferedImage[] font) {
        int whiteSpaceLeft = textBoxWidth - getLineWidthNoWhiteSpaces(line, scale, font);
        
        int newWhiteSpaceWidth = (int) Math.floor(whiteSpaceLeft/(line.size() -1) * effectFontScaleThin);
        
        int[] whiteSpaceWidthArray = new int[line.size() -1];
        for (int i = 0; i < whiteSpaceWidthArray.length; i++)  {
            whiteSpaceWidthArray[i] = newWhiteSpaceWidth;
        }
        
        int difference = whiteSpaceLeft - (newWhiteSpaceWidth * (line.size() -1));
        
        while (difference != 0) {
            for (int i = 0; i < whiteSpaceWidthArray.length; i++)  {
                whiteSpaceWidthArray[i]++;
                difference--;
                if (difference == 0) {
                    break;
                }
            }
        }
        return whiteSpaceWidthArray;
    }
    
    private int getLineWidthNoWhiteSpaces(List<String> line, float scale, BufferedImage[] font) {
        int lineWidth = 0;
        for (String word : line) {
            lineWidth += getWordWidth(word, scale, font);
        }
        return lineWidth;
    }
    
    private List<List<String>> getFormattedDescription(int lineCount, String descriptionString, float scale, boolean isPendulum, boolean isSpellTrap, BufferedImage[] font, String cardBase) {
        List<String> description = getDescriptionAsList(descriptionString);
        int currentLineWidth = 0;
        int sum = 0;
        
        for (int i = 0; i < description.size(); i++) {
            if (canAddWord(description.get(i), scale, currentLineWidth, font)) {
                currentLineWidth += getWordWidth(description.get(i), scale, font) + getWhiteSpaceWidth(scale, font);
            }
            else {
                if (!description.get(i).equals("\n")) {
                    description.add(i, "\n");
                }
                sum += textBoxWidth;
                currentLineWidth = 0;
            }
        }
        sum += currentLineWidth == 0 ? 0 : currentLineWidth - getWhiteSpaceWidth(scale, font);
        
        if (sum > lineCount * textBoxWidth) {
            if (effectFontScaleThin > 0.75) {
                effectFontScaleThin -= 0.05;
                return getFormattedDescription(lineCount, descriptionString, getScale(lineCount, isPendulum, isSpellTrap, cardBase), isPendulum, isSpellTrap, font, cardBase);
            }
            else {
                effectFontScaleThin = 1;
            }
            lineCount++;
            RegularCardGenerator.scale =  getScale(lineCount, isPendulum, isSpellTrap, cardBase);
            return getFormattedDescription(lineCount, descriptionString, getScale(lineCount, isPendulum,isSpellTrap, cardBase), isPendulum, isSpellTrap, font, cardBase);
        }
        return convertToLines(description);
    }
    
    private float getScale(int lineCount, boolean isPendulum, boolean isSpellTrap, String cardBase) {
        if (isPendulum) {
//            return (Scales.DESCRIPTION_SCALE/(lineCount + 1));
            return (Scales.DESCRIPTION_SCALE/(lineCount + 1))*1.01f*0.5f;
        }
        else if (isSpellTrap) {
//            return (Scales.DESCRIPTION_SCALE/(lineCount - 2));
            return (Scales.DESCRIPTION_SCALE/(lineCount - 2))*1.01f*0.5f;
        }
        else if (cardBase.equals(Frame.NORMAL.getFrame())) {
            return (Scales.NORMAL_SCALE/lineCount)*1.01f*0.5f;
        }
        else {
//            return Scales.DESCRIPTION_SCALE/lineCount;
            return (Scales.DESCRIPTION_SCALE/lineCount)*1.01f*0.5f;
        }
    }
    
    private boolean canAddWord(String word, float scale, int currentLineWidth, BufferedImage[] font) {
        if (word.equals("\n") || getWordWidth(word, scale, font) + currentLineWidth > textBoxWidth) {
            return false;
        }
        return true;
    }
    
    private List<String> getDescriptionAsList(String description) {
        List<String> descriptionList = new ArrayList<>();
        String[] descriptionArray = description.split("\n");
        
        for (int i =  0; i < descriptionArray.length; i++) {
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
    
    private int getWordWidth(String word, float scale, BufferedImage[] font) {
        int width = 0;
        
        for (char c : word.toCharArray()) {
            int unicode_decimal = (int) c;
            if (c == '\u25cf') {
                unicode_decimal = 127;
            }
            BufferedImage img = font[unicode_decimal - 32];
            width += Math.ceil(img.getWidth() * scale * effectFontScaleThin);
        }
        return width;
    }
    
    private int getWhiteSpaceWidth(float scale, BufferedImage[] font) {
        return ((int) Math.ceil(font[0].getWidth() * scale * effectFontScaleThin)) * 2;
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
    
    @Override
    public void setIsPendulum(boolean isPendulum){
        this.isPendulum = isPendulum;
        if (isPendulum) {
            CARD_TEMPLATES_PATH = "resources\\read_only\\card_templates\\pendulum\\";
        }else{
            CARD_TEMPLATES_PATH = "resources\\read_only\\card_templates\\";
        }
    }
    
    public void addPendulumScales(Graphics2D g) throws IOException {
        BufferedImage pendulumScale = ImageIO.read(new File("resources\\read_only\\card_features\\pendulum\\scaleBlue.png"));
        g.drawImage(pendulumScale, PENDULUM_SCALE_BLUE_START_X, PENDULUM_SCALE_START_Y, null);
        
        pendulumScale = ImageIO.read(new File("resources\\read_only\\card_features\\pendulum\\scaleRed.png"));
        g.drawImage(pendulumScale, PENDULUM_SCALE_RED_START_X, PENDULUM_SCALE_START_Y, null);
    }
    
    public void addSpellTrapType(Graphics2D g, String cardType, String spellTrapProperty) throws IOException{
        String full_monster_type = "[" + cardType + " Card";
        if (!spellTrapProperty.equals(Property.NORMAL.getProperty())) {
            full_monster_type += "   ]";
        }
        else {
            full_monster_type += "]";
        }
        int x = 0;
        final float SCALE_LOWER_CASE = 20/41f * Scales.SPELL_TRAP_TYPE_SCALE;
        final float SCALE_UPPER_CASE = 25/42f * Scales.SPELL_TRAP_TYPE_SCALE;
        final float SCALE_SQUARE_BRACKETS = 27/47f * Scales.SPELL_TRAP_TYPE_SCALE;
        
        final double START_Y_UPPER_CASE = (int) Coordinates.SPELL_TRAP_TYPE_START_Y + 2 + 2 * Scales.SPELL_TRAP_TYPE_SCALE;
        final double START_Y_LOWER_CASE = (int) Coordinates.SPELL_TRAP_TYPE_START_Y + 2 + 6 * Scales.SPELL_TRAP_TYPE_SCALE;
        
        float scale;
        int start_y;
        
        for(int i = full_monster_type.length() - 1; i >= 0; i--){
            int unicode_decimal = (int)full_monster_type.charAt(i);
            
            if (unicode_decimal == 47) {
                unicode_decimal = 92;
            }
            else if ((0 < unicode_decimal && unicode_decimal < 65)
                    || (unicode_decimal > 93 && unicode_decimal < 97)) {
                unicode_decimal = 94;
            }
            else if (97 <= unicode_decimal && unicode_decimal <= 122) {
                unicode_decimal -= 32;
            }
            
            if (full_monster_type.charAt(i) == '[' || full_monster_type.charAt(i) == ']') {
                scale = SCALE_SQUARE_BRACKETS;
                start_y = Coordinates.SPELL_TRAP_TYPE_START_Y + 5;
            }
            else if (Character.isUpperCase(full_monster_type.charAt(i))) {
                scale = SCALE_UPPER_CASE;
                start_y = (int) START_Y_UPPER_CASE;
            }
            else {
                scale = SCALE_LOWER_CASE;
                start_y = (int) START_Y_LOWER_CASE;
            }
                
            BufferedImage img = MONSTER_TYPE_FONT[unicode_decimal - 65];
            
            if( i != full_monster_type.length() - 1 && (full_monster_type.charAt(i) == '[' || full_monster_type.charAt(i+1) == ']')){
                x += 3;
            }
            x += (int)(img.getWidth() * scale);
            g.drawImage(img, (Coordinates.SPELL_TRAP_TYPE_START_X - x), start_y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += 2;
        }
        if (!spellTrapProperty.equals(Property.NORMAL.getProperty())) {
            BufferedImage property = ImageIO.read(new File(FilePaths.PROPERTY + spellTrapProperty.toLowerCase().replace('-', '_') + ".png"));
            g.drawImage(property, Coordinates.PROPERTY_X, Coordinates.PROPERTY_Y, null);
        }
    }
    
    private void addCreator(Graphics2D g, String creator, String base, BufferedImage[] font) throws IOException{
        int x = 0;
        for(int i = creator.length() - 1; i >= 0; i--){
            int unicode_decimal = getUnicodeValueBaseFont (creator, i);
            BufferedImage img = font[unicode_decimal - 48];

            x += (int)(img.getWidth() * Scales.CREATOR);
            
            BufferedImage thumbnail = Thumbnails.of(img)
                            .scale(Scales.CREATOR)
                            .asBufferedImage();
            g.drawImage(thumbnail, (Coordinates.CREATOR_X - x), Coordinates.CREATOR_Y, null);
                    
//            g.drawImage(img, (Coordinates.CREATOR_X - x), Coordinates.CREATOR_Y, (int)(img.getWidth() * Scales.CREATOR), (int)(img.getHeight() * Scales.CREATOR), null);
            x += 1;
        }
        
        addCopyRightSign(base, x + 2);
    }
    
    private void addEdition(Graphics2D g, String edition, BufferedImage[] font) throws IOException{
        int x = 0;
        float scale;
        for(int i = 0; i < edition.length(); i++){
            int unicode_decimal = getUnicodeValueBaseFont(edition, i);
            BufferedImage img = font[unicode_decimal - 48];
            
            scale = editionNumberCheck(edition, i);
            
            g.drawImage(img, (Coordinates.EDITION_X + x), Coordinates.EDITION_Y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += (int)(img.getWidth() * scale) + 1;
        }
    }
    
    private float editionNumberCheck(String edition, int i) {
        if (i != 0 && edition.substring(i-1, i).matches("[0-9]+")) {
            return Scales.EDITION_SMALL_SCALE;
        }
        else if (i != 0 && i != 1 && edition.substring(i-2, i-1).matches("[0-9]+")) {
            return Scales.EDITION_SMALL_SCALE;
        }
        else {
            return Scales.EDITION_SCALE;
        }
    }
    
    private int getUnicodeValueBaseFont(String text, int index) {
        int unicode_decimal = (int)text.charAt(index);
        if((0 < unicode_decimal && unicode_decimal < 45)
                || (45 < unicode_decimal && unicode_decimal < 48)
                || unicode_decimal > 122){
            unicode_decimal = 111;
        }else if(65 <= unicode_decimal && unicode_decimal <= 90){
            unicode_decimal -= 7;
        }else if(97 <= unicode_decimal && unicode_decimal <= 122){
            unicode_decimal -= 13;
        }else if(unicode_decimal == 45){
            unicode_decimal = 110;
        }
        return unicode_decimal;
    }
    
    protected void addSerialtag(String edition) throws IOException {
        String serialTagPath = FilePaths.CARD_FEATURES + "eye_of_anubis_hologram_gold.png";
        
        if (edition.equals(Edition.UNLIMITED.getEdition())) {
            serialTagPath = FilePaths.CARD_FEATURES + "eye_of_anubis_hologram_silver.png";
        }
        BufferedImage serialTagImage = ImageIO.read(new File(serialTagPath));
        g.drawImage(serialTagImage, Coordinates.SERIAL_TAG_X, Coordinates.SERIAL_TAG_Y, null);
    }
    
    protected void addCopyRightSign(String base, int startX) throws IOException {
        String filePath = FilePaths.CARD_FEATURES + "copy_right_sign.png";
        
        if (base.equals(Frame.EXCEED.getFrame())
                || base.equals(Frame.DARK_SYNCHRO.getFrame())
                || base.equals(Frame.WICKED_GOD.getFrame())) {
            filePath = FilePaths.CARD_FEATURES + "copy_right_sign_white.png";
        }
        BufferedImage img = ImageIO.read(new File(filePath));
        startX += img.getWidth();
        g.drawImage(img, (Coordinates.CREATOR_X - startX), Coordinates.CREATOR_Y, null);
    }
}