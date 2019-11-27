package newclasses.imageUtils;

import constants.FilePaths;
import newclasses.description.DescriptionPanel;
import newclasses.timeUtils.StopWatch;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class pixelPanel extends JFrame {

    private JTextArea textArea;
    private JPanel descriptionPreview;

    private JScrollPane scrCardDescription;

    public pixelPanel() {
        super("Description Panel");
        add(getSplMain());
        setSize(876, 528);
        setSize(new Dimension(870, 738));
        setVisible(true);
    }

    private JSplitPane getSplMain() {
        JSplitPane splMain = new JSplitPane();
        splMain.setTopComponent(getScrCardDescription());
        splMain.setBottomComponent(getDescriptionPreview());
        splMain.setEnabled(true);
        splMain.setBorder(BasicBorders.getSplitPaneDividerBorder());
        splMain.setOrientation(JSplitPane.VERTICAL_SPLIT);
        return splMain;
    }

    private JScrollPane getScrCardDescription() {
        scrCardDescription = new JScrollPane(getTextArea());
        scrCardDescription.setMinimumSize(new Dimension(0, 176));
        return scrCardDescription;
    }

    private JTextArea getTextArea() {
        if (textArea == null) {
            textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Tahoma", 0, 11));
            textArea.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    getDescriptionPreview().repaint();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    getDescriptionPreview().repaint();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    getDescriptionPreview().repaint();
                }
            });
        }
        return textArea;
    }

    private JPanel getDescriptionPreview() {
        if (descriptionPreview == null) {
            descriptionPreview = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    Graphics2D g2d = (Graphics2D) g;
                    ImageUtils.setRenderingHints(g2d);

                    int[][] pixels = PixelUtils.mergePixels(PixelUtils.pixels1, PixelUtils.pixels2);

                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();

                    BufferedImage img = PixelUtils.convertPixelsToImage(pixels);

                    stopWatch.printElapsedTime("img generation");

                    g2d.drawImage(img, 0, 0, null);

                }
            };
        }
        return descriptionPreview;
    }

    private void saveImage(BufferedImage img) {
        try {
            ImageIO.write(img, "PNG", new File(FilePaths.TEMP_DIR + "text.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //<editor-fold defaultState="collapsed" desc=" Look and feel setting code (Windows) ">
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DescriptionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        pixelPanel frame = new pixelPanel();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
