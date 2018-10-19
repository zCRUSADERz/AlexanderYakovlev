package ru.job4j.actions.grade;

import ru.job4j.SquadHeroes;
import ru.job4j.heroes.Hero;

import java.util.Collection;

public interface GradeAction {

    Collection<Hero> gradedHeroes(SquadHeroes squadHeroes);

    void grade(SquadHeroes squadHeroes, Hero hero);
}
