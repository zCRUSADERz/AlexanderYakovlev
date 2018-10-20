package ru.job4j.races;

import org.apache.log4j.Logger;
import ru.job4j.Stop;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.SquadSimple;
import ru.job4j.utils.RandomElementFromList;

import java.util.List;

public class RacesSimple implements Races {
    private final List<Race> firstSquad;
    private final List<Race> secondSquad;
    private final GradeChangeObservable gradeObservable;
    private final Stop stopGame;
    private final RandomElementFromList random;
    private final Logger logger = Logger.getLogger(RacesSimple.class);

    public RacesSimple(List<Race> firstSquad, List<Race> secondSquad,
                       GradeChangeObservable gradeObservable,
                       Stop stopGame, RandomElementFromList random) {
        this.firstSquad = firstSquad;
        this.secondSquad = secondSquad;
        this.gradeObservable = gradeObservable;
        this.stopGame = stopGame;
        this.random = random;
    }

    @Override
    public void createHeroes(int magicians, int archers, int warriors) {
        final SquadHeroes squad1 = new SquadSimple(
                "Красные",
                this.gradeObservable,
                this.random,
                this.stopGame
        );
        final SquadHeroes squad2 = new SquadSimple(
                "Синие",
                this.gradeObservable,
                this.random,
                this.stopGame
        );
        final Race firstSquadRace = this.random.randomElement(this.firstSquad);
        final Race secondSquadRace = this.random.randomElement(this.secondSquad);
        this.logger.info(
                String.format(
                        "First squad races: %s. "
                                + "Second squad races: %s. "
                                + "Selected races: %s vs %s .",
                        this.firstSquad, this.secondSquad,
                        firstSquadRace, secondSquadRace
                )
        );
        firstSquadRace.createMagiciansHeroes(magicians, squad1, squad2);
        firstSquadRace.createArchersHeroes(archers, squad1, squad2);
        firstSquadRace.createWarriorsHeroes(warriors, squad1, squad2);
        secondSquadRace.createMagiciansHeroes(magicians, squad2, squad1);
        secondSquadRace.createArchersHeroes(archers, squad2, squad1);
        secondSquadRace.createWarriorsHeroes(warriors, squad2, squad1);
    }
}
