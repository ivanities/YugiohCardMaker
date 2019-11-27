package cardproperties;

import renderers.Displayable;

public enum Limitation implements Displayable {

    NON_DUEL("This card cannot be used in a Duel."),
    NON_DECK("This card cannot be in a Deck."),
    NOT_OFFICIAL_DUEL("Not usable in Official Duels."),
    NOT_OFFICIAL_CARD("Not an Official Yu-Gi-Oh! TCG Card.");
    
    private String limitation;
    
    Limitation(String limitation) {
       this.limitation = limitation; 
    }

    public String getLimitation(){
        return limitation;
    }

    public static Limitation getLimitationText(String text) {
        for (Limitation limitation : Limitation.values()) {
            if (limitation.getLimitation().equals(text)) {
                return limitation;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return limitation;
    }

    @Override
    public String getDisplayString() {
        return limitation;
    }
}
