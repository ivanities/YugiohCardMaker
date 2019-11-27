package generators;

import cardproperties.Frame;
import cardproperties.Level;
import cardproperties.YugiohFont;
import constants.Coordinates;
import constants.FilePaths;
import constants.Scales;
import images.ImageSplitter;
import newyugiohcardmaker.Card;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimeCardGenerator extends CardGenerator{
    private int STAR_X_ODD_START;
    private int STAR_X_EVEN_START;
    private final int PENDULUM_SCALES_START_X = 23;
    private final int PENDULUM_SCALES_START_Y = 724;

    public AnimeCardGenerator() throws IOException{
        super();
        CARD_TEMPLATES_PATH = "resources\\read_only\\anime_card_templates\\";
        CARD_FEATURES_PATH = "resources\\read_only\\anime_card_features\\";
        IMAGE_DIRECTORY_PATH = "resources\\read_only\\images\\";
        TEMP_DIRECTORY_PATH = "src\\anime_tmp\\";
        IMAGE_SCALE_X = 783;
        IMAGE_SCALE_Y = 842;
        IMAGE_X_START = 22;
        IMAGE_Y_START = 25;
        ATTRIBUTE_SCALE = 88;
        ATTRIBUTE_X_START = 673;
        ATTRIBUTE_Y_START = 923;
        STAR_WIDTH = 50;
        STAR_HEIGHT = 50;
        STAR_X_ODD_START = 390;
        STAR_X_EVEN_START = 358;
        RANK_X_START = 67;
        STAR_Y_START = 940;
        STAR_GAP = 6;
        
        STAR_X_ODD_START -= 14;
        STAR_X_EVEN_START -= 14;
        STAR_Y_START -= 15;
        STAR_WIDTH = 62;
        STAR_HEIGHT = 62;
        STAR_GAP = -20;
        
        PENDULUM_SCALE_VALUE_BLUE_ODD_START_X = 44;
        PENDULUM_SCALE_VALUE_RED_ODD_START_X = 751;
        PENDULUM_SCALE_VALUE_BLUE_EVEN_START_X = 28;
        PENDULUM_SCALE_VALUE_RED_EVEN_START_X = 735;
        PENDULUM_SCALE_VALUE_START_Y = 789;
        PENDULUM_SCALE_FACTOR = 32/28f;

        
        BufferedImage base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\anime_atk_def_text\\atk_def_font.png"));
        ATK_DEF_NUMBER_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\anime_atk_def_text");
        initialiseImages();
    }
    
    private void initialiseImages() throws IOException {
        //Levels
       imgStar = ImageIO.read(new File(FilePaths.STAR_ANIME));
       imgRank = ImageIO.read(new File(FilePaths.RANK_ANIME));
       imgNegativeStar = ImageIO.read(new File(FilePaths.NEGATIVE_STAR_ANIME));
    }
    
    @Override
    public BufferedImage generate(Card card) throws IOException{
        final_image = new BufferedImage(826, 1204, BufferedImage.TYPE_INT_ARGB);
        
        g = final_image.createGraphics();
        ImageUtils.setRenderingHints(g);

        addPicture(card.getImagePath());
        if (isPendulum) {
            addPendulumScales(g);
            addPendulumScaleValue(g, String.valueOf(card.getPendulumScale()));
        }
        addCardBase(g, card.getFrame());
        addAttribute(g, card.getAttributeType(), card.getFrame(), card.getLevel());
        if (!card.getType().equals(Frame.SPELL.getFrame()) && !card.getType().equals(Frame.TRAP.getFrame())) {
            addLevel(card.getLevel(), card.getFrame());
            addAtkDef(g, card.getAtk(), card.getDef());
        }

        g.dispose();
        return final_image;
    }

    @Override
    protected void addPicture(String image_name) throws IOException{
        drawImage(imgPicture, Coordinates.PICTURE_ANIME_X, Coordinates.PICTURE_ANIME_Y);
    }
    
    @Override
    public void setPicture(String pictureName, int scaleX, int scaleY) throws IOException {
        super.setPicture(pictureName, scaleX, scaleY);
        imgPicture = imgPicture.getSubimage(30, 0, imgPicture.getWidth() - 61, imgPicture.getHeight());
//        imgPicture = toCompatibleImage(imgPicture, Scales.PICTURE_ANIME_X, Scales.PICTURE_ANIME_Y);
    }

    @Override
    protected void addLevel(int level, String card_type) throws IOException {
        if (level == Level.THIRTEEN) {
            drawLevels(level, Coordinates.LEVEL_13_ANIME_GAP, card_type);
        }
        else {
            drawLevels(level, Coordinates.LEVEL_ANIME_GAP, card_type);
        }
    }
    
    private void drawLevels(int level, int starGap, String card_type) throws IOException {
        BufferedImage imgLevel = getLevelImage(Frame.getFrame(card_type));
        int startX = getStartX(level, starGap, card_type);
        int posX;
        
        for (int i = 0; i < level; ++i) {
            posX = startX + (i * (starGap + Scales.LEVEL_ANIME));
            drawImage(imgLevel, posX, Coordinates.LEVEL_ANIME_Y, Scales.LEVEL_ANIME);
        }
    }
    
    private int getStartX(int level, int starGap, String card_type) {
        if (card_type.equals(Frame.EXCEED.getFrame()) && level <= Level.TEN) {
            return Coordinates.RANK_ANIME_X;
        }
        switch (level) {
            case Level.THIRTEEN:
            case Level.TWELVE:
                return Coordinates.LEVEL_12_ANIME_X;
            case Level.ELEVEN:
                return Coordinates.LEVEL_11_ANIME_X;
            case Level.TEN:
                return Coordinates.LEVEL_10_ANIME_X;
            case Level.NINE:
                return Coordinates.LEVEL_9_ANIME_X;
            default:
                if (isEven(level)) {
                    return Coordinates.LEVEL_EVEN_ANIME_X - ((level / 2 - 1) * (starGap + Scales.LEVEL_ANIME));
                }
                else {
                    return Coordinates.LEVEL_ODD_ANIME_X - (((level - 1) / 2) * (starGap + Scales.LEVEL_ANIME));
                }
        }
    }
    
    private boolean isEven(int value) {
        return value % 2 == 0;
    }

    private void addAttribute(Graphics2D g, String attributeType, String cardType, int level) throws IOException {
        ATTRIBUTE_SCALE = Scales.ATTRIBUTE_ANIME;
        if (cardType.equals(Frame.SPELL.getFrame()) || cardType.equals(Frame.TRAP.getFrame())) {
            ATTRIBUTE_X_START = Coordinates.ATTRIBUTE_ANIME_SPELL_X;
            ATTRIBUTE_Y_START = Coordinates.ATTRIBUTE_ANIME_SPELL_Y;
        }
        else {
            ATTRIBUTE_X_START = Coordinates.ATTRIBUTE_ANIME_MONSTER_X;
            ATTRIBUTE_Y_START = Coordinates.ATTRIBUTE_ANIME_MONSTER_Y;
            if(level == 13){
                ATTRIBUTE_X_START = Coordinates.ATTRIBUTE_ANIME_MONSTER_LEVEL_13_X;
            }else if(level == 12){
                ATTRIBUTE_X_START = Coordinates.ATTRIBUTE_ANIME_MONSTER_LEVEL_12_X;
            }
        }
        super.addAttribute(g, attributeType, level);
    }

    @Override
    protected void addAtkDef(Graphics2D g, String atk, String def) throws IOException{
        int[] atkCoordinates = new int[] { 153, 188 };
        int[] defCoordinates = new int[] { 530, 563 };

        drawAtkDef(g, atk, atkCoordinates);
        drawAtkDef(g, def, defCoordinates);
    }

    private void drawAtkDef(Graphics2D g, String value, int[] coordinates) {
        int x;
        int x_start;
        float scale = 1.2f;

        if((value.length() % 2) == 0){
            //even case
            x_start = coordinates[0] - ((value.length() / 2 - 1) * 64);
        }else{
            //odd case
            x_start = coordinates[1] - (((value.length() - 1) / 2) * 64);
        }
        for(int i = 0; i < value.length(); i++){
            int unicode_decimal = (int) value.charAt(i);
            if(unicode_decimal == 88 || unicode_decimal == 120){
                unicode_decimal = 58;
            }else if(unicode_decimal == 63){
                unicode_decimal = 59;
            }
            BufferedImage img = ATK_DEF_NUMBER_FONT[unicode_decimal - 48];

            x = x_start + (i * 64);
            g.drawImage(img, x, 1052, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
        }
    }
    
    @Override
    public void setIsPendulum(boolean isPendulum){
        this.isPendulum = isPendulum;
        if (isPendulum) {
            CARD_TEMPLATES_PATH = "resources\\read_only\\anime_card_templates\\pendulum\\";
        }else{
            CARD_TEMPLATES_PATH = "resources\\read_only\\anime_card_templates\\";
        }
    }
    
    public void addPendulumScales(Graphics2D g) throws IOException {
        BufferedImage pendulumScale = ImageIO.read(new File("resources\\read_only\\anime_card_features\\pendulum\\pendulumScales.png"));
        g.drawImage(pendulumScale, PENDULUM_SCALES_START_X, PENDULUM_SCALES_START_Y, null);
    }
}
