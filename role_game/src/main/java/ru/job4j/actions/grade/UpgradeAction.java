package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

import java.util.Collection;

public class UpgradeAction implements GradeAction {
    private final SquadsMapper squadsMapper;

    public UpgradeAction(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    @Override
    public Collection<Hero> gradedHeroes(Hero heroActor) {
        return this.squadsMapper.ownSquadFor(heroActor).regularHeroes();
    }

    @Override
    public void grade(Hero gradedHero) {
        this.squadsMapper.ownSquadFor(gradedHero).upgradeHero(gradedHero);
    }

    @Override
    public String actionName() {
        return "Наложение улучшения на персонажа своего отряда";
    }
}
