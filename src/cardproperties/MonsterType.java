package cardproperties;

import renderers.Displayable;

public enum MonsterType implements Displayable {

    AQUA("Aqua"),
    BEAST("Beast"),
    BEAST_WARRIOR("Beast-Warrior"),
    CREATOR_GOD("Creator God"),
    CYBERSE("Cyberse"),
    DINOSAUR("Dinosaur"),
    DIVINE_BEAST("Divine-Beast"),
    DRAGON("Dragon"),
    FAIRY("Fairy"),
    FIEND("Fiend"),
    FISH("Fish"),
    INSECT("Insect"),
    MACHINE("Machine"),
    PLANT("Plant"),
    PSYCHIC("Psychic"),
    PYRO("Pyro"),
    REPTILE("Reptile"),
    ROCK("Rock"),
    SEA_SERPENT("Sea Serpent"),
    SPELLCASTER("Spellcaster"),
    THUNDER("Thunder"),
    WARRIOR("Warrior"),
    WINGED_BEAST("Winged Beast"),
    WYRM("Wyrm"),
    ZOMBIE("Zombie");

    private String type;

    MonsterType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public String getDisplayString() {
        return type;
    }
}
