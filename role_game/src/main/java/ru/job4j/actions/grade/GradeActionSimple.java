package ru.job4j.actions.grade;

import org.apache.log4j.Logger;
import ru.job4j.SquadHeroes;
import ru.job4j.actions.HeroAction;
import ru.job4j.heroes.Hero;
import ru.job4j.utils.RandomElementFromList;

import java.util.ArrayList;
import java.util.Collection;

public class GradeActionSimple implements HeroAction {
    private final SquadHeroes squadHeroes;
    private final RandomElementFromList random;
    private final HeroAction defaultAction;
    private final GradeAction gradeAction;
    private final Logger logger = Logger.getLogger(GradeActionSimple.class);

    public GradeActionSimple(SquadHeroes squadHeroes, RandomElementFromList random,
                             HeroAction defaultAction, GradeAction gradeAction) {
        this.squadHeroes = squadHeroes;
        this.random = random;
        this.defaultAction = defaultAction;
        this.gradeAction = gradeAction;
    }

    @Override
    public void act(Hero heroActor) {
        final Collection<Hero> gradedHeroes
                = this.gradeAction.gradedHeroes(this.squadHeroes);
        if (gradedHeroes.isEmpty()) {
            this.logger.info(String.format(
                    "Hero %s did not find suitable targets. "
                            + "%s perform the default action(%s).",
                    heroActor, heroActor, this.defaultAction
            ));
            this.defaultAction.act(heroActor);
        } else {
            final Hero gradedHero
                    = this.random.randomElement(
                    new ArrayList<>(gradedHeroes)
            );
            this.logger.info(
                    String.format(
                            "%s. Цель: %s.",
                            this.gradeAction.actionName(), gradedHero
                    )
            );
            this.gradeAction.grade(this.squadHeroes, gradedHero);
        }
    }

    @Override
    public String toString() {
        return this.gradeAction.actionName();
    }
}
