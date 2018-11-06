package ru.job4j.squad;

import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.move.HeroMovedObserver;
import ru.job4j.observable.newhero.HeroCreatedObserver;

import java.util.Collection;
import java.util.Optional;

/**
 * Squad heroes.
 * Отряд героев.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface SquadHeroes
        extends HeroDiedObserver, HeroMovedObserver, HeroCreatedObserver {

    /**
     * Рандомный герой из отряда.
     * @return рандомный герой из отряда.
     */
    Optional<Hero> randomHero();

    /**
     * Привилегированные герои отряда.
     * @return привилегированные герои отряда.
     */
    Collection<Hero> upgradedHeroes();

    Optional<Hero> randomUpgradedHero();

    /**
     * Обычные герои отряда.
     * @return обычные герои отряда.
     */
    Collection<Hero> regularHeroes();

    Optional<Hero> randomRegularHero();

    /**
     * Улучшить героя.
     * @param hero улучшаемый герой.
     */
    void upgradeHero(Hero hero);

    /**
     * Снять улучшение с героя.
     * @param hero герой с которого снимается улучшение.
     */
    void degradeHero(Hero hero);
}
