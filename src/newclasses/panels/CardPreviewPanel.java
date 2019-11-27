package newclasses.panels;

import net.miginfocom.swing.MigLayout;
import newclasses.ColorConstants;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CardPreviewPanel extends JPanel {

    private static final double SCALE = 0.5;

    private BufferedImage cardImage = ImageUtils.toCompatibleImage(ImageIO.read(new File("resources\\read_only\\card_features\\backings\\default_backing.png")));
//    private BufferedImage cardImage = ImageUtils.toCompatibleImage(ImageIO.read(new File("src\\tmp\\dark_magician_girl_alt_1.png")));

    private JLabel lblCardPreview;

    private static final float cardMinWidth = 290; // width = 207, height = 301
    private float cardWidth = 290;
    private float scale = 1;
    private static final int ORIGINAL_CARD_WIDTH = 826;
    private static final int ORIGINAL_CARD_HEIGHT = 1204;
    private static final float ASPECT_RATIO = (float) ORIGINAL_CARD_WIDTH/ORIGINAL_CARD_HEIGHT;

    public CardPreviewPanel() throws IOException {
        setLayout(new MigLayout("align center, filly"));
        setBackground(ColorConstants.LIGHT_PURPLE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.GRAY));
        add(getLblCardPreview(), "grow");
    }

    public JLabel getLblCardPreview() {
        if (lblCardPreview == null) {
            lblCardPreview = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    scale = (float) lblCardPreview.getHeight() / lblCardPreview.getMinimumSize().height;
                    float newCardWidth = cardMinWidth * scale;

                    if (cardWidth != newCardWidth) {
                        cardWidth = newCardWidth;
                        lblCardPreview.setSize((int) cardWidth, lblCardPreview.getHeight());
                        lblCardPreview.setPreferredSize(new Dimension((int) cardWidth, lblCardPreview.getHeight()));
                    }

                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    if (cardWidth >= 413) {
                        ImageUtils.drawImage(g2d, cardImage, (int) cardWidth, lblCardPreview.getHeight());
//                        g2d.drawImage(cardImage, 0, 0, (int) (lblCardPreview.getHeight() * ASPECT_RATIO), lblCardPreview.getHeight(), null);
                    }
                    else {
                        double w = cardWidth * 4;
                        double h = lblCardPreview.getHeight() * 4;

                        BufferedImage dimg = cardImage;

                        for (int i = 0; i < 2; i++) {
                            w = w * SCALE;
                            h = h * SCALE;
                            dimg = ImageUtils.resize(dimg, (int) w, (int) h);
                        }

                        g2d.drawImage(dimg, 0, 0, this);
                    }
                }
            };
            lblCardPreview.setMinimumSize(new Dimension(290, 425));
            lblCardPreview.setPreferredSize(new Dimension(290, 425));
        }
        return lblCardPreview;
    }

    public void setCardImage(BufferedImage cardImage) {
        this.cardImage = cardImage;
    }
}
