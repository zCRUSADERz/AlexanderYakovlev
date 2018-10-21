package ru.job4j.races;

import org.apache.log4j.Logger;
import ru.job4j.StopGame;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.Opponents;
import ru.job4j.squad.OpponentsSimple;
import ru.job4j.squad.SquadsMapper;
import ru.job4j.utils.RandomElementFromList;

import java.util.List;

public class RaceSquadsSimple implements RaceSquads {
    private final List<Race> firstSquad;
    private final List<Race> secondSquad;
    private final RandomElementFromList random;
    private final Logger logger = Logger.getLogger(RaceSquadsSimple.class);

    public RaceSquadsSimple(List<Race> firstSquad,
                            List<Race> secondSquad, RandomElementFromList random) {
        this.firstSquad = firstSquad;
        this.secondSquad = secondSquad;
        this.random = random;
    }

    @Override
    public Opponents chooseOpponents(GradeChangeObservable upgradeObservable,
                                     SquadsMapper squadsMapper, StopGame stopGame) {
        final Race firstSquadRace = this.random.randomElement(this.firstSquad);
        final Race secondSquadRace = this.random.randomElement(this.secondSquad);
        this.logger.info(
                String.format(
                        "First squad races: %s. "
                                + "Second squad races: %s. "
                                + "Selected races: %s vs %s.",
                        this.firstSquad, this.secondSquad,
                        firstSquadRace, secondSquadRace
                )
        );
        return new OpponentsSimple(
                firstSquadRace, secondSquadRace,
                upgradeObservable,
                squadsMapper,
                stopGame,
                this.random
        );
    }
}
