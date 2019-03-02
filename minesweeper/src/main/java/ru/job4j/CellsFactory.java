package ru.job4j;

import ru.job4j.cells.CellType;
import ru.job4j.coordinates.Coordinate;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * CellsFactory.
 * Фабрика объектов ячеек, реализующих определенный интерфейс
 * относящийся к ячейкам.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.03.2019
 */
public final class CellsFactory<T> {
    /**
     * Массив ячеек с текущим типом.
     */
    private final CellType[][] cells;
    /**
     * Карта определяющая для каждого типа свою фабрику.
     */
    private final Map<CellType, BiFunction<Board, Coordinate,  T>> cellFactory;
    /**
     * Универсальная фабрика для типов
     * не имеющих своей уникальной реализации интерфейса.
     */
    private final BiFunction<Board, Coordinate, T> defaultFactory;

    public CellsFactory(
            final CellType[][] cells,
            final Map<CellType, BiFunction<Board, Coordinate, T>> cellFactory,
            final BiFunction<Board, Coordinate, T> defaultFactory) {
        this.cells = cells;
        this.cellFactory = cellFactory;
        this.defaultFactory = defaultFactory;
    }

    /**
     * Вернет реализацию интерфейса T, созданную фабрикой
     * для ячейки с переданными координатами.
     * @param board board.
     * @param coordinate координаты необходимой ячейки.
     * @return реализация интерфейса T, для ячейки с переданными координатами.
     */
    public final T cell(final Board board, final Coordinate coordinate) {
        return this.cellFactory.getOrDefault(
                this.cells[coordinate.x()][coordinate.y()],
                this.defaultFactory
        ).apply(board, coordinate);
    }
}
