package ru.job4j;

import ru.job4j.cells.*;
import ru.job4j.coordinates.*;
import ru.job4j.gui.GameFrame;
import ru.job4j.gui.GamePanel;
import ru.job4j.gui.listeners.*;
import ru.job4j.utils.Images;
import ru.job4j.utils.MapOf;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
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
        final int width = 100;
        final int height = 100;
        final int bombCount = 1500;
        final int imageSize = 10;
        final CellType[][] cells = new CellType[width][height];
        final AtomicBoolean gameFinished = new AtomicBoolean(false);
        final AtomicBoolean firstClick = new AtomicBoolean(true);
        final Map<String, Image> images = new Images(Arrays.asList(
                "bomb", "bombed", "closed", "flaged", "nobomb",
                "num1", "num2", "num3", "num4", "num5", "num6", "num7", "num8",
                "opened", "zero"
        )).images();
        final Runnable fillCells = () -> Arrays.stream(cells)
                .forEach(cellTypes -> Arrays.fill(
                        cellTypes, CellType.UN_OPENED
                ));
        fillCells.run();
        final BoardCoordinates boardCoordinates = new BoardCoordinates(cells);
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(boardCoordinates);
        final Bombs bombs = new Bombs(cells);
        new GameFrame(
                new GamePanel(
                        cells,
                        imageSize,
                        new BoardCoordinates(cells),
                        new MapOf<CellType, Function<Coordinate, CellImage>>(
                                new AbstractMap.SimpleEntry<>(
                                        CellType.UN_OPENED,
                                        coordinate -> (CellImage) () -> images.get("closed")
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.UN_OPENED_BOMB,
                                        coordinate ->
                                                (CellImage) () -> images.get("closed")
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.FLAG,
                                        coordinate ->
                                                (CellImage) () -> images.get("flaged")
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.BOMB_WITH_FLAG,
                                        coordinate ->
                                                (CellImage) () -> images.get("flaged")
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.EMPTY,
                                        coordinate ->
                                                (CellImage) () -> images.get("zero")
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.DANGER,
                                        coordinate -> new Danger.ImageCell(
                                                coordinate, aroundCoordinates,
                                                bombs, images
                                        )
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.BOMB,
                                        coordinate ->
                                                (CellImage) () -> images.get("bomb")
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.NO_BOMB,
                                        coordinate ->
                                                (CellImage) () -> images.get("nobomb")
                                ),
                                new AbstractMap.SimpleEntry<>(
                                        CellType.EXPLODED_BOMB,
                                        coordinate ->
                                                (CellImage) () -> images.get("bombed")
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
        final BoardCoordinates boardCoordinates = new BoardCoordinates(cells);
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(boardCoordinates);
        final Bombs bombs = new Bombs(cells);
        final UnopenedCells unopenedCells = new UnopenedCells(cells);
        final Flags flags = new Flags(cells);
        return new CellsFactory<>(
                cells,
                new MapOf<CellType, BiFunction<Board, Coordinate, OpeningCell>>(
                        new AbstractMap.SimpleEntry<>(
                                CellType.UN_OPENED,
                                (gameBoard, coordinate) -> new Unopened.Opening(
                                        coordinate, aroundCoordinates,
                                        gameBoard, bombs
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
                                (gameBoard, coordinate) -> new Empty(
                                        coordinate, aroundCoordinates,
                                        gameBoard, unopenedCells
                                )
                        ),
                        new AbstractMap.SimpleEntry<>(
                                CellType.DANGER,
                                (gameBoard, coordinate) -> new Danger.Opening(
                                        coordinate, aroundCoordinates, gameBoard,
                                        flags, bombs, unopenedCells
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
                                (gameBoard, coordinate) -> new Flag(
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
                                (gameBoard, coordinate) -> new Flag(
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
