package ru.job4j.bomberman;

import java.util.Objects;

/**
 * Позиция на плоскости.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class Position {
    private final int xCoord;
    private final int yCoord;

    public Position(final int xCoord, final int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * Абсцисса.
     * @return абсцисса.
     */
    public int xCoord() {
        return this.xCoord;
    }

    /**
     * Ордината.
     * @return ордината.
     */
    public int yCoord() {
        return this.yCoord;
    }

    /**
     * Следущая позиция в заданном направлении.
     * @param direction направение.
     * @return следующая позиция.
     */
    public Position nextPositionInDirection(DirectionOfMove direction) {
        return new Position(
                this.xCoord + direction.x(),
                this.yCoord + direction.y()
        );
    }

    /**
     * Возвращает элемент двумерного массива используя абсциссу как первый
     * индекс, а ординату как второй индекс.
     * @param arr двумерный массив.
     * @param <T> любой тип.
     * @return элемент массива.
     */
    public <T> T getArrElement(T[][] arr) {
        return arr[this.xCoord][this.yCoord];
    }

    /**
     * Проверяет проходимо ли данное местоположение.
     * @param passablePositions проходимые местоположения.
     * @return true, если проходимое.
     */
    public boolean isPassable(PassablePositions passablePositions) {
        return passablePositions.isPassableCoordinate(this.xCoord, this.yCoord);
    }

    /**
     * Проверяет есть ли путь в данном направлении.
     * @param direction направление.
     * @param passablePositions проходимые местоположения.
     * @return true, если путь проходимый.
     */
    public boolean isPassableDirection(DirectionOfMove direction,
                                       PassablePositions passablePositions) {
        return passablePositions.isPassableCoordinate(
                this.xCoord + direction.x(),
                this.yCoord + direction.y()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return this.xCoord == position.xCoord
                && this.yCoord == position.yCoord;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.xCoord, this.yCoord);
    }
}
