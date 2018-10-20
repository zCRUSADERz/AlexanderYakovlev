package ru.job4j.heroes.health;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.observable.newhero.HeroCreatedObserver;
import ru.job4j.squad.SquadHeroes;

import java.util.Map;

public class HealthHeroes implements HeroDiedObserver, HeroCreatedObserver {
    private final Map<Hero, HeroHealth> heroHealthMap;
    private final HeroDiedObservable dieObservable;
    private final Logger logger = Logger.getLogger(HealthHeroes.class);

    public HealthHeroes(Map<Hero, HeroHealth> heroHealthMap, HeroDiedObservable dieObservable) {
        this.heroHealthMap = heroHealthMap;
        this.dieObservable = dieObservable;
    }

    public void attackHero(Hero hero, int damage) {
        this.logger.info(
                String.format(
                        "%s, is attacked.", hero
                )
        );
        this.heroHealthMap.get(hero).takeDamage(damage);
    }

    @Override
    public void heroDied(Hero hero) {
        if (this.heroHealthMap.containsKey(hero)) {
            this.heroHealthMap.remove(hero);
            this.logger.info(String.format("%s health deleted.", hero));
        } else {
            this.logger.error(String.format("%s not have his health.", hero));
        }
    }


    @Override
    public void heroCreated(Hero hero) {
        this.heroHealthMap.put(hero, new HealthSimple(this.dieObservable, hero));
    }
}
