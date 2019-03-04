package ru.job4j.gui.listeners;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CellClick.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class CellClick extends MouseAdapter {
    private final Board board;
    private final int imageSize;

    public CellClick(final Board board, final int imageSize) {
        this.board = board;
        this.imageSize = imageSize;
    }

    /**
     * Определяет на которую из ячеек кликнули и в зависимости от кнопки
     * выполняет действие. Левая кнопка и нажатие на колесико мыши открывает
     * ячейку. Правая кнопка устанавливает либо снимает флажок с ячейки.
     * @param e MouseEvent.
     */
    @Override
    public final void mouseReleased(final MouseEvent e) {
        final Coordinate coordinate = new Coordinate(
                e.getX() / this.imageSize,
                e.getY() / this.imageSize
        );
        if (e.getButton() == MouseEvent.BUTTON1
                || e.getButton() == MouseEvent.BUTTON2) {
            this.board.open(coordinate);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.board.mark(coordinate);
        }
    }
}
