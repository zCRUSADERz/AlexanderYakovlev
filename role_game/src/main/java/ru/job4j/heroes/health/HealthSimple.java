package ru.job4j.heroes.health;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDieObservable;

public class HealthSimple implements HeroHealth {
    private final static int MAX_HEALTH = 100;
    private final HeroDieObservable dieObservable;
    private final Hero heroOwner;
    private int health = MAX_HEALTH;
    private final Logger logger = Logger.getLogger(HealthSimple.class);

    public HealthSimple(HeroDieObservable dieObservable, Hero heroOwner) {
        this.dieObservable = dieObservable;
        this.heroOwner = heroOwner;
    }

    @Override
    public void takeDamage(int damage) {
        final int startHP = this.health;
        this.health -= damage;
        this.logger.info(
                String.format(
                        "%s, HP: %d. Damage: %d, resultHP: %d.",
                        this.heroOwner, startHP, damage, this.health
                )
        );
        if (this.health <= 0) {
            this.logger.info(this.heroOwner + " die.");
            this.dieObservable.heroDied(this.heroOwner);
        }
    }
}
