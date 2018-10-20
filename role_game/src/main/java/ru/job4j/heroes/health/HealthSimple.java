package ru.job4j.heroes.health;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;

public class HealthSimple implements HeroHealth {
    private final static int MAX_HEALTH = 100;
    private final static int MIN_HEALTH = 0;
    private final HeroDiedObservable dieObservable;
    private int health = MAX_HEALTH;
    private final Logger logger = Logger.getLogger(HealthSimple.class);

    public HealthSimple(HeroDiedObservable dieObservable) {
        this.dieObservable = dieObservable;
    }

    @Override
    public void takeDamage(Hero heroOwner, int damage) {
        final int startHP = this.health;
        this.health -= damage;
        this.logger.info(
                String.format(
                        "%s, HP: %d. Damage: %d, resultHP: %d.",
                        heroOwner, startHP, damage, this.health
                )
        );
        if (this.health <= MIN_HEALTH) {
            this.logger.info(
                    String.format(
                            "%s die. Min health: %d, hero health: %d.",
                            heroOwner, MIN_HEALTH, this.health
                    )
            );
            this.dieObservable.heroDied(heroOwner);
        }
    }
}
