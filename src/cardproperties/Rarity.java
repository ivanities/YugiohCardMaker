package cardproperties;

import renderers.Displayable;

public enum Rarity implements Displayable {
    
    COMMON("Common"),
    RARE("rare"),
    SUPER("Super Rare"),
    ULTRA("Ultra Rare"),
    SECRET("Secret Rare"),
    ULTIMATE("Ultimate Rare"),
    PARALLEL("Parallel Rare"),
    STARFOIL("Starfoil Rare"),
    GHOST("Ghost Rare"),
    GOLD_ULTRA("Gold Ultra Rare");
    
    String rarity;
    
    Rarity(String rarity) {
       this.rarity = rarity; 
    }

    public String getRarity() {
        return rarity;
    }

    @Override
    public String toString() {
        return rarity;
    }

    @Override
    public String getDisplayString() {
        return rarity;
    }
}
