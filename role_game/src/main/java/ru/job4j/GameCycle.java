package ru.job4j;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroMovedObserver;
import ru.job4j.observers.Observables;
import ru.job4j.squad.*;
import ru.job4j.xml.races.SquadsParser;

import java.util.*;

/**
 * Game cycle.
 * Запускает новые раунды, пока какой либо из отрядов не победит.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class GameCycle {
    private final SquadsParser squadsParser;
    private final SquadRegister register;
    private final Observables<HeroMovedObserver> movedObservables;
    private final StopGame stopGame;
    private final Logger logger = Logger.getLogger(GameCycle.class);

    public GameCycle(SquadsParser squadsParser,
                     SquadRegister register,
                     Observables<HeroMovedObserver> movedObservables,
                     StopGame stopGame) {
        this.squadsParser = squadsParser;
        this.register = register;
        this.movedObservables = movedObservables;
        this.stopGame = stopGame;
    }

    /**
     * Запустить игровой цикл.
     */
    public void startGame(Document document) {
        this.logger.info("Game starting!");
        final Squads squads = this.squadsParser.parse(document);
        while (!this.stopGame.gameIsStopped()) {
            final Map<SquadSubType, SquadOperation> operations = new HashMap<>();
            operations.put(
                    SquadSubType.REGULAR,
                    new SquadOperation("Очередь-рядовые")
            );
            operations.put(
                    SquadSubType.UPGRADED,
                    new SquadOperation("Очередь-привилегированные")
            );
            final Squad squad = new Squad("Очередь", operations);
            this.register.register(squad);
            this.addHeroes(SquadSubType.REGULAR, squad, squads);
            this.addHeroes(SquadSubType.UPGRADED, squad, squads);
            final HeroMoveSequence sequence = new HeroMoveSequence(
                    Arrays.asList(
                            squad.operation(SquadSubType.UPGRADED),
                            squad.operation(SquadSubType.REGULAR)
                    ),
                    this.movedObservables,
                    this.stopGame
            );
            sequence.start();
        }
        this.logger.info("Game finished!");
    }

    private void addHeroes(SquadSubType squadType, Squad squad, Squads squads) {
        final List<Hero> heroes
                = squads.heroes(squadType);
        Collections.shuffle(heroes);
        for (Hero hero : heroes) {
            squad
                    .operation(squadType)
                    .add(hero);
        }
    }
}
