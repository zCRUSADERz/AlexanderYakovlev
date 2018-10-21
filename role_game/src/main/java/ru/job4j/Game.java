package ru.job4j;

import ru.job4j.heroes.HeroType;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
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
import ru.job4j.utils.RandomElementFromList;

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
        final GameEnvironment environment = new GameEnvironment(
                createdObservable, movedObservable,
                upgradeObservable, diedObservable
        );
        final StopGame stopGame = environment.createStopGame();
        final SquadsMapper squadsMapper = environment.createSquads();
        final HealthHeroes healthHeroes = environment.createHeroesHealths();
        final AttackStrengthModifiers modifiers = environment.createAttackModifiers();
        final RandomElementFromList random = environment.createRandomize();
        final RaceSquads raceSquads = new RaceSquadsSimple(
                Arrays.asList(
                        new ElvesRaceFactory(
                                squadsMapper, healthHeroes,
                                modifiers, random
                        ).createRace(),
                        new HumansRaceFactory(
                                squadsMapper, healthHeroes,
                                modifiers, random
                        ).createRace()
                ),
                Arrays.asList(
                        new OrcsRaceFactory(
                                squadsMapper, healthHeroes,
                                modifiers, random
                        ).createRace(),
                        new UndeadRaceFactory(
                                squadsMapper, healthHeroes,
                                modifiers, random
                        ).createRace()
                ),
                random
        );
        final Opponents opponents = raceSquads.chooseOpponents(
                upgradeObservable, squadsMapper, stopGame
        );
        final Map<HeroType, Integer> numberOfHeroes = new HashMap<>();
        numberOfHeroes.put(HeroType.MAGE, 1);
        numberOfHeroes.put(HeroType.ARCHER, 3);
        numberOfHeroes.put(HeroType.WARRIOR, 4);
        opponents.createSquads(numberOfHeroes);
        new GameCycle(
                squadsMapper,
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
