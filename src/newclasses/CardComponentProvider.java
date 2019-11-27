package newclasses;

import newclasses.card.Card;
import newclasses.card.MonsterCard;
import newclasses.card.PendulumMonsterCard;
import newclasses.card.SpellTrapCard;
import newclasses.cardcomponents.CardComponent;

import java.util.List;

interface CardComponentProvider {

    List<CardComponent<Card>> getCommonComponents();

    List<CardComponent<MonsterCard>> getMonsterComponents();

    List<CardComponent<PendulumMonsterCard>> getPendulumComponents();

    List<CardComponent<SpellTrapCard>> getSpellTrapComponents();

    List<CardComponent<Card>> getAdditionalDetailsComponents();
}
