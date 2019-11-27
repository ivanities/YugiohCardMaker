package cardproperties;

import cardproperties.interfaces.PendulumStyleImage;

import java.awt.image.BufferedImage;
import java.util.EnumSet;

import static constants.FilePaths.*;
import static utils.ImageUtils.createCompatibleImage;

public enum Frame implements PendulumStyleImage {

    NORMAL("Normal"),
    EFFECT("Effect"),
    FUSION("Fusion"),
    RITUAL("Ritual"),
    SPELL("Spell"),
    TRAP("Trap"),
    SYNCHRO("Synchro"),
    DARK_SYNCHRO("Dark Synchro"),
    EXCEED("Xyz"),
    LINK("Link"),
    TOKEN("Token"),
    LEGENDARY_DRAGON("Legendary Dragon"),
    EGYPTIAN_GOD_RED("Egyptian God Red"),
    EGYPTIAN_GOD_BLUE("Egyptian God Blue"),
    EGYPTIAN_GOD_YELLOW("Egyptian God Yellow"),
    SACRED_BEAST_RED("Sacred Beast Red"),
    SACRED_BEAST_BLUE("Sacred Beast Blue"),
    SACRED_BEAST_YELLOW("Sacred Beast Yellow"),
    WICKED_GOD("Wicked God");

    private final String frame;
    private final BufferedImage image;
    private final BufferedImage animeImage;
    private final BufferedImage pendulumImage;
    private final BufferedImage animePendulumImage;

    public static final EnumSet<Frame> UNDISPLAYABLE_FRAMES = EnumSet.of(
            EFFECT, EGYPTIAN_GOD_RED,
            EGYPTIAN_GOD_BLUE, EGYPTIAN_GOD_YELLOW,
            SACRED_BEAST_RED, SACRED_BEAST_BLUE,
            SACRED_BEAST_YELLOW, WICKED_GOD
    );

    public static final EnumSet<Frame> EXTRA_DECK_FRAMES = EnumSet.of(FUSION, SYNCHRO, DARK_SYNCHRO, EXCEED, LINK);

    public static final EnumSet<Frame> DARK_FRAMES = EnumSet.of(EXCEED, DARK_SYNCHRO, WICKED_GOD);

    Frame(String frame) {
        this.frame = frame;
        image = createCompatibleImage(TEMPLATES, this);
        animeImage = createCompatibleImage(ANIME_TEMPLATES, this);
        pendulumImage = createCompatibleImage(PENDULUM_TEMPLATES, this);
        animePendulumImage = createCompatibleImage(ANIME_PENDULUM_TEMPLATES, this);
    }

    public static Frame getFrame(String frame) {
        for(Frame cardFrame : values()){
            if(frame.equals(cardFrame.getFrame())){
                return cardFrame;
            }
        }
        return null;
    }

    public static EnumSet<Frame> getUndisplayableBases() {
        return UNDISPLAYABLE_FRAMES;
    }

    public String getFrame() {
        return frame;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public BufferedImage getAnimeImage() {
        return animeImage;
    }

    @Override
    public BufferedImage getPendulumImage() {
        return pendulumImage;
    }

    @Override
    public BufferedImage getAnimePendulumImage() {
        return animePendulumImage;
    }

    @Override
    public String toString() {
        return frame;
    }

    @Override
    public String getDisplayString() {
        return frame;
    }
}