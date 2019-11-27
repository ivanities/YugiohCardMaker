package newfont;

public class AnimeAttackDefenseFont extends YugiohFont {

    private static final String PACKAGE_NAME = "anime_atk_def_text";
    private static final float SCALE = 1.2f;
    private static final char[] CHARACTER_MAP = "0123456789X?".toCharArray();

    public AnimeAttackDefenseFont() {
        super(FontFilename.ANIME_ATK_DEF, SCALE);
    }

    @Override
    char[] getCharacterMap() {
        return CHARACTER_MAP;
    }

    @Override
    String getPackageName() {
        return PACKAGE_NAME;
    }
}
