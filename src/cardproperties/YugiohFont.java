package cardproperties;

import constants.FilePaths;
import images.ImageSplitter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YugiohFont {

    public static final int RED_SEPARATOR = 0xffed0000;
    
    public static final BufferedImage[] CARD_ID_FONT            = loadFont("resources\\read_only\\yugioh_font\\card_number_font\\yugioh_card_number_font.png");
    public static final BufferedImage[] EFFECT_DESCRIPTION_FONT = loadFont("resources\\read_only\\yugioh_font\\description_text\\effect_description_font.png");
    public static final BufferedImage[] NAME_FONT_YELLOW        = loadFont(FilePaths.NAME_YELLOW_FONT);
    public static final BufferedImage[] NAME_FONT_WHITE         = loadFont(FilePaths.NAME_WHITE_FONT);
    public static final BufferedImage[] NAME_FONT_PLAIN         = loadFont(FilePaths.NAME_FONT);
    public static final BufferedImage[] SERIAL_NUMBER_FONT      = loadFont(FilePaths.SERIAL_NUMBER_FONT);
    public static final BufferedImage[] MONSTER_TYPE_FONT       = loadFont("resources\\read_only\\yugioh_font\\description_text\\monster_type_font.png");
    public static final BufferedImage[] ATK_DEF_NUMBER_FONT     = loadFont("resources\\read_only\\yugioh_font\\description_text\\atk_def_font.png");
    public static final BufferedImage[] PENDULUM_SCALE_FONT     = loadFont("resources\\read_only\\yugioh_font\\description_text\\pendulum_scale_font.png");
    public static final BufferedImage[] EDITION_FONT            = loadFont(FilePaths.EDITION_FONT);
    
    private static BufferedImage[] loadFont(String filePath) {
        BufferedImage[] font = null;
        try {
            BufferedImage base_image = ImageIO.read(new File(filePath));
            font = new ImageSplitter(base_image, RED_SEPARATOR).split();
        }
        catch (IOException ex) {
            Logger.getLogger(YugiohFont.class.getName()).log(Level.SEVERE, null, ex);
        }
        return font;
    }
}
