package ru.job4j.gui;

import ru.job4j.cells.CellImage;
import ru.job4j.cells.CellType;
import ru.job4j.coordinates.BoardCoordinates;
import ru.job4j.coordinates.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.function.Function;

/**
 * BoardPanel.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.03.2019
 */
public final class BoardPanel extends JPanel {
    private final CellPanel cellPanel;

    public BoardPanel(
            final CellType[][] cells, final int imageSize,
            final BoardCoordinates boardCoordinates,
            final Map<CellType, Function<Coordinate, CellImage>> viewsFactory,
            final Function<JPanel, MouseListener> listenerFactory) {
        super(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.cellPanel = new CellPanel(
                cells, imageSize, boardCoordinates,
                viewsFactory, listenerFactory
        );
    }

    public final void init() {
        this.cellPanel.init();
        this.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createLoweredBevelBorder()
                )
        );
        this.add(this.cellPanel);
    }

    public static class CellPanel extends JPanel {
        private final CellType[][] cells;
        private final int imageSize;
        private final BoardCoordinates boardCoordinates;
        private final Map<CellType, Function<Coordinate, CellImage>> viewsFactory;
        private final Function<JPanel, MouseListener> listenerFactory;

        public CellPanel(
                final CellType[][] cells, final int imageSize,
                final BoardCoordinates boardCoordinates,
                final Map<CellType, Function<Coordinate, CellImage>> viewsFactory,
                final Function<JPanel, MouseListener> listenerFactory) {
            this.cells = cells;
            this.imageSize = imageSize;
            this.boardCoordinates = boardCoordinates;
            this.viewsFactory = viewsFactory;
            this.listenerFactory = listenerFactory;
        }

        public final void init() {
            this.setPreferredSize(
                    new Dimension(
                            this.cells.length * this.imageSize,
                            this.cells[0].length * this.imageSize
                    )
            );
            this.addMouseListener(this.listenerFactory.apply(this));
        }

        /**
         * Отрисовка игрового поля.
         * @param g Graphics.
         */
        @Override
        protected final void paintComponent(final Graphics g) {
            super.paintComponent(g);
            this.boardCoordinates.coordinates().forEach(coordinate -> {
                final CellType cellType = this.cells[coordinate.x()][coordinate.y()];
                final Image image = this.viewsFactory
                        .get(cellType)
                        .apply(coordinate)
                        .image();
                g.drawImage(
                        image,
                        coordinate.x() * this.imageSize,
                        coordinate.y() * this.imageSize,
                        this.imageSize,
                        this.imageSize,
                        this
                );
            });
        }
    }
}
