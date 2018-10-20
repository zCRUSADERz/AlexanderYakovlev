package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.Squads;

import java.util.Collection;

public class UpgradeAction implements GradeAction {
    private final Squads squads;

    public UpgradeAction(Squads squads) {
        this.squads = squads;
    }

    @Override
    public Collection<Hero> gradedHeroes(Hero heroActor) {
        return this.squads.ownSquadFor(heroActor).regularHeroes();
    }

    @Override
    public void grade(Hero gradedHero) {
        this.squads.ownSquadFor(gradedHero).upgradeHero(gradedHero);
    }

    @Override
    public String actionName() {
        return "Наложение улучшения на персонажа своего отряда";
    }
}
