package ru.job4j.actions;

import ru.job4j.heroes.Hero;

public interface HeroAction {

    void act(Hero heroActor);

    @Override
    String toString();
}
