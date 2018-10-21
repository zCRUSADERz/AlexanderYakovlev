package ru.job4j.races;

import ru.job4j.StopGame;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.Opponents;
import ru.job4j.squad.SquadsMapper;

public interface RaceSquads {

    Opponents chooseOpponents(GradeChangeObservable upgradeObservable,
                              SquadsMapper squadsMapper, StopGame stopGame
    );
}
