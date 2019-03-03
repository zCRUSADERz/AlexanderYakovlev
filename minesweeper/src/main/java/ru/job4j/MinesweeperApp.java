package ru.job4j;

import ru.job4j.cells.*;
import ru.job4j.coordinates.*;
import ru.job4j.gui.GameFrame;
import ru.job4j.gui.GamePanel;
import ru.job4j.gui.listeners.*;
import ru.job4j.utils.Images;
import ru.job4j.utils.MapOf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
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
        final int width = 9;
        final int height = 9;
        final int bombCount = 10;
        final int imageSize = 25;
        final CellType[][] cells = new CellType[width][height];
        final AtomicBoolean gameFinished = new AtomicBoolean();
        final AtomicBoolean firstClick = new AtomicBoolean();
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(
                new BoardCoordinates(cells)
        );
        final Bombs bombs = new Bombs(cells);
        final Flags flags = new Flags(cells);
        //----------------------------------------------------------------------
        final Runnable fillCells = () -> Arrays.stream(cells)
                .forEach(cellTypes -> Arrays.fill(
                        cellTypes, CellType.UN_OPENED)
                );
        final Function<JPanel, NewGame> newGameFunction =
                panel -> new NewGame(Arrays.asList(
                        fillCells,
                        () -> gameFinished.set(false),
                        () -> firstClick.set(true),
                        panel::repaint
                ));
        //----------------------------------------------------------------------
        final Board gameBoard = new GameBoard(
                cells,
                openingCells(cells, gameFinished),
                unopenedCells(cells),
                checkedCells(cells)
        );
        //----------------------------------------------------------------------
        final Function<JPanel, MouseListener> panelClickListener = jPanel -> new Repaint(
                jPanel,
                new GameFinished(
                        gameFinished,
                        newGameFunction.apply(jPanel),
                        gameBoard,
                        new BoardCoordinates(cells),
                        new Victory(
                                bombs,
                                flags,
                                new UnopenedCells(cells),
                                gameFinished,
                                new FirstClick(
                                        gameBoard,
                                        imageSize,
                                        new RandomBombs(width, height, bombCount),
                                        firstClick,
                                        new CellClick(gameBoard, imageSize)
                                )
                        )
                )
        );
        //----------------------------------------------------------------------
        final Map<String, Image> images = new Images(Arrays.asList(
                "bomb", "bombed", "closed", "flaged", "nobomb",
                "num1", "num2", "num3", "num4", "num5", "num6", "num7", "num8",
                "opened", "zero"
        )).images();
        final GamePanel cellPanel = new GamePanel(
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
                panelClickListener

        );
        //----------------------------------------------------------------------
        final NewGame newGame = newGameFunction.apply(cellPanel);
        //----------------------------------------------------------------------
        final JMenuBar menu = new JMenuBar();
        final JMenu game = menu.add(new JMenu("Игра"));
        final JMenuItem newGameItem = game.add("Начать новую игру");
        newGameItem.addActionListener(event -> newGame.start());
        game.addSeparator();
        final JMenuItem beginner = game.add("Новичок");
        final JMenuItem intermediate = game.add("Любитель");
        final JMenuItem expert = game.add("Профессионал");
        final JMenuItem custom = game.add("Особый");
        game.addSeparator();
        final JMenuItem exit = game.add("Выход");
        exit.addActionListener(event -> System.exit(0));
        //----------------------------------------------------------------------
        newGame.start();
        new GameFrame(cellPanel, menu).init();
    }

    /**
     * Возвращает фабрику ячеек, которые еще не были открыты.
     *
     * @param cells массив ячеек содержащий их текущее состояние.
     * @return CellsFactory<OpeningCell>
     */
    private static CellsFactory<OpeningCell> openingCells(
            final CellType[][] cells, final AtomicBoolean gameFinished) {
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(
                new BoardCoordinates(cells)
        );
        final Bombs bombs = new Bombs(cells);
        final UnopenedCells unopenedCells = new UnopenedCells(cells);
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
                                        new Flags(cells), bombs, unopenedCells
                                )
                        )
                ).map(),
                (gameBoard, coordinate) ->
                        () -> {
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
                        () -> {
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
                        () -> {
                        }
        );
    }
}
