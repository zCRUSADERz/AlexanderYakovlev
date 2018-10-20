package ru.job4j.squad;

import ru.job4j.observable.die.DieObserver;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.move.HeroMovedObserver;

import java.util.Collection;

public interface SquadHeroes extends DieObserver, HeroMovedObserver {
    
    Hero randomHero();

    Collection<Hero> upgradedHeroes();

    Collection<Hero> regularHeroes();

    void upgradeHero(Hero hero);

    void degradeHero(Hero hero);
}
