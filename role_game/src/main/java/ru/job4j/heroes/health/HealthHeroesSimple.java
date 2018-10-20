package ru.job4j.heroes.health;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;

import java.util.HashMap;
import java.util.Map;

public class HealthHeroesSimple implements HealthHeroes {
    private final Map<Hero, HeroHealth> heroHealthMap;
    private final HeroDiedObservable diedObservable;
    private final Logger logger = Logger.getLogger(HealthHeroesSimple.class);

    public HealthHeroesSimple(HeroDiedObservable diedObservable) {
        this(new HashMap<>(), diedObservable);
    }

    public HealthHeroesSimple(Map<Hero, HeroHealth> heroHealthMap, HeroDiedObservable diedObservable) {
        this.heroHealthMap = heroHealthMap;
        this.diedObservable = diedObservable;
    }

    public void attackHero(Hero hero, int damage) {
        this.logger.info(
                String.format(
                        "%s, is attacked.", hero
                )
        );
        this.heroHealthMap.get(hero).takeDamage(hero, damage);
    }

    @Override
    public void heroDied(Hero hero) {
        if (!this.heroHealthMap.containsKey(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s has no health.", hero
                    )
            );
        }
        this.heroHealthMap.remove(hero);
    }


    @Override
    public void heroCreated(Hero hero) {
        if (this.heroHealthMap.containsKey(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s already has its health.", hero
                    )
            );
        }
        this.heroHealthMap.put(hero, new HealthSimple(this.diedObservable));
    }
}
