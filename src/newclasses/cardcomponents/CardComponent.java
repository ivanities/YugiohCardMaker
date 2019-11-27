package newclasses.cardcomponents;

import newclasses.card.Card;

import java.awt.*;

public interface CardComponent<C extends Card> {

    void drawImage(Graphics2D g, C card);
}
