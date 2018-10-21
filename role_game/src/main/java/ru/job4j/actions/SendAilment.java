package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.heroes.attack.AttackStrengthModifier;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.squad.SquadsMapper;

/**
 * Send ailment.
 * Наслать недуг на противника. Эффект - уменьшение силы атаки цели.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class SendAilment implements HeroAction {
    private final SquadsMapper squadsMapper;
    private final AttackStrengthModifier attackModifier;
    private final AttackStrengthModifiers modifiers;
    private final Logger logger = Logger.getLogger(SendAilment.class);

    public SendAilment(SquadsMapper squadsMapper, AttackStrengthModifier attackModifier,
                       AttackStrengthModifiers modifiers) {
        this.squadsMapper = squadsMapper;
        this.attackModifier = attackModifier;
        this.modifiers = modifiers;
    }

    /**
     * Наслать недуг на противника.
     * @param heroActor герой выполняющий действие.
     */
    @Override
    public void act(Hero heroActor) {
        final Hero enemyHero = this.squadsMapper.enemySquadFor(heroActor).randomHero();
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
