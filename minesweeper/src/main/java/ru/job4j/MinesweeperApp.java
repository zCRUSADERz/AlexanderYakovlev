package ru.job4j;

import ru.job4j.cells.*;
import ru.job4j.coordinates.*;
import ru.job4j.gui.GameFrame;
import ru.job4j.gui.GamePanel;
import ru.job4j.gui.listeners.*;
import ru.job4j.utils.MapOf;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;

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
        final int imageSize = 25;
        final CellType[][] cells = new CellType[width][height];
        final AtomicBoolean gameFinished = new AtomicBoolean(false);
        final AtomicBoolean firstClick = new AtomicBoolean(true);
        final Runnable fillCells = () -> Arrays.stream(cells)
                .forEach(cellTypes -> Arrays.fill(
                        cellTypes, CellType.UN_OPENED
                ));
        fillCells.run();
        new GameFrame(
                new GamePanel(
                        cells,
                        imageSize,
                        new BoardCoordinates(cells),
                        new MapOf<CellType, Function<Coordinate, CellImage>>(
                                new AbstractMap.SimpleEntry<>(
                                        CellType.UN_OPENED,
                                        coordinate -> new Unopened.ImageCell()
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.UN_OPENED_BOMB,
                                        coordinate -> new Unopened.ImageCell()
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.FLAG,
                                        coordinate -> new Flag.ImageCell()
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.BOMB_WITH_FLAG,
                                        coordinate -> new Flag.ImageCell()
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.EMPTY,
                                        coordinate -> new Empty.ImageCell()
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.DANGER,
                                        coordinate -> new Danger.ImageCell(
                                                coordinate, cells
                                        )
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.BOMB,
                                        coordinate -> new Bomb()
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.NO_BOMB,
                                        coordinate -> new NoBomb()
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.EXPLODED_BOMB,
                                        coordinate -> new ExplodedBomb()
                                )
                        ).map(),
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
                                                new FirstClick(
                                                        new GameBoard(
                                                                cells,
                                                                openingCells(cells, gameFinished),
                                                                unopenedCells(cells),
                                                                checkedCells(cells)
                                                        ),
                                                        imageSize,
                                                        new RandomBombs(width, height, bombCount),
                                                        firstClick,
                                                        new CellClick(
                                                                new GameBoard(
                                                                        cells,
                                                                        openingCells(cells, gameFinished),
                                                                        unopenedCells(cells),
                                                                        checkedCells(cells)
                                                                ), imageSize
                                                        )
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
                new MapOf<CellType, BiFunction<Board, Coordinate, OpeningCell>>(
                        new AbstractMap.SimpleEntry<>(
                                CellType.UN_OPENED,
                                (gameBoard, coordinate) -> new Unopened.Opening(
                                        coordinate, cells, gameBoard
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.UN_OPENED_BOMB,
                                (gameBoard, coordinate) -> new UnopenedBomb.Opening(
                                        coordinate, gameBoard, gameFinished
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.EMPTY,
                                (gameBoard, coordinate) -> new Empty.Opening(
                                        coordinate, gameBoard, cells
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.DANGER,
                                (gameBoard, coordinate) -> new Danger.Opening(
                                        coordinate, gameBoard, cells
                                )
                        )
                ).map(),
                (gameBoard, coordinate) ->
                        () -> { }
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
                new MapOf<CellType, BiFunction<Board, Coordinate, UnopenedCell>>(
                        new AbstractMap.SimpleEntry<>(
                                CellType.UN_OPENED,
                                (gameBoard, coordinate) -> new Unopened.Marked(
                                        coordinate,
                                        gameBoard
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.UN_OPENED_BOMB,
                                (gameBoard, coordinate) -> new UnopenedBomb.Marked(
                                        coordinate,
                                        gameBoard
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.FLAG,
                                (gameBoard, coordinate) -> new Flag.Marked(
                                        coordinate,
                                        gameBoard
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.BOMB_WITH_FLAG,
                                (gameBoard, coordinate) -> new MarkedBomb(
                                        coordinate,
                                        gameBoard
                                )
                        )
                ).map(),
                (gameBoard, coordinate) ->
                        () -> { }
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
                new MapOf<CellType, BiFunction<Board, Coordinate, CheckedCell>>(
                        new AbstractMap.SimpleEntry<>(
                                CellType.UN_OPENED_BOMB,
                                (gameBoard, coordinate) -> new UnopenedBomb.Checked(
                                        coordinate,
                                        gameBoard
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.FLAG,
                                (gameBoard, coordinate) -> new Flag.Checked(
                                        coordinate,
                                        gameBoard
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.BOMB_WITH_FLAG,
                                (gameBoard, coordinate) -> new MarkedBomb(
                                        coordinate,
                                        gameBoard
                                )
                        )
                ).map(),
                (gameBoard, coordinate) ->
                        () -> { }
        );
    }
}
