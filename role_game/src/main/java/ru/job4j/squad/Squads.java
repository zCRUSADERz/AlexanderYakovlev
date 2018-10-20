package ru.job4j.squad;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.observable.move.HeroMovedObserver;

import java.util.Collection;

public interface Squads extends HeroDiedObserver, HeroMovedObserver {

    Collection<SquadHeroes> allSquads();

    SquadHeroes enemySquadFor(Hero hero);

    SquadHeroes ownSquadFor(Hero hero);

    void newHeroCreated(Hero hero, SquadHeroes ownSquad, SquadHeroes enemySquad);
}
