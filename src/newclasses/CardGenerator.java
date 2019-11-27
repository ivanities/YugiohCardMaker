package newclasses;

import newclasses.card.Card;
import newclasses.cardcomponents.CardComponent;
import newclasses.description.font.DescriptionFont;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

class CardGenerator {

    private Graphics2D g;
    private BufferedImage finalImage;
    private List<CardComponent> components;

    CardGenerator() {
        finalImage = ImageUtils.createNewCompatibleImage(826, 1204, Transparency.TRANSLUCENT);
        g = finalImage.createGraphics();
//        g.setBackground(Color.WHITE);
        ImageUtils.setRenderingHints(g);
        DescriptionFont.initializeNormalFontMap();
        DescriptionFont.initializeFontMap();
    }

    void setComponents(List<CardComponent> components) {
        this.components = components;
    }

    @SuppressWarnings("unchecked")
    <C extends Card> BufferedImage generate(C card) throws IOException {
        // TODO find out why picture quality is worse
//        g.clearRect(0, 0, 826, 1204);
        finalImage = ImageUtils.createNewCompatibleImage(826, 1204, Transparency.TRANSLUCENT);
        g = finalImage.createGraphics();
        ImageUtils.setRenderingHints(g);

        for (CardComponent component : components) {
            component.drawImage(g, card);
        }

        return finalImage;
    }
}
