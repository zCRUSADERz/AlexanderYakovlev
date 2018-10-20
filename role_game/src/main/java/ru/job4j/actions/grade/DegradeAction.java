package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.Squads;

import java.util.Collection;

public class DegradeAction implements GradeAction {
    private final Squads squads;

    public DegradeAction(Squads squads) {
        this.squads = squads;
    }

    @Override
    public Collection<Hero> gradedHeroes(Hero heroActor) {
         return this.squads.enemySquadFor(heroActor).upgradedHeroes();
    }

    @Override
    public void grade(Hero gradedHero) {
        this.squads.ownSquadFor(gradedHero).degradeHero(gradedHero);
    }

    @Override
    public String actionName() {
        return "Наложение проклятия "
                + "(снятие улучшения с персонажа противника для следующего хода)";
    }
}
