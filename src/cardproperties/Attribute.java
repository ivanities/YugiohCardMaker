package cardproperties;

import cardproperties.interfaces.StyleImage;
import net.coobird.thumbnailator.Thumbnails;
import renderers.Displayable;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static constants.FilePaths.ANIME_ATTRIBUTES;
import static constants.FilePaths.ATTRIBUTES;
import static utils.ImageUtils.loadImage;
import static utils.ImageUtils.toCompatibleImage;

public enum Attribute implements Displayable, StyleImage {
    
    LIGHT("Light"),
    DARK("Dark"),
    FIRE("Fire"),
    WATER("Water"),
    WIND("Wind"),
    EARTH("Earth"),
    DIVINE("Divine"),
    DARK_DIVINE("Dark Divine"),
    SPELL("Spell"),
    TRAP("Trap"),
    TRAP_SPELL("Trap Spell"),
    LAUGH("Laugh");

    private  static final int ATTRIBUTE_SCALE = 108;
    private final String attribute;
    private final BufferedImage image;
    private BufferedImage animeImage; //TODO change back to final once converted to correct image size

    Attribute(String attribute) {
        this.attribute = attribute;
        image = toCompatibleImage(loadImage(ATTRIBUTES + attribute + ".png"));
        BufferedImage animeImage = toCompatibleImage(loadImage(ANIME_ATTRIBUTES + attribute  + ".png"));
        try {
            this.animeImage = Thumbnails.of(animeImage)
                    .scale((float) ATTRIBUTE_SCALE/animeImage.getWidth())
                    .asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAttribute(){
        return attribute;
    }

    @Override
    public BufferedImage getImage(){
        return image;
    }

    @Override
    public BufferedImage getAnimeImage(){
        return animeImage;
    }

    @Override
    public String toString() {
        return attribute;
    }

    @Override
    public String getDisplayString() {
        return attribute;
    }
}
