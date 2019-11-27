package newclasses.cardcomponents;

import cardproperties.Frame;
import cardproperties.Property;
import constants.Coordinates;
import constants.Scales;
import interfaces.Font;
import newclasses.card.Card;
import newclasses.card.SpellTrapCard;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.COORDINATES;

public class SpellTrapTypeComponent implements CardComponent {

    private static final String KEY = "spellTrapType";
    private static final Font FONT = Font.SPELL_TRAP_TYPE_FONT;

    @Override
    public void drawImage(Graphics2D g, Card card) {
        SpellTrapCard spellTrapCard = (SpellTrapCard) card;
        Frame frame = spellTrapCard.getFrame();
        Property property = spellTrapCard.getProperty();

        Point point = COORDINATES.get(KEY);
        float scale;
        int x = 0;
        int y;

        String displayString = "[" + frame.getDisplayString() + " Card";
        displayString += Property.NORMAL.equals(property) ? "]" : "   ]";

        for(int i = displayString.length() - 1; i >= 0; i--){
            char c = displayString.charAt(i);
            BufferedImage img = FONT.getCharacterImage(c);
            scale = FONT.getScale();
            y = getSpellTrapTypeY(c);

            if( i != displayString.length() - 1 && (displayString.charAt(i) == '[' || displayString.charAt(i+1) == ']')){
                x += 3;
            }
            x += (int) (img.getWidth() * scale);
            g.drawImage(img, (Coordinates.SPELL_TRAP_TYPE_START_X - x), y, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
            x += 2;
        }
        if (!Property.NORMAL.equals(property)) {
            ImageUtils.drawImage(g, property.getImage(), point);
        }
    }

    private int getSpellTrapTypeY(char c) {
        final double START_Y_UPPER_CASE = Coordinates.SPELL_TRAP_TYPE_START_Y + 2 + 2 * Scales.SPELL_TRAP_TYPE_SCALE;
        final double START_Y_LOWER_CASE = Coordinates.SPELL_TRAP_TYPE_START_Y + 2 + 6 * Scales.SPELL_TRAP_TYPE_SCALE;

        if (c == '[' || c == ']') {
            return Coordinates.SPELL_TRAP_TYPE_START_Y + 5;
        }
        else if (Character.isUpperCase(c)) {
            return (int) START_Y_UPPER_CASE;
        }
        else {
            return (int) START_Y_LOWER_CASE;
        }
    }
}
