package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangeObserver;

import java.util.*;

/**
 * Hero move sequence.
 * Порядок ходов героев в раунде.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class HeroMoveSequence implements HeroDiedObserver, GradeChangeObserver {
    private final Collection<Hero> movedHeroes = new HashSet<>();
    private final Collection<Hero> upgradedHeroes;
    private final Collection<Hero> regularHeroes;
    private final HeroMovedObservable heroMovedObservable;
    private final StopGame stopGame;
    private final Logger logger = Logger.getLogger(HeroMoveSequence.class);

    public HeroMoveSequence(Collection<Hero> upgradedHeroes,
                            Collection<Hero> regularHeroes, HeroMovedObservable heroMovedObservable, StopGame stopGame) {
        this.upgradedHeroes = upgradedHeroes;
        this.regularHeroes = regularHeroes;
        this.heroMovedObservable = heroMovedObservable;
        this.stopGame = stopGame;
    }

    /**
     * Запустить цикл ходов героев.
     */
    public void start() {
        while (!this.stopGame.gameIsStopped()) {
            final Iterator<Hero> iterator;
            this.logSequence();
            if (!this.upgradedHeroes.isEmpty()) {
                iterator = this.upgradedHeroes.iterator();
            } else if (!this.regularHeroes.isEmpty()) {
                iterator = this.regularHeroes.iterator();
            } else {
                this.logger.info("Round is over.");
                break;
            }
            final Hero hero = iterator.next();
            iterator.remove();
            this.logger.info(String.format("%s turn.", hero));
            this.movedHeroes.add(hero);
            this.heroMovedObservable.heroMoved(hero);
            hero.doAction();
            this.logger.info(String.format("%s completed his turn.", hero));
            this.logger.info("------------------------------------");
        }
    }

    /**
     * герой был убит. Ссыки на героя будут удалены.
     * @param hero убитый герой.
     */
    @Override
    public void heroDied(Hero hero) {
        this.upgradedHeroes.remove(hero);
        this.regularHeroes.remove(hero);
        this.movedHeroes.remove(hero);
    }

    /**
     * Герой был улучшен. Если герой еще не ходил,
     * то он будет перемещен в группу привилегированных.
     * @param hero улучшенный герой.
     */
    @Override
    public void upgraded(Hero hero) {
        if (!this.movedHeroes.contains(hero)) {
            if (!this.regularHeroes.remove(hero)) {
                throw new IllegalStateException(
                        String.format("%s not in regular group", hero)
                );
            }
            this.upgradedHeroes.add(hero);
            this.logger.info(
                    String.format("%s was replace to upgraded group", hero)
            );
        }
    }

    /**
     * С героя было снято улучшение. Если герой еще не ходил,
     * то он бдет перемещен в группу обычных героев.
     * @param hero герой с которого сняли улучшение.
     */
    @Override
    public void degraded(Hero hero) {
        if (!this.movedHeroes.contains(hero)) {
            if (!this.upgradedHeroes.remove(hero)) {
                throw new IllegalStateException(
                        String.format("%s not in upgraded group", hero)
                );
            }
            this.regularHeroes.add(hero);
            this.logger.info(
                    String.format("%s was replace to regular group", hero)
            );
        }
    }

    private void logSequence() {
        this.logger.info(String.format(
                "Sequence of heroes moves: upgraded:%s regular:%s",
                this.upgradedHeroes, this.regularHeroes
        ));
        logger.info("------------------------------------");
    }
}
