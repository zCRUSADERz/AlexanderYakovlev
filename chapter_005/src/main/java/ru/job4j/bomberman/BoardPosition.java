package ru.job4j.bomberman;

/**
 * Местоположение на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class BoardPosition {
    private final int xCoordinate;
    private final int yCoordinate;

    public BoardPosition(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int xCoordinate() {
        return xCoordinate;
    }

    public int yCoordinate() {
        return yCoordinate;
    }

    /**
     * Вычисляет новое местоположение на основе текущего и направления движения.
     * @param direction направление движения.
     * @return новое местоположение.
     */
    public BoardPosition calculateNewPosition(DirectionOfMove direction) {
        return new BoardPosition(
                this.xCoordinate + direction.x(),
                this.yCoordinate + direction.y()
        );
    }
}
