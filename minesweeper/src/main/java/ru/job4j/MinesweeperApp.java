package ru.job4j;

import ru.job4j.cells.*;
import ru.job4j.coordinates.*;
import ru.job4j.gui.*;
import ru.job4j.gui.listeners.*;
import ru.job4j.utils.Images;
import ru.job4j.utils.MapOf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
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
        new GameFrame(
                new BoardProperties(9, 9, 10),
                panel(),
                menu()
        ).init();
    }

    /**
     * Сборка меню для основного фрейма игры.
     * Изменяющееся состояние для меню - boardPropertiesHolder хранящее
     * в себе прошлые настройки игрового поля. Все остальные объекты Immutable.
     *
     * @return функцию для создания объекта JMenuBar.
     */
    private static BiFunction<GameFrame, BoardProperties, JMenuBar> menu() {
        return (gameFrame, boardProperties) -> {
            final AtomicReference<BoardProperties> boardPropertiesHolder
                    = new AtomicReference<>(boardProperties);
            final JMenuBar menu = new JMenuBar();
            final JMenu game = menu.add(new JMenu("Игра"));
            final Function<BoardProperties, ActionListener> actionListenerFactory
                    = properties -> (ActionListener) e -> {
                boardPropertiesHolder.set(properties);
                gameFrame.update(properties);
            };
            game
                    .add("Начать новую игру")
                    .addActionListener(event -> gameFrame.update(
                            boardPropertiesHolder.get()
                    ));
            game.addSeparator();
            game
                    .add("Новичок")
                    .addActionListener(actionListenerFactory.apply(
                            new BoardProperties(9, 9, 10))
                    );
            game
                    .add("Любитель")
                    .addActionListener(actionListenerFactory.apply(
                            new BoardProperties(16, 16, 40))
                    );
            game
                    .add("Профессионал")
                    .addActionListener(actionListenerFactory.apply(
                            new BoardProperties(30, 16, 99))
                    );
            game
                    .add("Особый")
                    .addActionListener(event ->
                            new BoardOptions(
                                    boardProperties,
                                    properties -> {
                                        boardPropertiesHolder.set(properties);
                                        gameFrame.update(properties);
                                    }
                            ).init()
                    );
            game.addSeparator();
            game
                    .add("Выход")
                    .addActionListener(event -> System.exit(0));
            return menu;
        };
    }

    /**
     * Сборка объекта JPanel содержащего панель с информацией(таймер,
     * кнопка рестарта, счетчик флажков) и панель с игровым полем.
     * Изменяемое состояние.
     * 1. Двумерный массив CellType[][], содержащий текущее состояние каждой ячейки.
     * 2. AtomicBoolean gameFinished. Значение true означает,
     * что текущий раунд окончен.
     * 3. AtomicBoolean firstClick. Значение true означает, что в текущем
     * раунде все поле закрыто и бомбы не раставлены по игровому полю.
     * 4. TimerField - текстовое поле показывающее сколько времени в секундах
     * прошло с момента старта текущего раунда.
     * 5. BombsField - текстовое поле показывающее сколько еще флажков
     * нужно установить, чтобы покрыть все бомбы.
     * 6. Reset - клик по этой кнопке начинает новый раунд. Иконка при нажатии
     * и отпускании кнопки меняется.
     * @return функцию принимающую параметры игровой доски и возвращающую
     * игровую доску с этими параметрами.
     */
    private static Function<BoardProperties, GamePanel> panel() {
        return boardProperties -> {
            final int width = boardProperties.width();
            final int height = boardProperties.height();
            final int bombCount = boardProperties.bombs();
            final int imageSize = 25;
            final Icon okIcon = new ImageIcon(
                    MinesweeperApp.class.getResource(
                            "/img/new game.gif"
                    )
            );
            //------------------------------------------------------------------
            final CellType[][] cells = new CellType[width][height];
            final AtomicBoolean gameFinished = new AtomicBoolean();
            final AtomicBoolean firstClick = new AtomicBoolean();
            final TimerField timeField = new TimerField();
            final BombsField bombsField = new BombsField(bombCount);
            final AtomicLong startTime = new AtomicLong();
            //------------------------------------------------------------------
            final Function<JPanel, NewGame> newGameFactory =
                    panel -> new NewGame(Arrays.asList(
                            () -> Arrays.stream(cells)
                                    .forEach(cellTypes -> Arrays.fill(
                                            cellTypes, CellType.UN_OPENED)
                                    ),
                            () -> gameFinished.set(false),
                            () -> firstClick.set(true),
                            () -> bombsField.setText(Integer.toString(bombCount)),
                            () -> startTime.set(System.currentTimeMillis()),
                            () -> timeField.setText("000"),
                            panel::repaint
                    ));
            final ResetButton reset = new ResetButton(okIcon, newGameFactory);
            final Board gameBoard = new GameBoard(
                    cells,
                    openingCells(cells, gameFinished, reset, boardProperties),
                    unopenedCells(cells),
                    checkedCells(cells)
            );
            final Bombs bombs = new Bombs(cells, bombCount);
            final Flags flags = new Flags(cells);
            final Function<JPanel, MouseListener> mouseListenerFactory
                    = jPanel -> new MousePressed(
                    reset,
                    new ImageIcon(
                            MinesweeperApp.class.getResource("/img/press.gif")
                    ),
                    new ImageIcon(
                            MinesweeperApp.class.getResource("/img/new game.gif")
                    ),
                    new FlagsCounter(
                            bombsField,
                            flags,
                            bombs,
                            new Repaint(
                                    jPanel,
                                    new GameFinished(
                                            gameFinished,
                                            newGameFactory.apply(jPanel),
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
                            )
                    )
            );
            final GamePanel gamePanel = new GamePanel(
                    new InfoPanel(
                            timeField,
                            reset,
                            bombsField
                    ),
                    new BoardPanel(
                            cells,
                            imageSize,
                            new BoardCoordinates(cells),
                            cellImageFactory(cells, boardProperties),
                            mouseListenerFactory
                    ),
                    newGameFactory,
                    new Thread(
                            new Timer(
                                    timeField,
                                    startTime,
                                    gameFinished
                            )
                    )
            );
            gamePanel.init();
            return gamePanel;
        };
    }

    /**
     * Возвращает фабрику ячеек, которые еще не были открыты.
     *
     * @param cells массив ячеек содержащий их текущее состояние.
     * @return CellsFactory<OpeningCell>
     */
    private static CellsFactory<OpeningCell> openingCells(
            final CellType[][] cells, final AtomicBoolean gameFinished,
            final JButton reset, final BoardProperties boardProperties) {
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(
                new BoardCoordinates(cells)
        );
        final Bombs bombs = new Bombs(cells, boardProperties.bombs());
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
                                        coordinate, gameBoard, gameFinished,
                                        reset,
                                        new ImageIcon(
                                                MinesweeperApp.class.getResource(
                                                        "/img/crape.gif"
                                                )
                                        )
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
                        )
                ).map(),
                (gameBoard, coordinate) -> () -> {
                }
        );
    }

    /**
     * Возвращает карту, в которой каждому типу ячейки присвоена
     * функция(фабрика) возвращающая реализацию интерфейса CellImage.
     *
     * @param cells массив ячеек содержащий их текущее состояние.
     * @return карту CellType -> фабрика CellImage.
     */
    private static Map<CellType, Function<Coordinate, CellImage>> cellImageFactory(
            final CellType[][] cells, final BoardProperties boardProperties) {
        final Map<String, Image> images = new Images(Arrays.asList(
                "bomb", "bombed", "closed", "flaged", "nobomb",
                "num1", "num2", "num3", "num4", "num5", "num6", "num7", "num8",
                "opened", "zero"
        )).images();
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(
                new BoardCoordinates(cells)
        );
        final Bombs bombs = new Bombs(cells, boardProperties.bombs());
        return new MapOf<CellType, Function<Coordinate, CellImage>>(
                new AbstractMap.SimpleEntry<>(
                        CellType.UN_OPENED,
                        coordinate -> (CellImage) () -> images.get("closed")
                ),
                new AbstractMap.SimpleEntry<>(
                        CellType.UN_OPENED_BOMB,
                        coordinate -> (CellImage) () -> images.get("closed")
                ),
                new AbstractMap.SimpleEntry<>(
                        CellType.FLAG,
                        coordinate -> (CellImage) () -> images.get("flaged")
                ),
                new AbstractMap.SimpleEntry<>(
                        CellType.BOMB_WITH_FLAG,
                        coordinate -> (CellImage) () -> images.get("flaged")
                ),
                new AbstractMap.SimpleEntry<>(
                        CellType.EMPTY,
                        coordinate -> (CellImage) () -> images.get("zero")
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
                        coordinate -> (CellImage) () -> images.get("bomb")
                ),
                new AbstractMap.SimpleEntry<>(
                        CellType.NO_BOMB,
                        coordinate -> (CellImage) () -> images.get("nobomb")
                ),
                new AbstractMap.SimpleEntry<>(
                        CellType.EXPLODED_BOMB,
                        coordinate -> (CellImage) () -> images.get("bombed")
                )
        ).map();
    }
}
