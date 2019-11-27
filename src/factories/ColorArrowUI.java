package factories;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;

import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ColorArrowUI extends WindowsComboBoxUI {
    
    private static final Color LIGHT_YELLOW = new Color(255, 255, 200);
    private static final Color YELLOW = new Color(240, 220, 0);
    private static final Color DARK_YELLOW = new Color(200, 150, 0);
    private static final Color LIGHT_GRAY = new Color(230, 230, 230);
    private static final Color GRAY = new Color(200, 200, 200);

    public static ComboBoxUI createUI(JComponent c) {
        return new ColorArrowUI();
    }

    @Override protected JButton createArrowButton() {
//        return new BasicArrowButton(
//            BasicArrowButton.SOUTH,
//            YELLOW, DARK_YELLOW,
//            DARK_YELLOW, LIGHT_YELLOW);
        
//        JButton btn = new BasicArrowButton(
//            BasicArrowButton.SOUTH,
//            LIGHT_GRAY, GRAY,
//            GRAY, LIGHT_GRAY);
//        JButton btn = new JButton();
//        btn.setIcon(IconFactory.SEARCH_CARD);
//        btn.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
//        btn.setContentAreaFilled(false);
//        return btn;
        return new SearchArrowButton();
    }

    public class SearchArrowButton extends JButton implements SwingConstants
    {
        private Color shadow;
        private Color darkShadow;
        private Color highlight;

        public SearchArrowButton(Color background, Color shadow,
                                Color darkShadow, Color highlight) {
            super();
            setRequestFocusEnabled(false);
            setBackground(background);
            this.shadow = shadow;
            this.darkShadow = darkShadow;
            this.highlight = highlight;
        }

        public SearchArrowButton() {
            this(UIManager.getColor("control"), UIManager.getColor("controlShadow"),
                    UIManager.getColor("controlDkShadow"), UIManager.getColor("controlLtHighlight"));
        }

        public void paint(Graphics g) {
            Color origColor;
            boolean isPressed, isEnabled;
            int w, h, size;

            w = getSize().width;
            h = getSize().height;
            origColor = g.getColor();
            isPressed = getModel().isPressed();
            isEnabled = isEnabled();

            g.setColor(getBackground());
            g.fillRect(1, 1, w-2, h-2);

            /// Draw the proper Border
            if (getBorder() != null && !(getBorder() instanceof UIResource)) {
                paintBorder(g);
            }
            else if (isPressed) {
                g.setColor(getBackground());
                g.drawRect(0, 0, w-1, h-1);
            }
            else {
                // Using the background color set above
                g.drawLine(0, 0, 0, h-1);
                g.drawLine(1, 0, w-2, 0);

                g.setColor(getBackground());    // inner 3D border
                g.drawLine(1, 1, 1, h-3);
                g.drawLine(2, 1, w-3, 1);

                g.setColor(getBackground());       // inner 3D border
                g.drawLine(1, h-2, w-2, h-2);
                g.drawLine(w-2, 1, w-2, h-3);

                g.setColor(darkShadow);     // black drop shadow  __|
                g.drawLine(0, h-1, w-1, h-1);
                g.drawLine(w-1, h-1, w-1, 0);
            }

            // If there's no room to draw arrow, bail
            if (h < 5 || w < 5)      {
                g.setColor(origColor);
                return;
            }

            if (isPressed) {
                g.translate(1, 1);
            }

            // Draw the arrow
            size = Math.min((h - 4) / 3, (w - 4) / 3);
            size = Math.max(size, 2);
            paintTriangle(g, (w - size) / 2, (h - size) / 2,
                    size, isEnabled);

            // Reset the Graphics back to it's original settings
            if (isPressed) {
                g.translate(-1, -1);
            }
            g.setColor(origColor);

        }

        public void paintTriangle(Graphics g, int x, int y, int size, boolean isEnabled) {
            Color oldColor = g.getColor();
            int mid, i, j;

            j = 0;
            size = Math.max(size, 2);
            mid = (size / 2) - 1;

//            g.translate(x, y);
            g.drawImage(IconFactory.SEARCH_CARD.getImage(), 0, 0, getSize().width, getSize().height, null);
//            if (isEnabled)
//                g.setColor(darkShadow);
//            else
//                g.setColor(shadow);
//
//            if (!isEnabled)  {
//                g.translate(1, 1);
//                g.setColor(highlight);
//                for(i = size-1; i >= 0; i--)   {
//                    g.drawLine(mid-i, j, mid+i, j);
//                    j++;
//                }
//                g.translate(-1, -1);
//                g.setColor(shadow);
//            }
//
//            j = 0;
//            for (i = size - 1; i >= 0; i--) {
//                g.drawLine(mid - i, j, mid + i, j);
//                j++;
//            }
//            g.translate(-x, -y);
//            g.setColor(oldColor);
        }

    }
}
