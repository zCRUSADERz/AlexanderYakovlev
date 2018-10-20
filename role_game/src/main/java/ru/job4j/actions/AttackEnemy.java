package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.SquadHeroes;
import ru.job4j.heroes.attack.AttackStrengthModifier;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;

import java.util.Collection;

public class AttackEnemy implements HeroAction {
    private final String actionName;
    private final int damage;
    private final AttackStrengthModifiers modifiers;
    private final SquadHeroes enemySquad;
    private final HealthHeroes healthHeroes;
    private final Logger logger = Logger.getLogger(AttackEnemy.class);

    public AttackEnemy(String actionName, int damage, AttackStrengthModifiers modifiers,
                       SquadHeroes enemySquad, HealthHeroes healthHeroes) {
        this.actionName = actionName;
        this.damage = damage;
        this.modifiers = modifiers;
        this.enemySquad = enemySquad;
        this.healthHeroes = healthHeroes;
    }

    @Override
    public void act(Hero heroActor) {
        final Hero enemyHero = this.enemySquad.randomHero();
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
