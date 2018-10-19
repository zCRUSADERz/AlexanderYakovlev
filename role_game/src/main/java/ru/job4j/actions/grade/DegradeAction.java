package ru.job4j.actions.grade;

import ru.job4j.SquadHeroes;
import ru.job4j.heroes.Hero;

import java.util.Collection;

public class DegradeAction implements GradeAction {

    @Override
    public Collection<Hero> gradedHeroes(SquadHeroes squadHeroes) {
        return squadHeroes.upgradedHeroes();
    }

    @Override
    public void grade(SquadHeroes squadHeroes, Hero hero) {
        squadHeroes.degradeHero(hero);
    }
}
