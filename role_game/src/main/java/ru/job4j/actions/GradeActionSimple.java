package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.actions.actiontarget.RandomTargetForHero;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.Observables;

import java.util.Optional;

public class GradeActionSimple<T> implements HeroAction {
    private final String actionName;
    private final HeroAction defaultAction;
    private final Observables<T> observables;
    private final RandomTargetForHero randomTarget;
    private final Logger logger = Logger.getLogger(GradeActionSimple.class);

    public GradeActionSimple(String actionName, HeroAction defaultAction,
                             Observables<T> observables,
                             RandomTargetForHero randomTarget) {
        this.actionName = actionName;
        this.defaultAction = defaultAction;
        this.observables = observables;
        this.randomTarget = randomTarget;
    }

    @Override
    public void act(Hero heroActor) {
        final Optional<Hero> target = this.randomTarget.targetFor(heroActor);
        if (!target.isPresent()) {
            this.logger.info(String.format(
                    "%s did not find suitable targets.",
                    heroActor
            ));
            this.logger.info(String.format(
                    "%s perform the default action(%s).",
                    heroActor, this.defaultAction
            ));
            this.defaultAction.act(heroActor);
        } else {
            final Hero gradedHero = target.get();
            this.logger.info(
                    String.format(
                            "%s. Цель: %s.",
                            this.actionName, gradedHero
                    )
            );
            this.observables.notifyObservers(gradedHero);
        }
    }

    @Override
    public String toString() {
        return this.actionName;
    }
}
