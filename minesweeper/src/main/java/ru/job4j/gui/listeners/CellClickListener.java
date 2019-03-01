package ru.job4j.gui.listeners;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;
import ru.job4j.gui.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class CellClickListener extends MouseAdapter {
    private final Board board;

    public CellClickListener(final Board board) {
        this.board = board;
    }

    @Override
    public final void mouseClicked(MouseEvent e) {
        final Coordinate coordinate = new Coordinate(
                e.getX() / GamePanel.IMAGE_SIZE,
                e.getY() / GamePanel.IMAGE_SIZE
        );
        if (e.getButton() == MouseEvent.BUTTON1
                || e.getButton() == MouseEvent.BUTTON2) {
            this.board.open(coordinate);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.board.mark(coordinate);
        }
    }
}
