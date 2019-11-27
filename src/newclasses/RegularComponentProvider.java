package newclasses;

import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;
import newclasses.cardcomponents.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class RegularComponentProvider implements CardComponentProvider {

    RegularComponentProvider() {
        //TODO JF add this back in once providers are stored correctly
//        DescriptionFont.initializeFontMap();
    }

    @Override
    public List<CardComponent<Card>> getCommonComponents() {
        return Arrays.asList(
                new PictureComponent(),
                new FrameComponent(),
                new NameComponent(),
                new DescriptionComponent(),
                new AttributeComponent()
        );
    }

    @Override
    public List<CardComponent<MonsterCard>> getMonsterComponents() {
        return Arrays.asList(
                new LevelComponent(),
                new AttackDefenseComponent(),
                new MonsterTypeComponent()
        );
    }

    @Override
    public List<CardComponent<PendulumMonsterCard>> getPendulumComponents() {
        return Arrays.asList(
                new PendulumScalesComponent(),
                new PendulumDescriptionComponent()
        );
    }

    @Override
    public List<CardComponent<SpellTrapCard>> getSpellTrapComponents() {
        return Collections.singletonList(
                new SpellTrapTypeComponent()
        );
    }

    @Override
    public List<CardComponent<Card>> getAdditionalDetailsComponents() {
        return Arrays.asList(
                new CardIDComponent(),
                new SerialCodeComponent(),
                new CreatorComponent(),
                new SerialTagComponent(),
                new EditionComponent()
        );
    }
}
