package ru.job4j.bomberman;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Game {

    public static void main(String[] args) throws InterruptedException {
        new Game().start();
    }

    /**
     * Запуск игры на 3 секунды. Проверка работоспособности.
     * @throws InterruptedException, если поток был прерван.
     */
    public void start() throws InterruptedException {
        int sizeBoard = 10;
        String playerIdentificationString = "Player";
        int xCoordinatePlayer = 3;
        int yCoordinatePlayer = 7;
        Thread player = new Thread(
                new Player(
                        new SimpleGameBoard(
                                sizeBoard,
                                new LocationOfHeroes(
                                        addPlayer(
                                                new ConcurrentHashMap<>(),
                                                playerIdentificationString,
                                                new BoardPosition(
                                                        xCoordinatePlayer,
                                                        yCoordinatePlayer
                                                )
                                        )
                                )
                        ),
                        playerIdentificationString
                )
        );
        player.start();
        Thread.sleep(3000);
        player.interrupt();
    }

    private ConcurrentMap<String, BoardPosition> addPlayer(
            ConcurrentMap<String, BoardPosition> positions,
            String playerIdentificationString, BoardPosition playerPosition) {
        positions.put(playerIdentificationString, playerPosition);
        return positions;
    }
}
