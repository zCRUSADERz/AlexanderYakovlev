package ru.job4j;

import ru.job4j.heroes.HeroType;
import ru.job4j.observable.die.HeroDiedObservableSimple;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.move.HeroMovedObservableSimple;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangedObservableSimple;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.observable.newhero.HeroCreatedObservableSimple;
import ru.job4j.observable.newhero.HeroCreatedObservable;
import ru.job4j.races.*;
import ru.job4j.squad.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Game.
 * Основной класс игры.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class Game {

    /**
     * Запустить игру.
     */
    public void start() {
        final HeroDiedObservable diedObservable = new HeroDiedObservableSimple();
        final GradeChangeObservable upgradeObservable = new GradeChangedObservableSimple();
        final HeroMovedObservable movedObservable = new HeroMovedObservableSimple();
        final HeroCreatedObservable createdObservable = new HeroCreatedObservableSimple();
        final GameEnvironment environment = new GameEnvironmentInitialize(
                createdObservable, movedObservable,
                upgradeObservable, diedObservable
        ).init();
        final RaceSquads raceSquads = new RaceSquadsSimple(
                Arrays.asList(
                        new ElvesRaceFactory(
                                environment.getSquadsMapper(),
                                environment.getHealthHeroes(),
                                environment.getModifiers(),
                                environment.getRandom()
                        ).createRace(),
                        new HumansRaceFactory(
                                environment.getSquadsMapper(),
                                environment.getHealthHeroes(),
                                environment.getModifiers(),
                                environment.getRandom()
                        ).createRace()
                ),
                Arrays.asList(
                        new OrcsRaceFactory(
                                environment.getSquadsMapper(),
                                environment.getHealthHeroes(),
                                environment.getModifiers(),
                                environment.getRandom()
                        ).createRace(),
                        new UndeadRaceFactory(
                                environment.getSquadsMapper(),
                                environment.getHealthHeroes(),
                                environment.getModifiers(),
                                environment.getRandom()
                        ).createRace()
                ),
                environment.getRandom()
        );
        final Opponents opponents = raceSquads.chooseOpponents(
                upgradeObservable,
                environment.getSquadsMapper(),
                environment.getStopGame()
        );
        final Map<HeroType, Integer> numberOfHeroes = new HashMap<>();
        numberOfHeroes.put(HeroType.MAGE, 1);
        numberOfHeroes.put(HeroType.ARCHER, 3);
        numberOfHeroes.put(HeroType.WARRIOR, 4);
        opponents.createSquads(numberOfHeroes);
        new GameCycle(
                environment.getSquadsMapper(),
                diedObservable,
                upgradeObservable,
                movedObservable,
                environment.getStopGame()
        ).start();
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
