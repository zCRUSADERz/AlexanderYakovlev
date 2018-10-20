package ru.job4j.races;

import ru.job4j.Stop;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.Squads;
import ru.job4j.utils.RandomElementFromList;

import java.util.Arrays;

public class RacesFactorySimple implements RacesFactory {

    @Override
    public Races createRaces(Squads squads, HealthHeroes healthHeroes,
                             AttackStrengthModifiers modifiers,
                             GradeChangeObservable gradeObservable,
                             Stop stopGame) {
        final RandomElementFromList random = new RandomElementFromList();
        return new RacesSimple(
                Arrays.asList(
                        new ElvesRaceFactory(
                                squads, healthHeroes,
                                modifiers, random
                        ).createRace(),
                        new HumansRaceFactory(
                                squads, healthHeroes,
                                modifiers, random
                        ).createRace()
                ),
                Arrays.asList(
                        new OrcsRaceFactory(
                                squads, healthHeroes,
                                modifiers, random
                        ).createRace(),
                        new UndeadRaceFactory(
                                squads, healthHeroes,
                                modifiers, random
                        ).createRace()
                ), gradeObservable,
                stopGame,
                random
        );
    }
}
