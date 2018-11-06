package ru.job4j.actions.grade;

import org.apache.log4j.Logger;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTargetForGrade;
import ru.job4j.heroes.Hero;

public class GradeActionSimple implements HeroAction {
    private final HeroAction defaultAction;
    private final GradeAction gradeAction;
    private final RandomTargetForGrade randomTarget;
    private final Logger logger = Logger.getLogger(GradeActionSimple.class);

    public GradeActionSimple(HeroAction defaultAction,
                             GradeAction gradeAction,
                             RandomTargetForGrade randomTarget) {
        this.defaultAction = defaultAction;
        this.gradeAction = gradeAction;
        this.randomTarget = randomTarget;
    }

    @Override
    public void act(Hero heroActor) {
        if (this.randomTarget.targetsIsEmptyFor(heroActor)) {
            this.logger.info(String.format(
                    "%s did not find suitable targets.%n"
                            + "%s perform the default action(%s).",
                    heroActor, heroActor, this.defaultAction
            ));
            this.defaultAction.act(heroActor);
        } else {
            final Hero gradedHero = this.randomTarget.randomTargetFor(heroActor);
            this.logger.info(
                    String.format(
                            "%s. Цель: %s.",
                            this.gradeAction.actionName(), gradedHero
                    )
            );
            this.gradeAction.grade(gradedHero);
        }
    }

    @Override
    public String toString() {
        return this.gradeAction.actionName();
    }
}
