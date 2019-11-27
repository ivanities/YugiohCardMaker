package newclasses.card;

public class PendulumMonsterCard extends MonsterCard {

    private int pendulumScale;
    private String pendulumDescription;

    public PendulumMonsterCard(Card card) {
        super(card);
    }

    public int getPendulumScale() {
        return pendulumScale;
    }

    public void setPendulumScale(int pendulumScale) {
        this.pendulumScale = pendulumScale;
    }

    public String getPendulumDescription() {
        return pendulumDescription;
    }

    public void setPendulumDescription(String pendulumDescription) {
        this.pendulumDescription = pendulumDescription;
    }
}
