package ru.job4j.heroes.health;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.observable.newhero.HeroCreatedObserver;

public interface HealthHeroes extends HeroDiedObserver, HeroCreatedObserver {

    void attackHero(Hero hero, int damage);
}
