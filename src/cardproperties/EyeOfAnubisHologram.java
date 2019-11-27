package cardproperties;

import java.awt.image.BufferedImage;

import static constants.FilePaths.EYE_OF_ANUBIS;
import static utils.ImageUtils.loadImage;
import static utils.ImageUtils.toCompatibleImage;

public enum EyeOfAnubisHologram {

    GOLD("eye_of_anubis_hologram_gold"),
    SILVER("eye_of_anubis_hologram_silver");

    private String fileName;
    private BufferedImage hologram;

    EyeOfAnubisHologram(String fileName) {
        this.fileName = fileName;
        hologram = toCompatibleImage(loadImage(EYE_OF_ANUBIS + fileName + ".png"));
    }

    public String getFileName() {
        return fileName;
    }

    public BufferedImage getHologram() {
        return hologram;
    }
}
