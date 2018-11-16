package ru.job4j.bomberman;

import java.util.Collection;

/**
 * Консольное отображение игровой доски.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class GameScreen {
    private final Game game;
    private final HeroPosition playerPosition;
    private final Collection<HeroPosition> monstersPositions;
    private final int boardSize;
    private final SimplePassablePositions passablePositions;
    private String screen;

    public GameScreen(final Game game, final HeroPosition playerPosition,
                      final Collection<HeroPosition> monstersPositions, final int boardSize,
                      final SimplePassablePositions passablePositions) {
        this.game = game;
        this.playerPosition = playerPosition;
        this.monstersPositions = monstersPositions;
        this.boardSize = boardSize;
        this.passablePositions = passablePositions;
    }

    /**
     * Отображать ход игры в консоли.
     */
    public void show() {
        initScreen();
        System.out.println("Игра началась!");
        do {
            System.out.println(drawScreen());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException("Thread should not have been interrupted.", e);
            }
        } while (game.isNotFinished());
        System.out.println("Игра окончена!");
    }

    private String drawScreen() {
        StringBuilder newScreen = new StringBuilder(this.screen);
        addMonsters(newScreen);
        addPlayer(newScreen);
        return newScreen.toString();
    }

    private void initScreen() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < this.boardSize; y++) {
            addLine(builder, this.boardSize);
            for (int x = 0; x < this.boardSize; x++) {
                if (this.passablePositions.isPassableCoordinate(x, y)) {
                    addEmptyCell(builder);
                } else {
                    addWall(builder);
                }
            }
            builder.append("\n");
        }
        addLine(builder, this.boardSize);
        builder.append("\n");
        this.screen = builder.toString();
    }

    private void addLine(StringBuilder screen, int rows) {
        for (int i = 0; i < rows; i++) {
            screen.append("-----");
        }
        screen.append("\n");
    }

    private void addEmptyCell(StringBuilder screen) {
        screen.append("|   |");
    }

    private void addWall(StringBuilder screen) {
        screen.append("| X |");
    }

    private void addHero(StringBuilder screen, HeroPosition position, String name) {
        int x = position.xCoord();
        int y = position.yCoord();
        int lineCount = (y + 1) * (5 * this.boardSize + 1);
        int cellCount = y * (5 * this.boardSize + 1);
        int xCount = x * 5 + 2;
        int start = lineCount + cellCount + xCount;
        screen.replace(start, start + name.length(), name);
    }

    private void addMonsters(StringBuilder screen) {
        int i = 0;
        for (HeroPosition monster : this.monstersPositions) {
            addHero(screen, monster, "M" + i++);
        }
    }

    private void addPlayer(StringBuilder screen) {
        addHero(screen, this.playerPosition, "P");
    }
}
