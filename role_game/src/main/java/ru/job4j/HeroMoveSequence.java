package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroMovedObserver;
import ru.job4j.observers.Observables;
import ru.job4j.squad.SquadHeroes;

import java.util.*;

/**
 * Hero move sequence.
 * Порядок ходов героев в раунде.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class HeroMoveSequence {
    private final List<SquadHeroes> squads;
    private final Observables<HeroMovedObserver> movedObservables;
    private final StopGame stopGame;
    private final Logger logger = Logger.getLogger(HeroMoveSequence.class);

    public HeroMoveSequence(List<SquadHeroes> squads,
                            Observables<HeroMovedObserver> movedObservables,
                            StopGame stopGame) {
        this.squads = squads;
        this.movedObservables = movedObservables;
        this.stopGame = stopGame;
    }

    /**
     * Запустить цикл ходов героев.
     */
    public void start() {
        boolean roundOn = true;
        while (!this.stopGame.gameIsStopped() && roundOn) {
            this.logger.info(String.format(
                    "Sequence of heroes moves: upgraded:%s regular:%s",
                    this.squads.get(0).heroes(), this.squads.get(1).heroes()
            ));
            logger.info("------------------------------------");
            final Iterator<SquadHeroes> iterator = this.squads.iterator();
            Optional<Hero> optionalHero = Optional.empty();
            while (!optionalHero.isPresent() && iterator.hasNext()) {
                optionalHero = iterator.next().hero();
            }
            if (optionalHero.isPresent()) {
                final Hero hero = optionalHero.get();
                this.logger.info(String.format("%s turn.", hero));
                hero.doAction();
                this.logger.info(String.format("%s completed his turn.", hero));
                this.movedObservables.notifyObservers(hero);
                this.logger.info("------------------------------------");
            } else {
                roundOn = false;
                this.logger.info("Round is over.");
            }
        }
    }
}
