package ru.job4j.gui.listeners;

import ru.job4j.Board;
import ru.job4j.NewGame;
import ru.job4j.coordinates.BoardCoordinates;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * GameFinished.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class GameFinished extends MouseAdapter {
    private final AtomicBoolean gameFinished;
    private final NewGame newGame;
    private final Board board;
    private final BoardCoordinates boardCoordinates;
    private final MouseListener origin;

    public GameFinished(final AtomicBoolean gameFinished, final NewGame newGame,
                        final Board board, final BoardCoordinates boardCoordinates,
                        final MouseListener origin) {
        this.gameFinished = gameFinished;
        this.newGame = newGame;
        this.board = board;
        this.boardCoordinates = boardCoordinates;
        this.origin = origin;
    }

    /**
     * Если текущая игра окончена, то будет запущена новая.
     * @param e MouseEvent.
     */
    @Override
    public final void mouseClicked(final MouseEvent e) {
        if (this.gameFinished.get()) {
            this.newGame.start();
        } else {
            this.origin.mouseClicked(e);
            if (this.gameFinished.get()) {
                this.boardCoordinates.coordinates().forEach(this.board::check);
            }
        }
    }
}
