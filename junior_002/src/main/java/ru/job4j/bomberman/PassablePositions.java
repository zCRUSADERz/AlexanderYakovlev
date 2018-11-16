package ru.job4j.bomberman;

import java.util.Collection;

/**
 * Проходимые позиции на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public interface PassablePositions {

    /**
     * Проверяет проходимость местоположения по координатам.
     * @param x абсцисса.
     * @param y ордината.
     * @return true, если местоположение проходимо.
     */
    boolean isPassableCoordinate(int x, int y);

    /**
     * Все проходимые позиции на игровой доске.
     * @return коллекция проходимых местоположений.
     */
    Collection<Position> allPassablePositions();

    /**
     * Все непроходимые позиции на игровой доске.
     * @return коллекция непроходимых местоположений.
     */
    Collection<Position> impassablePositions();
}
