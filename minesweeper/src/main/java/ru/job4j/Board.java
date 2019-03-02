package ru.job4j;

import ru.job4j.cells.CellType;
import ru.job4j.coordinates.Coordinate;

/**
 * Board.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.03.2019
 */
public interface Board {

    /**
     * Откроет ячейку по переданным координатам.
     * @param coordinate координаты ячейки, которую необходимо открыть.
     */
    void open(final Coordinate coordinate);

    /**
     * Поставит флажок(или уберет существующий) в ячейке по переданным координатам.
     * @param coordinate координаты ячейки, которую необходимо пометить флажком.
     */
    void mark(final Coordinate coordinate);

    /**
     * Проверит правильность установки флажка если он установлен.
     * @param coordinate координаты ячейки, которую необходимо проверить.
     */
    void check(final Coordinate coordinate);

    /**
     * Заменить состояние ячейки по переданным координатам на новое.
     * @param coordinate координаты ячейки, которую необходимо изменить.
     * @param type тип ячейки, на который будет заменен прошлый.
     */
    void replace(final Coordinate coordinate, final CellType type);
}
