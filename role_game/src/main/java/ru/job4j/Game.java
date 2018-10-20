package ru.job4j;

import ru.job4j.heroes.attack.AttackModifierChangeByGrade;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.heroes.health.HealthHeroesSimple;
import ru.job4j.observable.die.HeroDiedObservableSimple;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.move.HeroMovedObservableSimple;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangedObservableSimple;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.observable.newhero.HeroCreatedObservableSimple;
import ru.job4j.observable.newhero.HeroCreatedObservable;
import ru.job4j.races.RacesFactorySimple;
import ru.job4j.squad.*;

public class Game {

    public void start() {
        final HeroDiedObservable diedObservable = new HeroDiedObservableSimple();
        final GradeChangeObservable upgradeObservable = new GradeChangedObservableSimple();
        final HeroMovedObservable movedObservable = new HeroMovedObservableSimple();
        final HeroCreatedObservable createdObservable = new HeroCreatedObservableSimple();
        final StopSimple stopGame = new StopSimple();
        final Squads squads = new SquadsSimple(createdObservable);
        diedObservable.addObserver(squads);
        movedObservable.addObserver(squads);
        final HealthHeroes healthHeroes = new HealthHeroesSimple(diedObservable);
        diedObservable.addObserver(healthHeroes);
        createdObservable.addObserver(healthHeroes);
        final AttackStrengthModifiers modifiers = new AttackStrengthModifiers();
        diedObservable.addObserver(modifiers);
        movedObservable.addObserver(modifiers);
        createdObservable.addObserver(modifiers);
        upgradeObservable.addObserver(
                new AttackModifierChangeByGrade(
                        modifiers,
                        new AttackStrengthModifierSimple(1.5d)
                )
        );
        new RacesFactorySimple()
                .createRaces(
                        squads, healthHeroes,
                        modifiers, upgradeObservable, stopGame
                ).createHeroes(1, 3, 4);
        new GameCycle(
                squads,
                diedObservable,
                upgradeObservable,
                movedObservable,
                stopGame
        ).start();
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
