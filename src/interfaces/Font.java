package interfaces;

import constants.Scales;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static constants.FilePaths.FONTS;

public enum Font implements DrawCondition {

    ATK_DEF(createFont("anime_atk_def_text\\atk_def_font.png"), 1.2f, 48) {

        private static final int X_CAPITAL_UNICODE = 88;
        private static final int X_UNICODE = 120;
        private static final int QUESTION_MARK_UNICODE = 63;
        private static final int X = 58;
        private static final int QUESTION_MARK = 59;

        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;

            if (unicodeValue == X_UNICODE || unicodeValue == X_CAPITAL_UNICODE) {
                unicodeValue = X;
            }
            else if (unicodeValue == QUESTION_MARK_UNICODE) {
                unicodeValue = QUESTION_MARK;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }

        @Override
        public int getOffsetX(boolean isEven, int length, int size) {
            if (isEven) {
                return (length / 2 - 1) * size;
            }
            else {
                return ((length - 1) / 2) * size;
            }
        }
    },
    NAME_FONT(createFont("name\\name_font.png"), 1.14583333333f, 32) {
//    NAME_FONT(new NameFont("name\\name_font.png")) {

        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    NAME_FONT_YELLOW(createFont("name\\name_yellow_font_shelfed_for_now.png"), 1.14583333333f, 32) {

        float width_scale = 1;
        int final_width = 610;
        int current_width = 0;
        int extra_bit = 0;

        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    NAME_FONT_WHITE(createFont("name\\name_white_font.png"), 1.14583333333f, 32) {

        float width_scale = 1;
        int final_width = 610;
        int current_width = 0;
        int extra_bit = 0;

        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    NAME_FONT_RED(createFont("name\\name_red_font.png"), 1.14583333333f, 32) {

        float width_scale = 1;
        int final_width = 610;
        int current_width = 0;
        int extra_bit = 0;

        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    NORMAL_FONT(createFont("description_text\\normal_description_font.png"), 2f, 32) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            if (c == '\u25cf') {
                unicodeValue = 127;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    EFFECT_FONT(createFont("description_text\\effect_description_font.png"), 1.14583333333f, 32) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            if (c == '\u25cf') {
                unicodeValue = 127;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    REGULAR_ATK_DEF_FONT(createFont("atk_def_text\\new_atk_def_font.png"), 1.14583333333f, 48) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            if (unicodeValue == 88 || unicodeValue == 120) {
                unicodeValue = 58;
            }
            if(unicodeValue == 63){
                unicodeValue = 59;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    SERIAL_NUMBER_FONT(createFont("serial_number_text\\serial_number.png"), 1f, 48) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    SERIAL_NUMBER_FONT_WHITE(createFont("serial_number_text\\serial_number_white.png"), 1f, 48) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    CARD_ID_FONT(createFont("card_number_font\\yugioh_card_number_font.png"), 17/29f, 48) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            if((0 < unicodeValue && unicodeValue < 45)
                    || (45 < unicodeValue && unicodeValue < 48)
                    || unicodeValue > 122){
                unicodeValue = 111;
            }else if(65 <= unicodeValue && unicodeValue <= 90){
                unicodeValue -= 7;
            }else if(97 <= unicodeValue && unicodeValue <= 122){
                unicodeValue -= 13;
            }else if(unicodeValue == 45){
                unicodeValue = 110;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    CARD_ID_FONT_WHITE(createFont("card_number_font\\yugioh_card_number_font_white.png"), 17/29f, 48) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            if((0 < unicodeValue && unicodeValue < 45)
                    || (45 < unicodeValue && unicodeValue < 48)
                    || unicodeValue > 122){
                unicodeValue = 111;
            }else if(65 <= unicodeValue && unicodeValue <= 90){
                unicodeValue -= 7;
            }else if(97 <= unicodeValue && unicodeValue <= 122){
                unicodeValue -= 13;
            }else if(unicodeValue == 45){
                unicodeValue = 110;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    EDITION_FONT(createFont("Edition_font\\edition_font.png"), 18/24f, 48) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            if((0 < unicodeValue && unicodeValue < 45)
                    || (45 < unicodeValue && unicodeValue < 48)
                    || unicodeValue > 122){
                unicodeValue = 111;
            }else if(65 <= unicodeValue && unicodeValue <= 90){
                unicodeValue -= 7;
            }else if(97 <= unicodeValue && unicodeValue <= 122){
                unicodeValue -= 13;
            }else if(unicodeValue == 45){
                unicodeValue = 110;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    EDITION_FONT_WHITE(createFont("Edition_font\\edition_font_white.png"), 18/24f, 48) {
        @Override
        public BufferedImage getCharacterImage(char c) {
            int unicodeValue = (int) c;
            if((0 < unicodeValue && unicodeValue < 45)
                    || (45 < unicodeValue && unicodeValue < 48)
                    || unicodeValue > 122){
                unicodeValue = 111;
            }else if(65 <= unicodeValue && unicodeValue <= 90){
                unicodeValue -= 7;
            }else if(97 <= unicodeValue && unicodeValue <= 122){
                unicodeValue -= 13;
            }else if(unicodeValue == 45){
                unicodeValue = 110;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }
    },
    MONSTER_TYPE_FONT(createFont("monster_type_font\\monster_type_font.png"), 20/41f, 65) {

        final float SCALE_UPPER_CASE = 25/42f;
        final float SCALE_SQUARE_BRACKETS = 32/47f;

        char currentChar;

        @Override
        public BufferedImage getCharacterImage(char c) {
            currentChar = c;
            int unicodeValue = (int) c;
            if(unicodeValue == 47){
                unicodeValue = 92;
            }else if((0 < unicodeValue && unicodeValue < 65)
                    || (unicodeValue > 93 && unicodeValue < 97)){
                unicodeValue = 94;
            }else if(97 <= unicodeValue && unicodeValue <= 122){
                unicodeValue -= 32;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }

        @Override
        public float getScale() {
            if(currentChar == '[' || currentChar == ']'){
                return SCALE_SQUARE_BRACKETS;
            }
            else if(Character.isUpperCase(currentChar)){
                return SCALE_UPPER_CASE;
            }
            else{
                return super.getScale();
            }
        }
    },
    SPELL_TRAP_TYPE_FONT(createFont("monster_type_font\\monster_type_font.png"), 20/41f * Scales.SPELL_TRAP_TYPE_SCALE, 65) {

        final float SCALE_UPPER_CASE = 25/42f * Scales.SPELL_TRAP_TYPE_SCALE;
        final float SCALE_SQUARE_BRACKETS = 27/47f * Scales.SPELL_TRAP_TYPE_SCALE;

        char currentChar;

        @Override
        public BufferedImage getCharacterImage(char c) {
            currentChar = c;
            int unicodeValue = (int) c;
            if(unicodeValue == 47){
                unicodeValue = 92;
            } else if((0 < unicodeValue && unicodeValue < 65)
                    || (unicodeValue > 93 && unicodeValue < 97)){
                unicodeValue = 94;
            } else if(97 <= unicodeValue && unicodeValue <= 122){
                unicodeValue -= 32;
            }
            return getFont()[unicodeValue - getIndexOffset()];
        }

        @Override
        public float getScale() {
            if(currentChar == '[' || currentChar == ']'){
                return SCALE_SQUARE_BRACKETS;
            }
            else if(Character.isUpperCase(currentChar)){
                return SCALE_UPPER_CASE;
            }
            else{
                return super.getScale();
            }
        }
    };

    private BufferedImage[] font;
    private final float scale;
    private final int indexOffset;

    Font(BufferedImage[] font, float scale, int indexOffset) {
        this.font = font;
        this.scale = scale;
        this.indexOffset = indexOffset;
    }

    public BufferedImage[] getFont() {
        return font;
    }

    public float getScale() {
        return scale;
    }

    public int getIndexOffset() {
        return indexOffset;
    }

    private static BufferedImage[] createFont(String filePath) {
        try {
            return ImageSplitter.split(ImageIO.read(new File(FONTS + filePath)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getOffsetX(boolean isEven, int length, int size) {
        throw new UnsupportedOperationException();
    }
}
