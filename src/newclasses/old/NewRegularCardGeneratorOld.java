package newclasses.old;

import cardproperties.*;
import cardproperties.Frame;
import constants.Coordinates;
import constants.FilePaths;
import constants.Scales;
import interfaces.Font;
import net.coobird.thumbnailator.Thumbnails;
import newclasses.CardController;
import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;
import newclasses.description.DescriptionBuilderV2;
import newclasses.description.font.DescriptionFont;
import newclasses.description.font.PendulumScaleFont;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static cardproperties.Coordinates.*;
import static files.Image.getLevelImage;
import static files.Image.getRegularLevelX;

public class NewRegularCardGeneratorOld extends NewCardGeneratorOld {

    private String description;
    private BufferedImage descriptionImage;

    public NewRegularCardGeneratorOld(CardController controller) {
        super(controller);
        DescriptionFont.initializeFontMap();
    }

    private void addName(String name, Rarity rarity, Frame frame) {
        Font font = getNameFont(rarity, frame);

        int x = 0;
        float scale = 1.14583333333f;
        float width_scale = 1;
        int final_width = 610;
        int current_width = 0;
        int extra_bit = 0;

        int startX = getRegularCoordinateX("Name");
        int startY = getRegularCoordinateY("Name");

        for(int i = 0; i < name.length(); i++){
            BufferedImage img = font.getCharacterImage(name.charAt(i));
            current_width += (int)(img.getWidth() * scale);
            extra_bit++;
        }
        final_width -= extra_bit;
        if (current_width > final_width) {
            width_scale = ((float)final_width/(float)current_width);
        }

        for(int i = 0; i < name.length(); i++){
            BufferedImage img = font.getCharacterImage(name.charAt(i));
            g.drawImage(img, (startX + x), startY, (int)(img.getWidth() * scale * width_scale), (int)(img.getHeight() * scale), null);
            x += (int)((img.getWidth() * scale * width_scale) + 1);
        }
    }

    private Font getNameFont(Rarity rarity, Frame frame) {
        if (rarity.equals(Rarity.ULTRA) || rarity.equals(Rarity.ULTIMATE)) {
            if (Frame.EXCEED.equals(frame)) {
                return Font.NAME_FONT_RED;
            }
            else {
                return Font.NAME_FONT_YELLOW;
            }
        }
        else if (rarity.equals(Rarity.SUPER)
                || rarity.equals(Rarity.SECRET)
                || frame.equals(Frame.EXCEED)
                || frame.equals(Frame.DARK_SYNCHRO)
                || frame.equals(Frame.WICKED_GOD)) {
            return Font.NAME_FONT_WHITE;
        }
        return Font.NAME_FONT;
    }

    @Override
    protected void addLevel(int level, Frame frame) {
        BufferedImage imgLevel = getLevelImage(frame);

        for (int i = 0; i < level; i++) {
            g.drawImage(imgLevel, getRegularLevelX(level, frame, i), getRegularCoordinateY("Rank"), null);
        }
    }

    private void addCardID(String cardID, Frame frame) throws IOException{
        if (isPendulum()) {
            addPendulumCardID(cardID);
        }
        else {
            addNormalCardID(cardID, frame);
        }
    }

    private void addNormalCardID(String cardID, Frame frame) throws IOException{
        Point point = COORDINATES.get("CardID");
        Font font = useWhiteFont(frame) ? Font.CARD_ID_FONT_WHITE : Font.CARD_ID_FONT;
        float scale = Scales.CARD_ID;

        int x = 0;
        for(int i = cardID.length() - 1; i >= 0; i--){
            BufferedImage img = font.getCharacterImage(cardID.charAt(i));

            x += (int)(img.getWidth() * Scales.CARD_ID);
            g.drawImage(img, ((int) point.getX() - x), (int) point.getY(), (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += 1;
        }
    }

    private void addPendulumCardID(String cardID) throws IOException{
        Point point = PENDULUM_COORDINATES.get("CardID");
        Font font = Font.CARD_ID_FONT;
        float scale = Scales.CARD_ID;

        int x = 0;
        for(int i = 0; i < cardID.length(); i++){
            BufferedImage img = font.getCharacterImage(cardID.charAt(i));

            g.drawImage(img, ((int) point.getX() + x), (int) point.getY(), (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
            x += (int)(img.getWidth() * Scales.CARD_ID) + 1;
        }
    }

    private void addMonsterType(MonsterType monsterType, Ability ability, Frame frame, boolean hasEffect) throws IOException{
        String displayString = getMonsterTypeDisplayString(monsterType, ability, frame, hasEffect);
        Point point = COORDINATES.get("monsterType");
        Font font = Font.MONSTER_TYPE_FONT;
        float scale;
        int x = 0;
        int y;

        for (int i = 0; i < displayString.length(); i++) {
            char c = displayString.charAt(i);
            BufferedImage img = font.getCharacterImage(c);
            scale = font.getScale();
            y = getMonsterTypeY(c);

            if (c == ' ') {
                x-=6;
            }

            g.drawImage(img, ((int) point.getX() + x), y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += (int)(img.getWidth() * scale) + 2;
            if( i != displayString.length() -1 && (c == '[' || displayString.charAt(i+1) == ']')){
                x += 3;
            }
        }
    }

    private int getMonsterTypeY(char c) {
        final int START_Y_SQUARE_BRACKETS = 904, START_Y_UPPER_CASE = 906, START_Y_LOWER_CASE = 910;

        if(c == '[' || c == ']'){
            return START_Y_SQUARE_BRACKETS;
        }
        else if(Character.isUpperCase(c)){
            return START_Y_UPPER_CASE;
        }
        else{
            return START_Y_LOWER_CASE;
        }
    }

    private String getMonsterTypeDisplayString(MonsterType monsterType, Ability ability, Frame frame, boolean hasEffect) {
        String displayString = "[" + monsterType.getDisplayString();

        if(!Frame.getUndisplayableBases().contains(frame)){
            displayString += " / " + frame.getDisplayString();
        }
        if(!ability.equals(Ability.NONE)){
            displayString += " / " + ability.getDisplayString();
        }
        if(hasEffect){
            displayString += " / Effect";
        }
        displayString += "]";

        return displayString;
    }

    private void addSpellTrapType(Frame frame, Property property) throws IOException{
        Point point = COORDINATES.get("spellTrapType");
        Font font = Font.SPELL_TRAP_TYPE_FONT;
        float scale;
        int x = 0;
        int y;

        String displayString = "[" + frame.getDisplayString() + " Card";
        displayString += property.equals(Property.NORMAL) ? "]" : "   ]";

        for(int i = displayString.length() - 1; i >= 0; i--){
            char c = displayString.charAt(i);
            BufferedImage img = font.getCharacterImage(c);
            scale = font.getScale();

            y = getSpellTrapTypeY(c);

            if( i != displayString.length() - 1 && (displayString.charAt(i) == '[' || displayString.charAt(i+1) == ']')){
                x += 3;
            }
            x += (int)(img.getWidth() * scale);
            g.drawImage(img, (Coordinates.SPELL_TRAP_TYPE_START_X - x), y, (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += 2;
        }
        if (!property.equals(Property.NORMAL)) {
            ImageUtils.drawImage(g, property.getImage(), point);
        }
    }

    private int getSpellTrapTypeY(char c) {
        final double START_Y_UPPER_CASE = Coordinates.SPELL_TRAP_TYPE_START_Y + 2 + 2 * Scales.SPELL_TRAP_TYPE_SCALE;
        final double START_Y_LOWER_CASE = Coordinates.SPELL_TRAP_TYPE_START_Y + 2 + 6 * Scales.SPELL_TRAP_TYPE_SCALE;

        if (c == '[' || c == ']') {
            return Coordinates.SPELL_TRAP_TYPE_START_Y + 5;
        }
        else if (Character.isUpperCase(c)) {
            return (int) START_Y_UPPER_CASE;
        }
        else {
            return (int) START_Y_LOWER_CASE;
        }
    }

    @Override
    protected void addAtkDef(String atk, String def) throws IOException {
        final int SPACE = 19;
        BufferedImage AtkDefBar = ImageIO.read(new File(FilePaths.ATK_DEF_BAR));
        g.drawImage(AtkDefBar, 63, 1092, AtkDefBar.getWidth(), AtkDefBar.getHeight(), null);

        final int DIGITS = atk.length() > def.length() ? atk.length() : def.length();

        addAtkDefValue(def, Coordinates.ATK_DEF_X);
        int startX = addAtkDefLabel(DIGITS, Coordinates.ATK_DEF_X, FilePaths.DEF);
        addAtkDefValue(atk, startX - SPACE);
        addAtkDefLabel(DIGITS, startX - SPACE, FilePaths.ATK);
    }

    private int addAtkDefLabel(int digits, int startX, String filePath) throws IOException {
        final int DEFAULT_DIGITS = 4;
        final int DEFAULT_DIGIT_WIDTH = 18 + 1;
        int numberOfDigitsMissing;
        if (digits < DEFAULT_DIGITS) {
            numberOfDigitsMissing = DEFAULT_DIGITS;
        }
        else {
            numberOfDigitsMissing = digits;
        }
        BufferedImage atkDefLabel = ImageIO.read(new File(filePath));
        startX -= atkDefLabel.getWidth() + 1 + numberOfDigitsMissing * DEFAULT_DIGIT_WIDTH;
        g.drawImage(atkDefLabel, startX, Coordinates.ATK_DEF_Y, atkDefLabel.getWidth(), atkDefLabel.getHeight(), null);

        return startX;
    }

    private int addAtkDefValue(String atkDef, int startX) {
        Font font = Font.REGULAR_ATK_DEF_FONT;
        for(int i = atkDef.length() - 1; i >= 0; i--) {
            BufferedImage img = font.getCharacterImage(atkDef.charAt(i));

            startX -= Scales.ATK_DEF_X;
            g.drawImage(img, startX, Coordinates.ATK_DEF_Y, Scales.ATK_DEF_X, Scales.ATK_DEF_Y, null);
            startX -= 1;
        }
        return startX;
    }

    @Override
    protected void addAttribute(Attribute attribute, int level) throws IOException {
        drawImage(attribute, "Attribute", Scales.ATTRIBUTE_SCALE);
    }

    private void addDescriptionFont(Card card) {
        String description = card.getDescription();
        Point point = getDescriptionCoordinates(card);
        Frame frame = card.getFrame();

//        if (this.description == null || !this.description.equals(description)) {
            this.description = description;
            if (Frame.EXTRA_DECK_FRAMES.contains(frame)) {
                String materials = ((MonsterCard) card).getMaterials();

                if (materials == null || materials.length() == 0) {
                    descriptionImage = getDescriptionBuilder(card).getDescriptionImage(frame, description);
                }
                else {
                    descriptionImage = getDescriptionBuilder(card).getDescriptionWithMaterialsImage(frame, description, materials);
                }
            }
            else {
                descriptionImage = getDescriptionBuilder(card).getDescriptionImage(frame, description);
            }
//        }
        ImageUtils.drawImage(g, descriptionImage, point);
    }

    private Point getDescriptionCoordinates(Card card) {
        if (card instanceof MonsterCard) {
            return COORDINATES.get("monsterDescription");
        }
        else {
            return COORDINATES.get("spellTrapDescription");
        }
    }

    private DescriptionBuilderV2 getDescriptionBuilder(Card card) {
        if (card instanceof MonsterCard) {
            return DescriptionBuilderV2.getMonsterDescBuilder();
        }
        else {
            return DescriptionBuilderV2.getSpellTrapDescBuilder();
        }
    }

    private void addPendulumDescriptionFont(PendulumMonsterCard card) {
        String description = card.getPendulumDescription();
        Point point = PENDULUM_COORDINATES.get("PendulumDescription");

//        if (this.description == null || !this.description.equals(description)) {
//            this.description = description;
        BufferedImage descriptionImage = DescriptionBuilderV2.getPendulumDescBuilder().getDescriptionImage(card.getFrame(), description);
//        }
        ImageUtils.drawImage(g, descriptionImage, point);
    }

    private void addEdition(Edition edition, Frame frame) {
        Point point = COORDINATES.get("Edition");
        Font font = useWhiteFont(frame) ? Font.EDITION_FONT_WHITE : Font.EDITION_FONT;
        String display = edition.getDisplayString();
        int x = 0;
        float scale;

        for(int i = 0; i < display.length(); i++){
            BufferedImage img = font.getCharacterImage(display.charAt(i));
            scale = editionNumberCheck(font, display, i);

            g.drawImage(img, ((int) point.getX() + x), (int) point.getY(), (int)(img.getWidth() * scale), (int)(img.getHeight() * scale), null);
            x += (int)(img.getWidth() * scale) + 1;
        }
    }

    private float editionNumberCheck(Font font, String edition, int i) {
        if (i != 0 && edition.substring(i-1, i).matches("[0-9]+")) {
            return Scales.EDITION_SMALL_SCALE;
        }
        else if (i != 0 && i != 1 && edition.substring(i-2, i-1).matches("[0-9]+")) {
            return Scales.EDITION_SMALL_SCALE;
        }
        else {
            return font.getScale();
        }
    }

    private void addSerialLimit(String serial_number, Frame frame) throws IOException {
        if(serial_number.matches("[0-9]+")){
            addSerialNumber(serial_number, frame);
        }
        else {
            addSerialText(serial_number, frame);
        }
    }

    private void addSerialNumber(String serialNumber, Frame frame) {
        if (!serialNumber.equals("")) {
            Point point = COORDINATES.get("serialNumber");
            Font font = useWhiteFont(frame) ? Font.SERIAL_NUMBER_FONT_WHITE : Font.SERIAL_NUMBER_FONT;

            int x = 0;
            for(int i = 0; i < serialNumber.length(); i++){
                BufferedImage img = font.getCharacterImage(serialNumber.charAt(i));

                g.drawImage(img, ((int) point.getX() + x), (int) point.getY(), null);
                x += img.getWidth() + 2;
            }
        }
    }

    private void addSerialText(String serialText, Frame frame) {
        if (!serialText.equals("")) {
            Point point = COORDINATES.get("serialText");
            Font font = useWhiteFont(frame) ? Font.CARD_ID_FONT_WHITE : Font.CARD_ID_FONT;
            float scale = font.getScale();

            int x = 0;
            for (int i = 0; i < serialText.length(); i++) {
                BufferedImage img = font.getCharacterImage(serialText.charAt(i));

                g.drawImage(img, ((int) point.getX() + x), (int) point.getY(), (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
                x += (int) (img.getWidth() * scale) + 1;
            }
        }
    }

    private void addCreator(String creator, Frame frame) {
        Point point = COORDINATES.get("creator");
        Font font = useWhiteFont(frame) ? Font.CARD_ID_FONT_WHITE : Font.CARD_ID_FONT;
        float scale = Scales.CREATOR;

        int x = 0;
        for(int i = creator.length() - 1; i >= 0; i--){
            BufferedImage img = font.getCharacterImage(creator.charAt(i));

            x += (int)(img.getWidth() * scale);

            BufferedImage thumbnail = null;
            try {
                thumbnail = Thumbnails.of(img)
                        .scale(scale)
                        .asBufferedImage();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(thumbnail, ((int) point.getX()- x), (int) point.getY(), null);
            x += 1;
        }

        addCopyRightSign(frame, x + 2);
    }

    private void addCopyRightSign(Frame frame, int x) {
        Point point = COORDINATES.get("creator");
        BufferedImage img = useWhiteFont(frame) ? CopyRightSymbol.WHITE.getImage() : CopyRightSymbol.BLACK.getImage();

        x += img.getWidth();
        g.drawImage(img, ((int) point.getX()- x), (int) point.getY(), null);
    }

    private boolean useWhiteFont(Frame frame) {
        return frame.equals(Frame.EXCEED)
                || frame.equals(Frame.DARK_SYNCHRO)
                || frame.equals(Frame.WICKED_GOD);
    }

    private void addSerialTag(Edition edition) {
        Point point = COORDINATES.get("eyeOfAnubisHologram");
        BufferedImage image;

        if (edition.equals(Edition.UNLIMITED)) {
            image = EyeOfAnubisHologram.SILVER.getHologram();
        }
        else {
            image = EyeOfAnubisHologram.GOLD.getHologram();
        }
        ImageUtils.drawImage(g, image, point);
    }

    @Override
    protected void addPendulumScales() {
        Point point = PENDULUM_COORDINATES.get("PendulumScaleBlue");
        ImageUtils.drawImage(g, files.Image.getPendulumScaleBlue(), point);

        point = PENDULUM_COORDINATES.get("PendulumScaleRed");
        ImageUtils.drawImage(g, files.Image.getPendulumScaleRed(), point);
    }

    @Override
    public void addPendulumScaleValue(String pendulumScaleValue) {
        newclasses.description.font.Font font = PendulumScaleFont.getRegularFont();
        final float SCALE = font.getScale();
        Point pointBlueScale;
        Point pointRedScale;
        int startBlueX;
        int startRedX;
        int startY = cardproperties.Coordinates.getPendulumCoordinateY("PendulumScaleBlueValueOdd");

        if (Integer.parseInt(pendulumScaleValue) > 9) {
            pointBlueScale = PENDULUM_COORDINATES.get("PendulumScaleBlueValueEven");
            pointRedScale = PENDULUM_COORDINATES.get("PendulumScaleRedValueEven");
        }
        else {
            pointBlueScale = PENDULUM_COORDINATES.get("PendulumScaleBlueValueOdd");
            pointRedScale = PENDULUM_COORDINATES.get("PendulumScaleRedValueOdd");
        }

        startBlueX = (int) pointBlueScale.getX();
        startRedX = (int) pointRedScale.getX();

        for(int i = 0; i < pendulumScaleValue.length(); i++){
            BufferedImage img = font.getCharacterImage(pendulumScaleValue.charAt(i));

            g.drawImage(img, startBlueX, startY, (int) (img.getWidth() * SCALE), (int) (img.getHeight() * SCALE), null);
            g.drawImage(img, startRedX, startY, (int) (img.getWidth() * SCALE), (int) (img.getHeight() * SCALE), null);
            startBlueX += img.getWidth();
            startRedX +=  img.getWidth();
        }
    }

    @Override
    public String getSavePath() {
        return FilePaths.TEMP_DIR;
    }

    @Override
    public BufferedImage generate(Card card) throws IOException {
        super.generate(card);
        addName(card.getName(), card.getRarity(), card.getFrame());
        addDescriptionFont(card);
//
//        if (controller.showAdditionalDetails()) {
//            addCardID(card.getCardID(), card.getFrame());
//            addSerialLimit(card.getSerialCode(), card.getFrame());
//            addCreator(card.getYear() + " " + card.getCreator(), card.getFrame());
//            addSerialTag(card.getEdition());
//            addEdition(card.getEdition(), card.getFrame());
//        }

        if (card instanceof MonsterCard) {
            MonsterCard monsterCard = (MonsterCard) card;
            addMonsterType(monsterCard.getMonsterType(), monsterCard.getAbility(), monsterCard.getFrame(), monsterCard.isHasEffect());

            if (monsterCard instanceof PendulumMonsterCard) {
                addPendulumDescriptionFont((PendulumMonsterCard) monsterCard);
            }
        }
        else if (card instanceof SpellTrapCard) {
            SpellTrapCard spellTrapCard = (SpellTrapCard) card;
            addSpellTrapType(spellTrapCard.getFrame(), spellTrapCard.getProperty());
        }

        return finalImage;
    }
}
