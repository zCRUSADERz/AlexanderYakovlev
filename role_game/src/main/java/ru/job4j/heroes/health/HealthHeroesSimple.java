package ru.job4j.heroes.health;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.observers.Observables;

import java.util.HashMap;
import java.util.Map;

/**
 * Health heroes.
 * Хранилище объектов здоровья для каждого героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class HealthHeroesSimple implements HealthHeroes {
    private final Map<Hero, HeroHealth> heroHealthMap;
    private final Observables<HeroDiedObserver> diedObservables;
    private final Logger logger;

    public HealthHeroesSimple(Observables<HeroDiedObserver> diedObservables) {
        this(
                new HashMap<>(),
                diedObservables,
                Logger.getLogger(HealthHeroesSimple.class)
        );
    }

    public HealthHeroesSimple(Map<Hero, HeroHealth> heroHealthMap,
                              Observables<HeroDiedObserver> diedObservables,
                              Logger logger) {
        this.heroHealthMap = heroHealthMap;
        this.diedObservables = diedObservables;
        this.logger = logger;
    }

    /**
     * Герой был атакован. Здоровье атакованного героя получит переданный урон.
     * @param hero атакованный герой.
     * @param damage нанесенный урон.
     */
    @Override
    public void attackHero(Hero hero, int damage) {
        final HeroHealth heroHealth = this.heroHealthMap.get(hero);
        if (heroHealth == null) {
            throw new IllegalStateException(String.format(
                    "%s has no health.", hero
            ));
        }
        this.logger.info(
                String.format(
                        "%s, is attacked.", hero
                )
        );
        heroHealth.takeDamage(hero, damage);
    }

    /**
     * Герой был убит. Будет удалена ссылка на убитого героя и его здоровье.
     * @param hero убитый герой.
     */
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

    /**
     * Герой был создан. Для героя будет создано здоровье и добавлено в карту.
     * @param hero созданный герой.
     */
    @Override
    public void heroCreated(Hero hero) {
        if (this.heroHealthMap.containsKey(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s already has its health.", hero
                    )
            );
        }
        this.heroHealthMap.put(hero, new HealthSimple(this.diedObservables));
    }
}
