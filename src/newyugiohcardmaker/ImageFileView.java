package newyugiohcardmaker;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.io.File;

public class ImageFileView extends FileView {

    private static final ImageIcon jpgIcon = Utils.createImageIcon("images/jpgIcon.gif");
    private static final ImageIcon gifIcon = Utils.createImageIcon("images/gifIcon.gif");
    private static final ImageIcon tiffIcon = Utils.createImageIcon("images/tiffIcon.gif");
    private static final ImageIcon pngIcon = Utils.createImageIcon("images/pngIcon.png");

    private static final String JPEG_IMAGE = "JPEG Image";
    private static final String GIF_IMAGE = "GIF Image";
    private static final String TIFF_IMAGE = "TIFF Image";
    private static final String PNG_IMAGE = "PNG Image";

    @Override
    public String getTypeDescription(File f) {
        String extension = Utils.getExtension(f);

        switch (extension) {
            case Utils.JPEG:
            case Utils.JPG:
                return JPEG_IMAGE;
            case Utils.GIF:
                return GIF_IMAGE;
            case Utils.TIFF:
            case Utils.TIF:
                return TIFF_IMAGE;
            case Utils.PNG:
                return PNG_IMAGE;
            default:
                return null;
        }
    }

    @Override
    public Icon getIcon(File f) {
        String extension = Utils.getExtension(f);
        Icon icon = null;

        if (extension.equals(Utils.PNG)) {
            icon = Utils.createImageIcon(f.getName());
        }

//        if (extension != null) {
//            if (extension.equals(utils.JPEG) ||
//                    extension.equals(utils.JPG)) {
//                icon = jpgIcon;
//            }
//            else if (extension.equals(utils.GIF)) {
//                icon = gifIcon;
//            }
//            else if (extension.equals(utils.TIFF) ||
//                    extension.equals(utils.TIF)) {
//                icon = tiffIcon;
//            }
//            else if (extension.equals(utils.PNG)) {
//                icon = pngIcon;
//            }
//        }
        return icon;
    }
}
