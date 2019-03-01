package ru.job4j;

import ru.job4j.cells.*;
import ru.job4j.coordinates.*;
import ru.job4j.gui.GameFrame;
import ru.job4j.gui.GamePanel;
import ru.job4j.gui.listeners.*;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MinesweeperApp {

    private MinesweeperApp() {
    }

    public static void main(String[] args) {
        new MinesweeperApp().start();
    }

    public final void start() {
        final int width = 9;
        final int height = 9;
        final int bombCount = 10;
        final CellTypes[][] cells = new CellTypes[width][height];
        final Runnable fillCells = () -> Arrays.stream(cells)
                .forEach(cellTypes -> Arrays.fill(
                        cellTypes, CellTypes.UN_OPENED
                ));
        fillCells.run();
        final AtomicBoolean gameFinished = new AtomicBoolean(false);
        final AtomicBoolean firstClick = new AtomicBoolean(true);
        final Board board = new GameBoard(
                cells,
                openingCells(cells, gameFinished),
                unopenedCells(cells),
                checkedCells(cells)
        );
        final BoardCoordinates boardCoordinates = new BoardCoordinates(cells);
        new GameFrame(
                new GamePanel(
                        cells,
                        boardCoordinates,
                        Map.of(
                                CellTypes.UN_OPENED,
                                coordinate -> new Unopened.ImageCell(),
                                CellTypes.UN_OPENED_BOMB,
                                coordinate -> new Unopened.ImageCell(),
                                CellTypes.FLAG,
                                coordinate -> new Flag.ImageCell(),
                                CellTypes.BOMB_WITH_FLAG,
                                coordinate -> new Flag.ImageCell(),
                                CellTypes.EMPTY,
                                coordinate -> new Empty.ImageCell(),
                                CellTypes.DANGER,
                                coordinate -> new Danger.ImageCell(
                                        coordinate, cells
                                ),
                                CellTypes.BOMB,
                                coordinate -> new Bomb(),
                                CellTypes.NO_BOMB,
                                coordinate -> new NoBomb(),
                                CellTypes.EXPLODED_BOMB,
                                coordinate -> new ExplodedBomb()
                        ),
                        jPanel -> new Repaint(
                                jPanel,
                                new GameFinished(
                                        gameFinished,
                                        new NewGame(Arrays.asList(
                                                fillCells,
                                                () -> gameFinished.set(false),
                                                () -> firstClick.set(true)
                                        )),
                                        board,
                                        boardCoordinates,
                                        new Victory(
                                                new Bombs(cells),
                                                new Flags(cells),
                                                new UnopenedCells(cells),
                                                gameFinished,
                                                new FirstClickListener(
                                                        cells,
                                                        new RandomBombs(width, height, bombCount),
                                                        firstClick,
                                                        new CellClickListener(board)
                                                )
                                        )
                                )
                        )
                )
        ).init();
    }

    private static Cells<OpeningCell> openingCells(
            final CellTypes[][] cells, final AtomicBoolean gameFinished) {
        return new Cells<>(
                cells,
                Map.of(
                        CellTypes.UN_OPENED,
                        (gameBoard, coordinate) -> new Unopened.Opening(
                                coordinate, cells, gameBoard
                        ),
                        CellTypes.UN_OPENED_BOMB,
                        (gameBoard, coordinate) -> new UnopenedBomb.Opening(
                                coordinate, gameBoard, gameFinished
                        ),
                        CellTypes.EMPTY,
                        (gameBoard, coordinate) -> new Empty.Opening(
                                coordinate, gameBoard, cells
                        ),
                        CellTypes.DANGER,
                        (gameBoard, coordinate) -> new Danger.Opening(
                                coordinate, gameBoard, cells
                        )
                ),
                (gameBoard, coordinate) -> () -> {
                }
        );
    }

    private static Cells<UnopenedCell> unopenedCells(final CellTypes[][] cells) {
        return new Cells<>(
                cells,
                Map.of(
                        CellTypes.UN_OPENED,
                        (gameBoard, coordinate) -> new Unopened.Marked(
                                coordinate,
                                gameBoard
                        ),
                        CellTypes.UN_OPENED_BOMB,
                        (gameBoard, coordinate) -> new UnopenedBomb.Marked(
                                coordinate,
                                gameBoard
                        ),
                        CellTypes.FLAG,
                        (gameBoard, coordinate) -> new Flag.Marked(
                                coordinate,
                                gameBoard
                        ),
                        CellTypes.BOMB_WITH_FLAG,
                        (gameBoard, coordinate) -> new MarkedBomb(
                                coordinate,
                                gameBoard
                        )
                ),
                (gameBoard, coordinate) -> () -> {
                }
        );
    }

    private static Cells<CheckedCell> checkedCells(final CellTypes[][] cells) {
        return new Cells<>(
                cells,
                Map.of(
                        CellTypes.UN_OPENED_BOMB,
                        (gameBoard, coordinate) -> new UnopenedBomb.Checked(
                                coordinate,
                                gameBoard
                        ),
                        CellTypes.FLAG,
                        (gameBoard, coordinate) -> new Flag.Checked(
                                coordinate,
                                gameBoard
                        ),
                        CellTypes.BOMB_WITH_FLAG,
                        (gameBoard, coordinate) -> new MarkedBomb(
                                coordinate,
                                gameBoard
                        )
                ),
                (gameBoard, coordinate) -> () -> {
                }
        );
    }
}
