package ru.job4j.sequences;

import org.apache.log4j.Logger;
import ru.job4j.Stop;
import ru.job4j.observable.die.DieObserver;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.upgrade.UpgradeObserver;

import java.util.*;

public class HeroMoveSequence implements DieObserver, UpgradeObserver {
    private final Collection<Hero> movedHeroes = new HashSet<>();
    private final Collection<Hero> upgradedHeroes;
    private final Collection<Hero> regularHeroes;
    private final HeroMovedObservable heroMovedObservable;
    private final Stop stop;
    private final Logger logger = Logger.getLogger(HeroMoveSequence.class);

    public HeroMoveSequence(Collection<Hero> upgradedHeroes,
                            Collection<Hero> regularHeroes, HeroMovedObservable heroMovedObservable, Stop stop) {
        this.upgradedHeroes = upgradedHeroes;
        this.regularHeroes = regularHeroes;
        this.heroMovedObservable = heroMovedObservable;
        this.stop = stop;
    }

    public void start() {
        while (true) {
            final Iterator<Hero> iterator;
            if (this.stop.gameIsStopped()) {
                break;
            }
            this.logSequence();
            if (!this.upgradedHeroes.isEmpty()) {
                iterator = this.upgradedHeroes.iterator();

            } else if (!this.regularHeroes.isEmpty()) {
                iterator = this.regularHeroes.iterator();
            } else {
                break;
            }
            final Hero hero = iterator.next();
            iterator.remove();
            this.logger.info(String.format("%s turn.", hero));
            hero.doAction();
            this.movedHeroes.add(hero);
            this.heroMovedObservable.heroMoved(hero);
            this.logger.info(String.format("%s completed his turn.", hero));
            this.logger.info("------------------------------------");
        }
    }

    @Override
    public void heroDied(Hero hero) {
        this.upgradedHeroes.remove(hero);
        this.regularHeroes.remove(hero);
    }

    @Override
    public void upgraded(Hero hero) {
        if (!this.movedHeroes.contains(hero)) {
            this.regularHeroes.remove(hero);
            this.upgradedHeroes.add(hero);
            this.logger.info(
                    String.format("%s was replace to upgraded group", hero)
            );
        }
    }

    @Override
    public void degraded(Hero hero) {
        if (!this.movedHeroes.contains(hero)) {
            this.upgradedHeroes.remove(hero);
            this.regularHeroes.add(hero);
            this.logger.info(
                    String.format("%s was replace to regular group", hero)
            );
        }
    }

    private void logSequence() {
        this.logger.info(String.format(
                "Sequence of moves of heroes: upgraded:%s regular:%s",
                this.upgradedHeroes, this.regularHeroes
        ));
        logger.info("------------------------------------");
    }
}
