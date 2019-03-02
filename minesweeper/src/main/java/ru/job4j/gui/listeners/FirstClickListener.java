package ru.job4j.gui.listeners;

import ru.job4j.cells.CellType;
import ru.job4j.coordinates.Coordinate;
import ru.job4j.coordinates.RandomBombs;
import ru.job4j.gui.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicBoolean;

public final class FirstClickListener extends MouseAdapter {
    private final CellType[][] cells;
    private final RandomBombs bombs;
    private final AtomicBoolean firstClick;
    private final MouseListener origin;

    public FirstClickListener(final CellType[][] cells, final RandomBombs bombs,
                              final AtomicBoolean firstClick,
                              final MouseListener origin) {
        this.cells = cells;
        this.bombs = bombs;
        this.firstClick = firstClick;
        this.origin = origin;
    }

    @Override
    public final void mouseClicked(final MouseEvent e) {
        if (this.firstClick.get()) {
            this.firstClick.set(false);
            final Coordinate clickedCoordinate = new Coordinate(
                    e.getX() / GamePanel.IMAGE_SIZE,
                    e.getY() / GamePanel.IMAGE_SIZE
            );
            for (Coordinate coordinate : this.bombs.coordinates(clickedCoordinate)) {
                this.cells[coordinate.x()][coordinate.y()]
                        = CellType.UN_OPENED_BOMB;
            }
        }
        this.origin.mouseClicked(e);
    }
}
