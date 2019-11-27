package newclasses.cardcomponents;

import cardproperties.Frame;
import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.description.DescriptionBuilderV2;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.COORDINATES;

public class DescriptionComponent implements CardComponent {

    private static final String MONSTER_KEY = "monsterDescription";
    private static final String SPELL_TRAP_KEY = "spellTrapDescription";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        Point point = getDescriptionCoordinates(card);
        BufferedImage img = getDescriptionImage(card);
        ImageUtils.drawImage(g, img, point);
    }

    private Point getDescriptionCoordinates(Card card) {
        if (card instanceof MonsterCard) {
            return COORDINATES.get(MONSTER_KEY);
        }
        return COORDINATES.get(SPELL_TRAP_KEY);
    }

    private BufferedImage getDescriptionImage(Card card) {
        DescriptionBuilderV2 builder = getDescriptionBuilder(card);
        String description = card.getDescription();
        Frame frame = card.getFrame();

        if (Frame.EXTRA_DECK_FRAMES.contains(frame)) {
            String materials = ((MonsterCard) card).getMaterials();

            if (hasValidMaterials(materials)) {
                return builder.getDescriptionWithMaterialsImage(frame, description, materials);
            }
        }
        return builder.getDescriptionImage(frame, description);
    }

    private DescriptionBuilderV2 getDescriptionBuilder(Card card) {
        if (card instanceof MonsterCard) {
            return DescriptionBuilderV2.getMonsterDescBuilder();
        }
        return DescriptionBuilderV2.getSpellTrapDescBuilder();
    }

    private boolean hasValidMaterials(String materials) {
        return materials != null && materials.length() > 0;
    }
}
