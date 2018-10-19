package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDieObservable;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.sequences.HeroMoveSequence;

import java.util.*;

public class GameCycle {
    private final Collection<SquadHeroes> squads;
    private final Stop stop;
    private final Logger logger = Logger.getLogger(GameCycle.class);

    public GameCycle(Collection<SquadHeroes> squads, Stop stop) {
        this.squads = squads;
        this.stop = stop;
    }

    public void start(GradeChangeObservable upgradeObservable,
                      HeroDieObservable dieObservable,
                      HeroMovedObservable movedObservable) {
        this.logger.info("Game starting!");
        while (!this.stop.gameIsStopped()) {
            final HeroMoveSequence sequence = new HeroMoveSequence(
                    allUpgradedHeroesInRandomOrder(),
                    allRegularHeroesInRandomOrder(),
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

    private Collection<Hero> allUpgradedHeroesInRandomOrder() {
        final List<Hero> heroes = new ArrayList<>();
        this.squads.forEach(squad -> heroes.addAll(squad.upgradedHeroes()));
        Collections.shuffle(heroes);
        return new LinkedHashSet<>(heroes);
    }

    private Collection<Hero> allRegularHeroesInRandomOrder() {
        final List<Hero> heroes = new ArrayList<>();
        this.squads.forEach(squad -> heroes.addAll(squad.regularHeroes()));
        Collections.shuffle(heroes);
        return new LinkedHashSet<>(heroes);
    }
}
