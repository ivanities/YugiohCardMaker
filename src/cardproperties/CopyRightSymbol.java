package cardproperties;

import java.awt.image.BufferedImage;

import static constants.FilePaths.CARD_FEATURES;
import static utils.ImageUtils.loadImage;
import static utils.ImageUtils.toCompatibleImage;

public enum CopyRightSymbol {

    BLACK("copy_right_sign"),
    WHITE("copy_right_sign_white");

    private String fileName;
    private BufferedImage hologram;

    CopyRightSymbol(String fileName) {
        this.fileName = fileName;
        hologram = toCompatibleImage(loadImage(CARD_FEATURES + fileName + ".png"));
    }

    public String getFileName() {
        return fileName;
    }

    public BufferedImage getImage() {
        return hologram;
    }
}
