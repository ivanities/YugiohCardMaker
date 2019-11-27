package newclasses;

import newclasses.card.Card;

public class CardModel {

    private Card card;
    private boolean isAnime;
    private boolean isPendulum;
    private boolean showAdditionalDetails;

    public CardModel(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isAnime() {
        return isAnime;
    }

    public void setAnime(boolean isAnime) {
        this.isAnime = isAnime;
    }

    public boolean isPendulum() {
        return isPendulum;
    }

    public void setPendulum(boolean isPendulum) {
        this.isPendulum = isPendulum;
    }

    public boolean showAdditionalDetails() {
        return showAdditionalDetails;
    }

    public void setShowAdditionalDetails(boolean showAdditionalDetails) {
        this.showAdditionalDetails = showAdditionalDetails;
    }
}
