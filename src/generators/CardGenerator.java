package generators;

import cardproperties.YugiohFont;
import constants.FilePaths;
import images.ImageSplitter;
import net.coobird.thumbnailator.Thumbnails;
import newyugiohcardmaker.Card;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class CardGenerator {
    
    BufferedImage imgPicture;
    BufferedImage imgPendulumPicture;
    private String pictureName;
    private String picturePendulumName;
    
    protected final String READ_ONLY_PATH = "resources\\read_only\\";
    
    String CARD_TEMPLATES_PATH;
    String CARD_FEATURES_PATH;
    String IMAGE_DIRECTORY_PATH;
    String TEMP_DIRECTORY_PATH;
    int IMAGE_SCALE_X;
    int IMAGE_SCALE_Y;
    int IMAGE_X_START;
    int IMAGE_Y_START;
    int ATTRIBUTE_SCALE;
    int ATTRIBUTE_X_START;
    int ATTRIBUTE_Y_START;
    int STAR_WIDTH;
    int STAR_HEIGHT;
    int RANK_X_START;
    int STAR_Y_START;
    int STAR_GAP;
    int PENDULUM_SCALE_BLUE_START_X = 58;
    int PENDULUM_SCALE_RED_START_X = 717;
    int PENDULUM_SCALE_START_Y = 784;
    int PENDULUM_SCALE_VALUE_BLUE_ODD_START_X = 71;
    int PENDULUM_SCALE_VALUE_RED_ODD_START_X = 727;
    int PENDULUM_SCALE_VALUE_BLUE_EVEN_START_X = 57;
    int PENDULUM_SCALE_VALUE_RED_EVEN_START_X = 713;
    int PENDULUM_SCALE_VALUE_START_Y = 827;
    float PENDULUM_SCALE_FACTOR = 1;


    boolean isPendulum;
    BufferedImage final_image;
    static BufferedImage[] SERIAL_NUMBER_FONT;
    static BufferedImage[] SERIAL_NUMBER_FONT_WHITE;
    static BufferedImage[] EFFECT_DESCRIPTION_FONT;
    static BufferedImage[] NORMAL_DESCRIPTION_FONT;
    BufferedImage[] ATK_DEF_NUMBER_FONT;
    static BufferedImage[] NAME_FONT_PLAIN;
    static BufferedImage[] NAME_FONT_WHITE;
    static BufferedImage[] NAME_FONT_YELLOW;
    static BufferedImage[] CARD_ID_FONT;
    static BufferedImage[] CARD_ID_FONT_WHITE;
    static BufferedImage[] MONSTER_TYPE_FONT;
    static BufferedImage[] PENDULUM_SCALE_FONT;
    static BufferedImage[] EDITION_FONT;
    static BufferedImage[] EDITION_FONT_WHITE;
    
    Graphics2D g;
    
    BufferedImage imgStar;
    BufferedImage imgRank;
    BufferedImage imgNegativeStar;
    
    CardGenerator() throws IOException{
        CARD_TEMPLATES_PATH = "resources\\read_only\\card_templates\\";
        CARD_FEATURES_PATH = "resources\\read_only\\card_features\\";
        IMAGE_DIRECTORY_PATH = "resources\\read_only\\images\\";
        TEMP_DIRECTORY_PATH = "src\\tmp\\";
        IMAGE_SCALE_X = 628;
        IMAGE_SCALE_Y = 628;
        IMAGE_X_START = 100;
        IMAGE_Y_START = 221;
        ATTRIBUTE_SCALE = 78;
        ATTRIBUTE_X_START = 691;
        ATTRIBUTE_Y_START = 55;
        STAR_WIDTH = 51;
        STAR_HEIGHT = 49;
        RANK_X_START = 91;
        STAR_Y_START = 153;
        STAR_GAP = 3;
        
        initialiseImageMaps();
    }
    
    private void initialiseImageMaps() throws IOException {
        BufferedImage base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\card_number_font\\yugioh_card_number_font.png"));
        CARD_ID_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\card_number_font");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\card_number_font\\yugioh_card_number_font_white.png"));
        CARD_ID_FONT_WHITE = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\card_number_font");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\description_text\\effect_description_font.png"));
        EFFECT_DESCRIPTION_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\description_text");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\description_text\\normal_description_font.png"));
        NORMAL_DESCRIPTION_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\description_text");

        base_image = ImageIO.read(new File(FilePaths.NAME_YELLOW_FONT));
        NAME_FONT_YELLOW = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\name");

        base_image = ImageIO.read(new File(FilePaths.NAME_WHITE_FONT));
        NAME_FONT_WHITE = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\name");

        base_image = ImageIO.read(new File(FilePaths.NAME_FONT));
        NAME_FONT_PLAIN = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\name");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\serial_number_text\\serial_number.png"));
        SERIAL_NUMBER_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\serial_number_text");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\serial_number_text\\serial_number_white.png"));
        SERIAL_NUMBER_FONT_WHITE = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\serial_number_text");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\monster_type_font\\monster_type_font.png"));
        MONSTER_TYPE_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\monster_type_font");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\atk_def_text\\atk_def_font.png"));
        ATK_DEF_NUMBER_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\atk_def_text");
        
        base_image = ImageIO.read(new File("resources\\read_only\\yugioh_font\\pendulum_font\\scale\\pendulum_scale_font.png"));
        PENDULUM_SCALE_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split("resources\\read_only\\yugioh_font\\pendulum_font\\scale");
        
        base_image = ImageIO.read(new File(FilePaths.EDITION_FONT));
        EDITION_FONT = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split();
        
        base_image = ImageIO.read(new File(FilePaths.EDITION_FONT_WHITE));
        EDITION_FONT_WHITE = new ImageSplitter(base_image, YugiohFont.RED_SEPARATOR).split();
    }

    public abstract BufferedImage generate(Card card) throws IOException;

    public void saveCard(String card_name) throws IOException{
        String name = card_name.toLowerCase().replace(' ', '_');
        ImageIO.write(final_image, "PNG", new File(TEMP_DIRECTORY_PATH + name + ".png"));
    }

    protected abstract void addPicture(String image_name) throws IOException;

    protected void addAttribute(Graphics2D g, String attribute_type, int level) throws IOException{
        String attribute_path = CARD_FEATURES_PATH + "attributes\\" + attribute_type.toLowerCase().replace(' ', '_') + ".png";
        BufferedImage attribute_image = ImageIO.read(new File(attribute_path));
        
        BufferedImage thumbnail = Thumbnails.of(attribute_image)
                            .scale((float) ATTRIBUTE_SCALE/attribute_image.getWidth())
                            .asBufferedImage();
                    g.drawImage(thumbnail, ATTRIBUTE_X_START, ATTRIBUTE_Y_START, null);
                    
//        g.drawImage(attribute_image, ATTRIBUTE_X_START, ATTRIBUTE_Y_START, ATTRIBUTE_SCALE, ATTRIBUTE_SCALE, null);
    }

    protected void addCardBase(Graphics2D g, String card_base) throws IOException{
        String card_base_default_path = CARD_TEMPLATES_PATH + "default_backing.png";
        String card_base_path = CARD_TEMPLATES_PATH + card_base.toLowerCase().replace(' ', '_') + ".png";

        File file = new File(card_base_path);
        if(!file.exists() || file.isDirectory()){
            card_base_path = card_base_default_path;
        }

        BufferedImage base_image = ImageIO.read(new File(card_base_path));
//        BufferedImage base_image = files.Image.NORMAL;
        g.drawImage(base_image, 0, 0, null);
    }
    
    protected abstract void addLevel(int level, String card_type) throws IOException;
    
    protected abstract void addAtkDef(Graphics2D g, String atk, String def) throws IOException;

    public abstract void setIsPendulum(boolean isPendulum);

    protected BufferedImage getLevelImage(cardproperties.Frame card_type) {
        switch (card_type) {
            case DARK_SYNCHRO:
                return imgNegativeStar;
            case EXCEED:
                return imgRank;
            default:
                return imgStar;
        }
    }
    
    public void addPendulumScaleValue(Graphics2D g, String pendulumScaleValue) throws IOException {
        int startBlueX = PENDULUM_SCALE_VALUE_BLUE_ODD_START_X;
        int startRedX = PENDULUM_SCALE_VALUE_RED_ODD_START_X;
        
        if (Integer.parseInt(pendulumScaleValue) > 9) {
            startBlueX = PENDULUM_SCALE_VALUE_BLUE_EVEN_START_X;
            startRedX = PENDULUM_SCALE_VALUE_RED_EVEN_START_X;
        }
        
        for(int i = 0; i < pendulumScaleValue.length(); i++){
            int unicode_decimal = (int)pendulumScaleValue.charAt(i);
                
            BufferedImage img = PENDULUM_SCALE_FONT[unicode_decimal - 48];
            
            g.drawImage(img, startBlueX, PENDULUM_SCALE_VALUE_START_Y, (int) (img.getWidth() * PENDULUM_SCALE_FACTOR), (int) (img.getHeight() * PENDULUM_SCALE_FACTOR), null);
            g.drawImage(img, startRedX, PENDULUM_SCALE_VALUE_START_Y, (int) (img.getWidth() * PENDULUM_SCALE_FACTOR), (int) (img.getHeight() * PENDULUM_SCALE_FACTOR), null);
            startBlueX += img.getWidth();
            startRedX +=  img.getWidth();
        }
    }
    
    protected void drawImage(BufferedImage img, int posX, int posY, int scale) {
        g.drawImage(img, posX, posY, scale, scale, null);
    }
    
    protected void drawImage(BufferedImage img, int posX, int posY) {
        g.drawImage(img, posX, posY, null);
    }
    
    public void setPicture(String pictureName, int scale) throws IOException {
        setPicture(pictureName, scale, scale);
    }
    
    public void setPicture(String pictureName, int scaleX, int scaleY) throws IOException {
        if (this.pictureName == null || !this.pictureName.equals(pictureName)) {
            this.pictureName = pictureName;
            String image_path = IMAGE_DIRECTORY_PATH + pictureName;
            File image_file = new File(image_path);
            imgPicture = ImageIO.read(image_file);
            imgPicture = ImageUtils.toCompatibleImage(imgPicture, scaleX, scaleY);
        }
    }
    
    public void setPendulumPicture(String picturePendulumName, int scale) throws IOException {
        setPendulumPicture(picturePendulumName, scale, scale);
    }
    
    public void setPendulumPicture(String picturePendulumName, int scaleX, int scaleY) throws IOException {
        if (this.picturePendulumName == null || !this.picturePendulumName.equals(picturePendulumName)) {
            this.picturePendulumName = picturePendulumName;
            String image_path = IMAGE_DIRECTORY_PATH + pictureName;
            File image_file = new File(image_path);
            imgPendulumPicture = ImageIO.read(image_file);
            imgPendulumPicture = ImageUtils.toCompatibleImage(imgPendulumPicture, scaleX, scaleY);
        }
    }
    
}
