package ru.job4j.observable.newhero;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadHeroes;

public interface HeroCreatedObservable {

    void addObserver(HeroCreatedObserver observer);

    void removeObserver(HeroCreatedObserver observer);

    void heroCreated(Hero hero, SquadHeroes ownSquad, SquadHeroes enemySquad);
}
