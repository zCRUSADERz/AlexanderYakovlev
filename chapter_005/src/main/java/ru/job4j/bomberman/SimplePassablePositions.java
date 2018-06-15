package ru.job4j.bomberman;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Проходимые позиции на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class SimplePassablePositions implements PassablePositions {
    private final BoardPositions boardPositions;
    private Collection<Position> allPassablePositions;
    private Collection<Position> allImpassablePositions;

    public SimplePassablePositions(final BoardPositions boardPositions) {
        this.boardPositions = boardPositions;
        this.allPassablePositions = new ArrayList<>();
        this.allImpassablePositions = new ArrayList<>();
    }

    /**
     * Проверяет проходимость местоположения по координатам.
     * @param x абсцисса.
     * @param y ордината.
     * @return true, если местоположение проходимо.
     */
    public boolean isPassableCoordinate(int x, int y) {
        boolean result = false;
        if (!(((x % 2) != 0) && ((y % 2) != 0))) {
            if (boardPositions.isCoordinateOnBoard(x, y)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Все проходимые позиции на игровой доске.
     * @return коллекция проходимых местоположений.
     */
    public Collection<Position> allPassablePositions() {
        if (this.allPassablePositions.isEmpty()) {
            init();
        }
        return this.allPassablePositions;
    }

    /**
     * Все непроходимые позиции на игровой доске.
     * @return коллекция непроходимых местоположений.
     */
    public Collection<Position> impassablePositions() {
        if (this.allImpassablePositions.isEmpty()) {
            init();
        }
        return this.allImpassablePositions;
    }

    private void init() {
        Collection<Position> passable = new ArrayList<>();
        Collection<Position> impassable = new ArrayList<>();
        Collection<Position> allBoardPositions = this.boardPositions.allBoardPositions();
        for (Position pos : allBoardPositions) {
            if (pos.isPassable(this)) {
                passable.add(pos);
            } else {
                impassable.add(pos);
            }
        }
        this.allPassablePositions = passable;
        this.allImpassablePositions = impassable;
    }
}
