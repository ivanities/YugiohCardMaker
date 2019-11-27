package cardproperties;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Coordinates {

    public static final Map<String, Point> COORDINATES;
    static
    {
        COORDINATES = new HashMap<>();
        COORDINATES.put("Frame", new Point(0, 0));
        COORDINATES.put("Attribute", new Point(691, 55));
        COORDINATES.put("Picture", new Point(98, 219));
        COORDINATES.put("Name", new Point(67, 66));
        COORDINATES.put("CardID", new Point(740, 867));
        COORDINATES.put("SerialText", new Point(38, 1150));
        COORDINATES.put("Level", new Point(685, 147));
        COORDINATES.put("Level13", new Point(710, 147));
        COORDINATES.put("Rank", new Point(91, 147));
        COORDINATES.put("eyeOfAnubisHologram", new Point(758, 1135));
        COORDINATES.put("serialNumber", new Point(38, 1150));
        COORDINATES.put("serialText", new Point(38, 1151));
        COORDINATES.put("creator", new Point(746, 1150));
        COORDINATES.put("monsterDescription", new Point(65, 937));
        COORDINATES.put("spellTrapDescription", new Point(65, 911));
        COORDINATES.put("monsterType", new Point(67, 910));
        COORDINATES.put("spellTrapType", new Point(683, 154));
        COORDINATES.put("Edition", new Point(164, 1149));
    }

    public static final Map<String, Point> ANIME_COORDINATES;
    static
    {
        ANIME_COORDINATES = new HashMap<>();
        ANIME_COORDINATES.put("Frame", new Point(0, 0));
        ANIME_COORDINATES.put("AttributeMonster", new Point(663, 913));
        ANIME_COORDINATES.put("AttributeMonster12", new Point(688, 913));
        ANIME_COORDINATES.put("AttributeMonster13", new Point(697, 913));
        ANIME_COORDINATES.put("AttributeSpell", new Point(358, 997));
        ANIME_COORDINATES.put("Picture", new Point(-8, 25));
        ANIME_COORDINATES.put("LevelOdd", new Point(384, 934));
        ANIME_COORDINATES.put("LevelEven", new Point(352, 934));
        ANIME_COORDINATES.put("Level9", new Point(128, 934));
        ANIME_COORDINATES.put("Level10", new Point(87, 934));
        ANIME_COORDINATES.put("Level11", new Point(31, 934));
        ANIME_COORDINATES.put("Level12", new Point(20, 934));
        ANIME_COORDINATES.put("Rank", new Point(61, 934));
        ANIME_COORDINATES.put("AtkOdd", new Point(188, 1052));
        ANIME_COORDINATES.put("AtkEven", new Point(153, 1052));
        ANIME_COORDINATES.put("DefOdd", new Point(563, 1052));
        ANIME_COORDINATES.put("DefEven", new Point(530, 1052));
    }

    public static final Map<String, Point> PENDULUM_COORDINATES;
    static
    {
        PENDULUM_COORDINATES = new HashMap<>(COORDINATES);
        PENDULUM_COORDINATES.put("Picture", new Point(55, 213));
        PENDULUM_COORDINATES.put("CardID", new Point(69, 1105));
        PENDULUM_COORDINATES.put("PendulumScaleBlue", new Point(58, 784));
        PENDULUM_COORDINATES.put("PendulumScaleRed", new Point(717, 784));
        PENDULUM_COORDINATES.put("PendulumScaleBlueValueOdd", new Point(71, 827));
        PENDULUM_COORDINATES.put("PendulumScaleBlueValueEven", new Point(57, 827));
        PENDULUM_COORDINATES.put("PendulumScaleRedValueOdd", new Point(727, 827));
        PENDULUM_COORDINATES.put("PendulumScaleRedValueEven", new Point(713, 827));

        PENDULUM_COORDINATES.put("PendulumScaleAnime", new Point(23, 724));
        PENDULUM_COORDINATES.put("PendulumScaleAnimeBlueValueOdd", new Point(44, 789));
        PENDULUM_COORDINATES.put("PendulumScaleAnimeBlueValueEven", new Point(28, 789));
        PENDULUM_COORDINATES.put("PendulumScaleAnimeRedValueOdd", new Point(751, 789));
        PENDULUM_COORDINATES.put("PendulumScaleAnimeRedValueEven", new Point(735, 789));

        PENDULUM_COORDINATES.put("PendulumDescription", new Point(126, 757));
    }

    public static Map<String, Point> getCoordinates(boolean isAnime, boolean isPendulum) {
        if (!isAnime && !isPendulum) {
            return COORDINATES;
        }
        else if (isPendulum && !isAnime) {
            return PENDULUM_COORDINATES;
        }
        return ANIME_COORDINATES;
    }

    public static int getPendulumCoordinateX(String key) {
        return getCoordinateX(PENDULUM_COORDINATES, key);
    }

    public static int getPendulumCoordinateY(String key) {
        return getCoordinateY(PENDULUM_COORDINATES, key);
    }

    public static int getAnimeCoordinateX(String key) {
        return getCoordinateX(ANIME_COORDINATES, key);
    }

    public static int getAnimeCoordinateY(String key) {
        return getCoordinateY(ANIME_COORDINATES, key);
    }

    public static int getRegularCoordinateX(String key) {
        return getCoordinateX(COORDINATES, key);
    }

    public static int getRegularCoordinateY(String key) {
        return getCoordinateY(COORDINATES, key);
    }

    private static int getCoordinateX(Map<String, Point> coordinates, String key) {
        return (int) coordinates.get(key).getX();
    }

    private static int getCoordinateY(Map<String, Point> coordinates, String key) {
        return (int) coordinates.get(key).getY();
    }
}
