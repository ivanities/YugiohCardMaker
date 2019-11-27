package newclasses.cardcomponents;

import newclasses.card.Card;
import newclasses.card.PendulumMonsterCard;
import newclasses.description.DescriptionBuilderV2;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cardproperties.Coordinates.PENDULUM_COORDINATES;

public class PendulumDescriptionComponent implements CardComponent {

    private static final String KEY = "PendulumDescription";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        String description = ((PendulumMonsterCard) card).getPendulumDescription();
        Point point = PENDULUM_COORDINATES.get(KEY);

        BufferedImage descriptionImage = DescriptionBuilderV2.getPendulumDescBuilder()
                .getDescriptionImage(card.getFrame(), description);

        ImageUtils.drawImage(g, descriptionImage, point);
    }
}
