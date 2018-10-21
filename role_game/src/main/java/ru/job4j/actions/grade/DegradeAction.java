package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

import java.util.Collection;

public class DegradeAction implements GradeAction {
    private final SquadsMapper squadsMapper;

    public DegradeAction(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    @Override
    public Collection<Hero> gradedHeroes(Hero heroActor) {
         return this.squadsMapper.enemySquadFor(heroActor).upgradedHeroes();
    }

    @Override
    public void grade(Hero gradedHero) {
        this.squadsMapper.ownSquadFor(gradedHero).degradeHero(gradedHero);
    }

    @Override
    public String actionName() {
        return "Наложение проклятия "
                + "(снятие улучшения с персонажа противника для следующего хода)";
    }
}
