package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDieObservable;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.Squads;

import java.util.*;
import java.util.function.Function;

public class GameCycle {
    private final Squads squads;
    private final Stop stop;
    private final Logger logger = Logger.getLogger(GameCycle.class);

    public GameCycle(Squads squads, Stop stop) {
        this.squads = squads;
        this.stop = stop;
    }

    public void start(GradeChangeObservable upgradeObservable,
                      HeroDieObservable dieObservable,
                      HeroMovedObservable movedObservable) {
        this.logger.info("Game starting!");
        while (!this.stop.gameIsStopped()) {
            final HeroMoveSequence sequence = new HeroMoveSequence(
                    heroesInRandomOrder(SquadHeroes::upgradedHeroes),
                    heroesInRandomOrder(SquadHeroes::regularHeroes),
                    movedObservable, this.stop
            );
            upgradeObservable.addObserver(sequence);
            dieObservable.addObserver(sequence);
            sequence.start();
            upgradeObservable.removeObserver(sequence);
            dieObservable.removeObserver(sequence);
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
