package ru.job4j.races;

import ru.job4j.StopGame;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.squad.Opponents;
import ru.job4j.squad.SquadsMapper;

/**
 * Race squads.
 * Расы отрядов.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface RaceSquads {

    /**
     * Выбрать из отрядов рас две противоборствующие стороны.
     * @param upgradeObservable оповещатель изменения привилегий.
     * @param squadsMapper контроллер отрядов.
     * @param stopGame стоп игра.
     * @return две противоборствующие расы, оппоненты.
     */
    Opponents chooseOpponents(GradeChangeObservable upgradeObservable,
                              SquadsMapper squadsMapper, StopGame stopGame
    );
}
