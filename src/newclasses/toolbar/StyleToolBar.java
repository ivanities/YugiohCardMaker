package newclasses.toolbar;

import factories.IconFactory;
import factories.JButtonFactory;
import newclasses.CardController;

import javax.swing.*;

public class StyleToolBar extends JToolBar {

    private CardController controller;

    private JButton btnGenerate;
    private JButton btnAnimeStyle;
    private JButton btnPendulumStyle;
    private JButton btnBulletPoint;
    private JButton btnAdditionalDetails;

    public StyleToolBar(CardController controller) {
        this.controller = controller;

        add(getBtnGenerate());
        add(getBtnAnimeStyle());
        add(getBtnPendulumStyle());
        add(getBtnBulletPoint());
        add(getBtnAdditionalDetails());

        setOrientation(JToolBar.VERTICAL);
        setFloatable(false);
    }

    private JButton getBtnGenerate() {
        if (btnGenerate == null) {
            btnGenerate = JButtonFactory.createToolBarButton(IconFactory.REFRESH)
                    .addActionListener(controller::refreshButtonAction)
                    .setToolTipText("Refresh")
                    .build();
        }
        return btnGenerate;
    }

    private JButton getBtnAnimeStyle() {
        if (btnAnimeStyle == null) {
            btnAnimeStyle = JButtonFactory.createToolBarButton(IconFactory.ANIME_STYLE)
                    .addActionListener(controller::animeButtonAction)
                    .setToolTipText("4Kids Style")
                    .build();
        }
        return btnAnimeStyle;
    }

    private JButton getBtnPendulumStyle() {
        if (btnPendulumStyle == null) {
            btnPendulumStyle = JButtonFactory.createToolBarButton(IconFactory.PENDULUM_MODE)
                    .addActionListener(controller::pendulumButtonAction)
                    .setToolTipText("Pendulum Style")
                    .build();
        }
        return btnPendulumStyle;
    }

    private JButton getBtnBulletPoint() {
        if (btnBulletPoint == null) {
            btnBulletPoint = JButtonFactory.createToolBarButton(IconFactory.BULLET_POINT)
                    .addActionListener(controller::refreshButtonAction)
                    .setToolTipText("Bullet Point Mode")
                    .build();
        }
        return btnBulletPoint;
    }

    private JButton getBtnAdditionalDetails() {
        if (btnAdditionalDetails == null) {
            btnAdditionalDetails = JButtonFactory.createToolBarButton(IconFactory.ADDITIONAL_DETAILS)
                    .addActionListener(controller::additionalDetailsButtonAction)
                    .setToolTipText("Additional Details")
                    .build();
        }
        return btnAdditionalDetails;
    }
}
