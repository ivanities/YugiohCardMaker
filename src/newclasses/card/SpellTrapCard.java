package newclasses.card;

import cardproperties.Property;

public class SpellTrapCard extends Card {

    private Property property;

    public SpellTrapCard() {
    }

    public SpellTrapCard(Card card) {
        super(card);
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
