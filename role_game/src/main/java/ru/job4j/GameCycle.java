package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.Squads;

import java.util.*;
import java.util.function.Function;

public class GameCycle {
    private final Squads squads;
    private final HeroDiedObservable dieObservable;
    private final GradeChangeObservable upgradeObservable;
    private final HeroMovedObservable movedObservable;
    private final Stop stop;
    private final Logger logger = Logger.getLogger(GameCycle.class);

    public GameCycle(Squads squads, HeroDiedObservable dieObservable,
                     GradeChangeObservable upgradeObservable,
                     HeroMovedObservable movedObservable, Stop stop) {
        this.squads = squads;
        this.dieObservable = dieObservable;
        this.upgradeObservable = upgradeObservable;
        this.movedObservable = movedObservable;
        this.stop = stop;
    }

    public void start() {
        this.logger.info("Game starting!");
        while (!this.stop.gameIsStopped()) {
            final HeroMoveSequence sequence = new HeroMoveSequence(
                    heroesInRandomOrder(SquadHeroes::upgradedHeroes),
                    heroesInRandomOrder(SquadHeroes::regularHeroes),
                    this.movedObservable, this.stop
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
        this.squads.allSquads().forEach(squad -> heroes.addAll(function.apply(squad)));
        Collections.shuffle(heroes);
        return new LinkedHashSet<>(heroes);
    }
}
