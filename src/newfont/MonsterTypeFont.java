package newfont;

public class MonsterTypeFont extends YugiohFont {

    private static final String PACKAGE_NAME = "Edition_font";
    private static final float SCALE = 20/41f;
    private static final char[] CHARACTER_MAP = "ABCDEFGHIJKLMNOPQRSTUVWXYZ[/] ".toCharArray();
//    private static final char[] CHARACTER_MAP = (
//            "ABCDEFGHIJKLMNOPQRSTUVWXYZ[/] " +
//            "abcdefghijklmnopqrstuvwxyz"
//    ).toCharArray();

    public MonsterTypeFont(FontFilename filename) {
        super(filename, SCALE);
    }

    @Override
    char[] getCharacterMap() {
        return CHARACTER_MAP;
    }

    @Override
    String getPackageName() {
        return PACKAGE_NAME;
    }

//    TODO move this into CardComponent drawing behaviour
//    TODO remove scale and add this logic by changing the font map
//    final float SCALE_UPPER_CASE = 25/42f;
//    final float SCALE_SQUARE_BRACKETS = 32/47f;
//
//    @Override
//    public float getScale() {
//        if(currentChar == '[' || currentChar == ']'){
//            return SCALE_SQUARE_BRACKETS;
//        }
//        else if(Character.isUpperCase(currentChar)){
//            return SCALE_UPPER_CASE;
//        }
//        else{
//            return super.getScale();
//        }
//    }
}
