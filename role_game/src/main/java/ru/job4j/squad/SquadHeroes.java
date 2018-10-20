package ru.job4j.squad;

import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.move.HeroMovedObserver;
import ru.job4j.observable.newhero.HeroCreatedObservable;
import ru.job4j.observable.newhero.HeroCreatedObserver;

import java.util.Collection;

public interface SquadHeroes
        extends HeroDiedObserver, HeroMovedObserver, HeroCreatedObserver {
    
    Hero randomHero();

    Collection<Hero> upgradedHeroes();

    Collection<Hero> regularHeroes();

    void upgradeHero(Hero hero);

    void degradeHero(Hero hero);
}
