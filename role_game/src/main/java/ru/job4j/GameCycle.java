package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.SquadHeroes;

import java.util.*;
import java.util.function.Function;

/**
 * Game cycle.
 * Запускает новые раунды, пока какой либо из отрядов не победит.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class GameCycle {
    private final HeroDiedObservable dieObservable;
    private final GradeChangeObservable upgradeObservable;
    private final HeroMovedObservable movedObservable;
    private final GameEnvironment environment;
    private final Logger logger = Logger.getLogger(GameCycle.class);

    public GameCycle(HeroDiedObservable dieObservable,
                     GradeChangeObservable upgradeObservable,
                     HeroMovedObservable movedObservable,
                     GameEnvironment environment) {
        this.dieObservable = dieObservable;
        this.upgradeObservable = upgradeObservable;
        this.movedObservable = movedObservable;
        this.environment = environment;
    }

    /**
     * Запустить игровой цикл.
     */
    public void startGame() {
        this.logger.info("Game starting!");
        final StopGame stopGame = this.environment.getStopGame();
        while (!stopGame.gameIsStopped()) {
            final HeroMoveSequence sequence = new HeroMoveSequence(
                    heroesInRandomOrder(SquadHeroes::upgradedHeroes),
                    heroesInRandomOrder(SquadHeroes::regularHeroes),
                    this.movedObservable,
                    stopGame
            );
            this.upgradeObservable.addObserver(sequence);
            this.dieObservable.addObserver(sequence);
            sequence.start();
            this.upgradeObservable.removeObserver(sequence);
            this.dieObservable.removeObserver(sequence);
        }
        this.logger.info("Game finished!");
    }

    private Collection<Hero> heroesInRandomOrder(
            Function<SquadHeroes, Collection<Hero>> function) {
        final List<Hero> heroes = new ArrayList<>();
        this.environment
                .getSquadsMapper()
                .allSquads()
                .forEach(
                        squad -> heroes.addAll(function.apply(squad))
                );
        Collections.shuffle(heroes);
        return new LinkedHashSet<>(heroes);
    }
}
