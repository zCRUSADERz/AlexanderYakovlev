package ru.job4j.squad;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.DieObserver;

import java.util.Collection;

public interface Squads extends DieObserver {

    Collection<SquadHeroes> allSquads();

    SquadHeroes enemySquadFor(Hero hero);

    SquadHeroes ownSquadFor(Hero hero);
}
