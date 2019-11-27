package newclasses;

import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;
import newclasses.cardcomponents.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class AnimeComponentProvider implements CardComponentProvider {

    @Override
    public List<CardComponent<Card>> getCommonComponents() {
        return Arrays.asList(
                new AnimePictureComponent(),
                new AnimeFrameComponent(),
                new AnimeAttributeComponent()
        );
    }

    @Override
    public List<CardComponent<MonsterCard>> getMonsterComponents() {
        return Arrays.asList(
                new AnimeLevelComponent(),
                new AnimeAttackDefenseComponent()
        );
    }

    @Override
    public List<CardComponent<PendulumMonsterCard>> getPendulumComponents() {
        return Collections.singletonList(
                new AnimePendulumScalesComponent()
        );
    }

    @Override
    public List<CardComponent<SpellTrapCard>> getSpellTrapComponents() {
        return Collections.emptyList();
    }

    @Override
    public List<CardComponent<Card>> getAdditionalDetailsComponents() {
        return Collections.emptyList();
    }
}
