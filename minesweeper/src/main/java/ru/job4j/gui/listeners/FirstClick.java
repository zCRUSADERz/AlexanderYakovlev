package ru.job4j.gui.listeners;

import ru.job4j.Board;
import ru.job4j.cells.CellType;
import ru.job4j.coordinates.Coordinate;
import ru.job4j.coordinates.RandomBombs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * FirstClick.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class FirstClick extends MouseAdapter {
    private final Board board;
    private final int imageSize;
    private final RandomBombs bombs;
    private final AtomicBoolean firstClick;
    private final MouseListener origin;

    public FirstClick(final Board board, final int imageSize,
                      final RandomBombs bombs,
                      final AtomicBoolean firstClick,
                      final MouseListener origin) {
        this.board = board;
        this.imageSize = imageSize;
        this.bombs = bombs;
        this.firstClick = firstClick;
        this.origin = origin;
    }

    /**
     * Если это первый клик в текущем раунде, то игровое поле будет
     * заполнено бомбами.
     * @param e MouseEvent.
     */
    @Override
    public final void mouseReleased(final MouseEvent e) {
        if (this.firstClick.get()) {
            this.firstClick.set(false);
            final Coordinate clickedCoordinate = new Coordinate(
                    e.getX() / this.imageSize,
                    e.getY() / this.imageSize
            );
            for (Coordinate coordinate : this.bombs.coordinates(clickedCoordinate)) {
                this.board.replace(coordinate, CellType.UN_OPENED_BOMB);
            }
        }
        this.origin.mouseReleased(e);
    }
}
