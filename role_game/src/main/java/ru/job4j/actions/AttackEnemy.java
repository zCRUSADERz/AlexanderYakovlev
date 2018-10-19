package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.SquadHeroes;
import ru.job4j.heroes.health.HealthHeroes;

public class AttackEnemy implements HeroAction {
    private final String actionName;
    private final int damage;
    private final SquadHeroes enemySquad;
    private final HealthHeroes healthHeroes;
    private final Logger logger = Logger.getLogger(AttackEnemy.class);

    public AttackEnemy(String actionName, int damage,
                       SquadHeroes enemySquad, HealthHeroes healthHeroes) {
        this.actionName = actionName;
        this.damage = damage;
        this.enemySquad = enemySquad;
        this.healthHeroes = healthHeroes;
    }

    @Override
    public void act() {
        final Hero enemyHero = this.enemySquad.randomHero();
        this.logger.info(
                String.format(
                        "%s%s. Damage: %d HP.",
                        this.actionName, enemyHero, this.damage
                )
        );
        this.healthHeroes.attackHero(enemyHero, this.damage);
    }

    @Override
    public String toString() {
        return String.format(
                "%s (damage: %d HP)",
                this.actionName, this.damage
        );
    }
}
