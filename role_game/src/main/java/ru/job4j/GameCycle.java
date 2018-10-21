package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.SquadsMapper;

import java.util.*;
import java.util.function.Function;

public class GameCycle {
    private final SquadsMapper squadsMapper;
    private final HeroDiedObservable dieObservable;
    private final GradeChangeObservable upgradeObservable;
    private final HeroMovedObservable movedObservable;
    private final StopGame stopGame;
    private final Logger logger = Logger.getLogger(GameCycle.class);

    public GameCycle(SquadsMapper squadsMapper, HeroDiedObservable dieObservable,
                     GradeChangeObservable upgradeObservable,
                     HeroMovedObservable movedObservable, StopGame stopGame) {
        this.squadsMapper = squadsMapper;
        this.dieObservable = dieObservable;
        this.upgradeObservable = upgradeObservable;
        this.movedObservable = movedObservable;
        this.stopGame = stopGame;
    }

    public void start() {
        this.logger.info("Game starting!");
        while (!this.stopGame.gameIsStopped()) {
            final HeroMoveSequence sequence = new HeroMoveSequence(
                    heroesInRandomOrder(SquadHeroes::upgradedHeroes),
                    heroesInRandomOrder(SquadHeroes::regularHeroes),
                    this.movedObservable, this.stopGame
            );
            this.upgradeObservable.addObserver(sequence);
            this.dieObservable.addObserver(sequence);
            sequence.start();
            this.upgradeObservable.removeObserver(sequence);
            this.dieObservable.removeObserver(sequence);
        }
        this.logger.info("Game finished!");
    }

    private Collection<Hero> heroesInRandomOrder(Function<SquadHeroes, Collection<Hero>> function) {
        final List<Hero> heroes = new ArrayList<>();
        this.squadsMapper.allSquads().forEach(squad -> heroes.addAll(function.apply(squad)));
        Collections.shuffle(heroes);
        return new LinkedHashSet<>(heroes);
    }
}
