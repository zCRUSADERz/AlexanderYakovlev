package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.actions.actiontarget.RandomTarget;
import ru.job4j.heroes.Hero;
import ru.job4j.heroes.attack.AttackStrengthModifier;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;

import java.util.Collection;

/**
 * Attack enemy.
 * Действие атаковать противника.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class AttackEnemy implements HeroAction {
    private final String actionName;
    private final int damage;
    private final RandomTarget randomTarget;
    private final AttackStrengthModifiers modifiers;
    private final HealthHeroes healthHeroes;
    private final Logger logger = Logger.getLogger(AttackEnemy.class);

    public AttackEnemy(String actionName, int damage,
                       RandomTarget randomTarget, AttackStrengthModifiers modifiers,
                       HealthHeroes healthHeroes) {
        this.actionName = actionName;
        this.damage = damage;
        this.randomTarget = randomTarget;
        this.modifiers = modifiers;
        this.healthHeroes = healthHeroes;
    }

    /**
     * Выполнить действие. Атаковать противника.
     * @param heroActor герой выполняющий действие.
     */
    @Override
    public void act(Hero heroActor) {
        final Hero enemyHero = this.randomTarget.randomTargetFor(heroActor);
        final Collection<AttackStrengthModifier> attackModifiers
                = this.modifiers.modifiersFor(heroActor);
        int resultDamage = this.damage;
        for (AttackStrengthModifier modifier : attackModifiers) {
            resultDamage = modifier.resultDamage(resultDamage);
        }
        this.logger.info(
                String.format(
                        "%s, %s%s. Damage: %d HP.(Modifiers: %s)",
                        heroActor,
                        this.actionName, enemyHero,
                        resultDamage,
                        attackModifiers
                )
        );
        this.healthHeroes.attackHero(enemyHero, resultDamage);
    }

    @Override
    public String toString() {
        return String.format(
                "%s (damage: %d HP)",
                this.actionName, this.damage
        );
    }
}
