package ru.job4j.observable.newhero;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadHeroes;

public interface HeroCreatedObserver {

    void heroCreated(Hero hero);
}
