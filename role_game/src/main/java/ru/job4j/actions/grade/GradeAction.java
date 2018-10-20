package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;

import java.util.Collection;

public interface GradeAction {

    Collection<Hero> gradedHeroes(Hero heroActor);

    void grade(Hero gradedHero);

    String actionName();
}
