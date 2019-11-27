package cardproperties;

import renderers.Displayable;

public enum Ability implements Displayable {
    
    NONE("None"),
    FLIP("Flip"),
    TOON("Toon"),
    UNION("Union"),
    SPIRIT("Spirit"),
    GEMINI("Gemini"),
    TUNER("Tuner"),
    DARK_TUNER("Dark Tuner");
    
    private String ability;
    
    Ability(String ability) {
       this.ability = ability; 
    }

    public String getAbility(){
        return ability;
    }

    public static Ability[] getEffect() {
        return Ability.values();
    }

    public static Ability[] getNonEffect() {
        return new Ability[]{NONE, UNION, TUNER, DARK_TUNER};
    }

    @Override
    public String toString() {
        return ability;
    }

    @Override
    public String getDisplayString() {
        return ability;
    }
}
