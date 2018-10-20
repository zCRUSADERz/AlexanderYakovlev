package ru.job4j.races;

import ru.job4j.Stop;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.Squads;

public interface RacesFactory {

    Races createRaces(Squads squads, HealthHeroes healthHeroes,
                      AttackStrengthModifiers modifiers,
                      GradeChangeObservable gradeObservable,
                      Stop stopGame);
}
