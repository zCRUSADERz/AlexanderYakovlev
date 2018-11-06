package ru.job4j.actions.grade;

import org.apache.log4j.Logger;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTarget;
import ru.job4j.heroes.Hero;

import java.util.Optional;

public class GradeActionSimple implements HeroAction {
    private final HeroAction defaultAction;
    private final GradeAction gradeAction;
    private final RandomTarget randomTarget;
    private final Logger logger = Logger.getLogger(GradeActionSimple.class);

    public GradeActionSimple(HeroAction defaultAction,
                             GradeAction gradeAction,
                             RandomTarget randomTarget) {
        this.defaultAction = defaultAction;
        this.gradeAction = gradeAction;
        this.randomTarget = randomTarget;
    }

    @Override
    public void act(Hero heroActor) {
        final Optional<Hero> target = this.randomTarget.randomTargetFor(heroActor);
        if (!target.isPresent()) {
            this.logger.info(String.format(
                    "%s did not find suitable targets.%n"
                            + "%s perform the default action(%s).",
                    heroActor, heroActor, this.defaultAction
            ));
            this.defaultAction.act(heroActor);
        } else {
            final Hero gradedHero = target.get();
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
