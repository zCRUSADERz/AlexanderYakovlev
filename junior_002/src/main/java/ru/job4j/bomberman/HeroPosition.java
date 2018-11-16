package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;

/**
 * Местоположение героя на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class HeroPosition {
    private volatile Position position;
    private final GameBoard board;
    private final PassablePositions passablePositions;

    public HeroPosition(final Position position, final GameBoard board,
                        final PassablePositions passablePositions) {
        this.position = position;
        this.board = board;
        this.passablePositions = passablePositions;
    }

    /**
     * Х координата местоположения.
     * @return Х - координата.
     */
    public int xCoord() {
        return this.position.xCoord();
    }

    /**
     * Y координата местоположения.
     * @return Y - координата.
     */
    public int yCoord() {
        return this.position.yCoord();
    }

    /**
     * Двигаться в направлении.
     * @param direction направление движения.
     * @param timeout длительность попытки.
     * @param unit единица измерения времени.
     * @return true, если перейти в новое местоположенине удалось.
     * @throws InterruptedException если поток был прерван.
     */
    public boolean moveInDirection(DirectionOfMove direction, long timeout, TimeUnit unit) throws InterruptedException {
        boolean result = false;
        if (this.position.isPassableDirection(direction, this.passablePositions)) {
            Position target = this.position.nextPositionInDirection(direction);
            if (this.board.move(this.position, target, timeout, unit)) {
                this.position = target;
                result = true;
            }
        }
        return result;
    }

    /**
     * Проверяет находится ли другой герой в следующей позиции по заданному направлению.
     * @param direction направление движения.
     * @param otherHero другой герой.
     * @return true, если герой найден.
     */
    public boolean checkOtherHeroInDirection(DirectionOfMove direction, HeroPosition otherHero) {
        Position target = this.position.nextPositionInDirection(direction);
        return otherHero.isHeroPosition(target);
    }

    /**
     * Инициализировать стартовую позицию на игровой доске.
     */
    public void initStartPosition() {
        this.board.occupyCell(this.position);
    }

    /**
     * Проверяет текущее местоположение с переданным в параметре.
     * @param otherPosition другое местоположение.
     * @return true, если местоположения совпадают.
     */
    private boolean isHeroPosition(Position otherPosition) {
        return this.position.equals(otherPosition);
    }
}
