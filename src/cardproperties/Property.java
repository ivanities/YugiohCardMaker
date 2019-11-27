package cardproperties;

import renderers.Displayable;

import java.awt.image.BufferedImage;

import static constants.FilePaths.PROPERTY;
import static utils.ImageUtils.loadImage;
import static utils.ImageUtils.toCompatibleImage;

public enum Property implements Displayable {

    NORMAL("Normal"),
    CONTINUOUS("Continuous"),
    COUNTER("Counter"),
    EQUIP("Equip"),
    FIELD("Field"),
    QUICK_PLAY("Quick-Play"),
    RITUAL("Ritual");

    private static final Property[] SPELL_PROPERTIES = new Property[]{NORMAL, CONTINUOUS, EQUIP, FIELD, QUICK_PLAY, RITUAL};
    private static final Property[] TRAP_PROPERTIES = new Property[]{NORMAL, CONTINUOUS, COUNTER};

    private String property;
    private BufferedImage image;

    Property(String property) {
       this.property = property;
        if (!property.equals("Normal")) {
            image = toCompatibleImage(loadImage(PROPERTY + getDisplayString().toLowerCase().replace('-', '_') + ".png"));
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getProperty(){
        return property;
    }
    
    public static Property[] getSpellProperties() {
        return SPELL_PROPERTIES;
    }
    
    public static Property[] getTrapProperties() {
        return TRAP_PROPERTIES;
    }
    
    @Override
    public String toString() {
        return property;
    }

    @Override
    public String getDisplayString() {
        return property;
    }
}

