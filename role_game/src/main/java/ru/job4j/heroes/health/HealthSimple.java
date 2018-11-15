package ru.job4j.heroes.health;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.observers.Observables;

/**
 * Hero health.
 * Здоровье героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class HealthSimple implements HeroHealth {
    private final static int MAX_HEALTH = 100;
    private final int minHealth;
    private final Observables<HeroDiedObserver> diedObservables;
    private final HealthLogger logger;
    private int health;

    public HealthSimple(Observables<HeroDiedObserver> dieObservable) {
        this(
                MAX_HEALTH,
                0,
                dieObservable,
                new HealthLogger()
        );
    }

    public HealthSimple(int health,
                        int minHealth,
                        Observables<HeroDiedObserver> diedObservables,
                        HealthLogger logger) {
        this.health = health;
        this.minHealth = minHealth;
        this.diedObservables = diedObservables;
        this.logger = logger;
    }

    /**
     * Получить урон.
     * Если зоровье снизится до минимальной отметки или более,
     * то герой будет убит и будут оповещены наблюдатели за данным событием.
     * @param damage полученный урон.
     * @throws IllegalStateException если герой уже мертв(HP равно или меньше
     * минимальной отметки HP)
     */
    @Override
    public void takeDamage(Hero heroOwner, int damage) {
        if (isDead()) {
            throw new IllegalStateException(String.format(
                    "%s is already dead. His hp: %d, minHP: %d",
                    heroOwner, this.health, this.minHealth
            ));
        }
        final int startHP = this.health;
        this.health -= damage;
        this.logger.logDamage(heroOwner, startHP, damage, this.health);
        if (isDead()) {
            this.logger.logHeroDie(heroOwner, this.minHealth, this.health);
            this.diedObservables.notifyObservers(heroOwner);
        }
    }

    private boolean isDead() {
        return this.health <= this.minHealth;
    }

    public static class HealthLogger {
        private final Logger logger = Logger.getLogger(HealthLogger.class);

        public void logDamage(Hero hero, int currentHP, int damage, int resultHP) {
            this.logger.info(
                    String.format(
                            "%s, HP: %d. Damage: %d, resultHP: %d.",
                            hero, currentHP, damage, resultHP
                    )
            );
        }

        public void logHeroDie(Hero hero, int minHP, int heroHP) {
            this.logger.info(
                    String.format(
                            "%s die. Min health: %d, hero health: %d.",
                            hero, minHP, heroHP
                    )
            );
        }
    }
}
