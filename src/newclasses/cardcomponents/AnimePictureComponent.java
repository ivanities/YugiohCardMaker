package newclasses.cardcomponents;

import cardproperties.Coordinates;
import newclasses.card.Card;
import utils.ImageUtils;

import java.awt.*;

public class AnimePictureComponent implements CardComponent<Card> {

    private static final String KEY = "Picture";

    @Override
    public void drawImage(Graphics2D g, Card card) {
        Point point = Coordinates.ANIME_COORDINATES.get(KEY);
        ImageUtils.drawImage(g, card.getAnimeImage(), point);
    }
}
