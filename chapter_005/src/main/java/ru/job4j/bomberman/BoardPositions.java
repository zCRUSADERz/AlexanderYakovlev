package ru.job4j.bomberman;

import java.util.*;

/**
 * Позиции на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class BoardPositions {
    private final int boardSize;
    private Set<Position> allBoardPositions;

    public BoardPositions(final int boardSize) {
        this(boardSize, new HashSet<>());
    }

    public BoardPositions(final int boardSize, Set<Position> allBoardPositions) {
        this.boardSize = boardSize;
        this.allBoardPositions = allBoardPositions;
    }

    /**
     * Определяет находятся ли координаты на игровой доске.
     * @param x абсцисса.
     * @param y ордината.
     * @return true, если координаты указывают на игровую доску.
     */
    public boolean isCoordinateOnBoard(int x, int y) {
        return (x >= 0 && x < boardSize) && (y >= 0 && y < boardSize);
    }

    /**
     * Коллекция всех позиций находящихся на игровой доске.
     * @return коллекция позиций на игровой доске.
     */
    public Collection<Position> allBoardPositions() {
        if (this.allBoardPositions.isEmpty()) {
            initAllBoardPositions();
        }
        return this.allBoardPositions;
    }

    private void initAllBoardPositions() {
        Set<Position> result = new HashSet<>(
                this.boardSize * this.boardSize
        );
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                result.add(new Position(x, y));
            }
        }
        this.allBoardPositions = result;
    }
}
