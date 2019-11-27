package cardproperties.interfaces;

import renderers.Displayable;

import java.awt.image.BufferedImage;

public interface StyleImage extends Displayable{

    BufferedImage getImage();

    BufferedImage getAnimeImage();
}
