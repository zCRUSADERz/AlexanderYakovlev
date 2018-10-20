package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.SquadHeroes;
import ru.job4j.heroes.Hero;
import ru.job4j.heroes.attack.AttackStrengthModifier;
import ru.job4j.heroes.attack.AttackStrengthModifiers;

public class SendAilment implements HeroAction {
    private final SquadHeroes enemySquad;
    private final AttackStrengthModifier attackModifier;
    private final AttackStrengthModifiers modifiers;
    private final Logger logger = Logger.getLogger(SendAilment.class);

    public SendAilment(SquadHeroes enemySquad,
                       AttackStrengthModifier attackModifier,
                       AttackStrengthModifiers modifiers) {
        this.enemySquad = enemySquad;
        this.attackModifier = attackModifier;
        this.modifiers = modifiers;
    }

    @Override
    public void act(Hero heroActor) {
        final Hero enemyHero = this.enemySquad.randomHero();
        this.modifiers.add(enemyHero, this.attackModifier);
        this.logger.info(String.format(
                "%s sent the disease to the %s. (%s)",
                heroActor, enemyHero, this.attackModifier
        ));
    }

    @Override
    public String toString() {
        return "Наслать недуг "
                + "(уменьшение силы урона персонажа противника на 50% на один ход)";
    }
}
