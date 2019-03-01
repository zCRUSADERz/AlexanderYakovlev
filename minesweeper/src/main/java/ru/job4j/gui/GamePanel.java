package ru.job4j.gui;

import ru.job4j.cells.CellImage;
import ru.job4j.cells.CellTypes;
import ru.job4j.coordinates.BoardCoordinates;
import ru.job4j.coordinates.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.function.Function;

public final class GamePanel extends JPanel {
    public final static int IMAGE_SIZE = 25;
    private final CellTypes[][] cells;
    private final BoardCoordinates boardCoordinates;
    private final Map<CellTypes, Function<Coordinate, CellImage>> viewsFactory;
    private final Function<JPanel, MouseListener> listenerFactory;

    public GamePanel(
            final CellTypes[][] cells, final BoardCoordinates boardCoordinates,
            final Map<CellTypes, Function<Coordinate, CellImage>> viewsFactory,
            final Function<JPanel, MouseListener> listenerFactory) {
        this.cells = cells;
        this.boardCoordinates = boardCoordinates;
        this.viewsFactory = viewsFactory;
        this.listenerFactory = listenerFactory;
    }

    public final void init() {
        this.setPreferredSize(
                new Dimension(
                        this.cells.length * IMAGE_SIZE,
                        this.cells[0].length * IMAGE_SIZE
                )
        );
        this.addMouseListener(this.listenerFactory.apply(this));
    }

    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.boardCoordinates.coordinates().forEach(coordinate -> {
            final CellTypes cellType = this.cells[coordinate.x()][coordinate.y()];
            final Image image = this.viewsFactory
                    .get(cellType)
                    .apply(coordinate)
                    .image();
            g.drawImage(
                    image,
                    coordinate.x() * IMAGE_SIZE,
                    coordinate.y() * IMAGE_SIZE,
                    IMAGE_SIZE,
                    IMAGE_SIZE,
                    this
            );
        });
    }
}
