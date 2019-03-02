package ru.job4j;

import ru.job4j.cells.*;
import ru.job4j.coordinates.*;
import ru.job4j.gui.GameFrame;
import ru.job4j.gui.GamePanel;
import ru.job4j.gui.listeners.*;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MinesweeperApp.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.03.2019
 */
public final class MinesweeperApp {

    private MinesweeperApp() {
    }

    public static void main(String[] args) {
        new MinesweeperApp().start();
    }

    /**
     * В данном методе происходит сборка всех объектов
     * в единое целое и запуск приложения.
     */
    public final void start() {
        final int width = 9;
        final int height = 9;
        final int bombCount = 10;
        final CellType[][] cells = new CellType[width][height];
        final Runnable fillCells = () -> Arrays.stream(cells)
                .forEach(cellTypes -> Arrays.fill(
                        cellTypes, CellType.UN_OPENED
                ));
        fillCells.run();
        final AtomicBoolean gameFinished = new AtomicBoolean(false);
        final AtomicBoolean firstClick = new AtomicBoolean(true);
        new GameFrame(
                new GamePanel(
                        cells,
                        new BoardCoordinates(cells),
                        Map.of(
                                CellType.UN_OPENED,
                                coordinate -> new Unopened.ImageCell(),
                                CellType.UN_OPENED_BOMB,
                                coordinate -> new Unopened.ImageCell(),
                                CellType.FLAG,
                                coordinate -> new Flag.ImageCell(),
                                CellType.BOMB_WITH_FLAG,
                                coordinate -> new Flag.ImageCell(),
                                CellType.EMPTY,
                                coordinate -> new Empty.ImageCell(),
                                CellType.DANGER,
                                coordinate -> new Danger.ImageCell(
                                        coordinate, cells
                                ),
                                CellType.BOMB,
                                coordinate -> new Bomb(),
                                CellType.NO_BOMB,
                                coordinate -> new NoBomb(),
                                CellType.EXPLODED_BOMB,
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
                                        new GameBoard(
                                                cells,
                                                openingCells(cells, gameFinished),
                                                unopenedCells(cells),
                                                checkedCells(cells)
                                        ),
                                        new BoardCoordinates(cells),
                                        new Victory(
                                                new Bombs(cells),
                                                new Flags(cells),
                                                new UnopenedCells(cells),
                                                gameFinished,
                                                new FirstClickListener(
                                                        cells,
                                                        new RandomBombs(width, height, bombCount),
                                                        firstClick,
                                                        new CellClickListener(new GameBoard(
                                                                cells,
                                                                openingCells(cells, gameFinished),
                                                                unopenedCells(cells),
                                                                checkedCells(cells)
                                                        ))
                                                )
                                        )
                                )
                        )
                )
        ).init();
    }

    /**
     * Возвращает фабрику ячеек, которые еще не были открыты.
     *
     * @param cells массив ячеек содержащий их текущее состояние.
     * @return CellsFactory<OpeningCell>
     */
    private static CellsFactory<OpeningCell> openingCells(
            final CellType[][] cells, final AtomicBoolean gameFinished) {
        return new CellsFactory<>(
                cells,
                Map.of(
                        CellType.UN_OPENED,
                        (gameBoard, coordinate) -> new Unopened.Opening(
                                coordinate, cells, gameBoard
                        ),
                        CellType.UN_OPENED_BOMB,
                        (gameBoard, coordinate) -> new UnopenedBomb.Opening(
                                coordinate, gameBoard, gameFinished
                        ),
                        CellType.EMPTY,
                        (gameBoard, coordinate) -> new Empty.Opening(
                                coordinate, gameBoard, cells
                        ),
                        CellType.DANGER,
                        (gameBoard, coordinate) -> new Danger.Opening(
                                coordinate, gameBoard, cells
                        )
                ),
                (gameBoard, coordinate) -> () -> {
                }
        );
    }

    /**
     * Возвращает фабрику ячеек, которые можно помечать флажком.
     *
     * @param cells массив ячеек содержащий их текущее состояние.
     * @return CellsFactory<UnopenedCell>
     */
    private static CellsFactory<UnopenedCell> unopenedCells(final CellType[][] cells) {
        return new CellsFactory<>(
                cells,
                Map.of(
                        CellType.UN_OPENED,
                        (gameBoard, coordinate) -> new Unopened.Marked(
                                coordinate,
                                gameBoard
                        ),
                        CellType.UN_OPENED_BOMB,
                        (gameBoard, coordinate) -> new UnopenedBomb.Marked(
                                coordinate,
                                gameBoard
                        ),
                        CellType.FLAG,
                        (gameBoard, coordinate) -> new Flag.Marked(
                                coordinate,
                                gameBoard
                        ),
                        CellType.BOMB_WITH_FLAG,
                        (gameBoard, coordinate) -> new MarkedBomb(
                                coordinate,
                                gameBoard
                        )
                ),
                (gameBoard, coordinate) -> () -> {
                }
        );
    }

    /**
     * Возвращает фабрику ячеек, которые реализуют интерфейс CheckedCell.
     *
     * @param cells массив ячеек содержащий их текущее состояние.
     * @return CellsFactory<CheckedCell>
     */
    private static CellsFactory<CheckedCell> checkedCells(final CellType[][] cells) {
        return new CellsFactory<>(
                cells,
                Map.of(
                        CellType.UN_OPENED_BOMB,
                        (gameBoard, coordinate) -> new UnopenedBomb.Checked(
                                coordinate,
                                gameBoard
                        ),
                        CellType.FLAG,
                        (gameBoard, coordinate) -> new Flag.Checked(
                                coordinate,
                                gameBoard
                        ),
                        CellType.BOMB_WITH_FLAG,
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
