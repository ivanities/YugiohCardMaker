package newclasses.cardcomponents;

import cardproperties.Ability;
import cardproperties.Frame;
import interfaces.Font;
import newclasses.card.MonsterCard;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.COORDINATES;

public class MonsterTypeComponent implements CardComponent<MonsterCard> {

    private static final String KEY = "monsterType";
    private static final Font FONT = Font.MONSTER_TYPE_FONT;

    @Override
    public void drawImage(Graphics2D g, MonsterCard card) {
        String displayString = getMonsterTypeDisplayString(card);
        Point point = COORDINATES.get(KEY);
        float scale;
        int x = 0;
        int y;

        for (int i = 0; i < displayString.length(); i++) {
            char c = displayString.charAt(i);
            BufferedImage img = FONT.getCharacterImage(c);
            scale = FONT.getScale();
            y = getMonsterTypeY(c);

            if (c == ' ') {
                x-=6;
            }

            g.drawImage(img, ((int) point.getX() + x), y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += (int)(img.getWidth() * scale) + 2;
            if( i != displayString.length() -1 && (c == '[' || displayString.charAt(i+1) == ']')){
                x += 3;
            }
        }
    }

    private String getMonsterTypeDisplayString(MonsterCard card) {
        String displayString = "[" + card.getMonsterType().getDisplayString();

        if(!Frame.getUndisplayableBases().contains(card.getFrame())){
            displayString += " / " + card.getFrame().getDisplayString();
        }
        if(!card.getAbility().equals(Ability.NONE)){
            displayString += " / " + card.getAbility().getDisplayString();
        }
        if(card.isHasEffect()){
            displayString += " / Effect";
        }
        displayString += "]";

        return displayString;
    }

    private int getMonsterTypeY(char c) {
        final int START_Y_SQUARE_BRACKETS = 904, START_Y_UPPER_CASE = 906, START_Y_LOWER_CASE = 910;

        if(c == '[' || c == ']'){
            return START_Y_SQUARE_BRACKETS;
        }
        else if(Character.isUpperCase(c)){
            return START_Y_UPPER_CASE;
        }
        return START_Y_LOWER_CASE;
    }
}
