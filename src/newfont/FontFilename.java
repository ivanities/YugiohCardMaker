package newfont;

public enum FontFilename {

    ANIME_ATK_DEF("atk_def_font.png"),
    ATK_DEF("new_atk_def_font.png"),
    NAME("name_font.png"),
    NAME_YELLOW("name_yellow_font_shelfed_for_now.png"),
    NAME_WHITE("name_white_font.png"),
    NAME_RED("name_red_font.png"),
    EFFECT("effect_description_font.png"),
    SERIAL_NUMBER("serial_number.png"),
    SERIAL_NUMBER_WHITE("serial_number_white.png"),
    CARD_ID("yugioh_card_number_font.png"),
    CARD_ID_WHITE("yugioh_card_number_font_white.png"),
    EDITION("edition_font.png"),
    EDITION_WHITE("edition_font_white.png"),

    MONSTER_TYPE("monster_type_font\\monster_type_font.png"),
    SPELL_TRAP_TYPE("monster_type_font\\monster_type_font.png"),
    PENDULUM("pendulum_scale_font.png");

    private String name;

    FontFilename(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
