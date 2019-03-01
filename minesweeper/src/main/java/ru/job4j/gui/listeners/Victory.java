package ru.job4j.gui.listeners;

import ru.job4j.coordinates.Bombs;
import ru.job4j.coordinates.Flags;
import ru.job4j.coordinates.UnopenedCells;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Victory extends MouseAdapter {
    private final Bombs bombs;
    private final Flags flags;
    private final UnopenedCells unopenedCells;
    private final AtomicBoolean gameFinished;
    private final MouseListener origin;

    public Victory(final Bombs bombs, final Flags flags,
                   final UnopenedCells unopenedCells,
                   final AtomicBoolean gameFinished, final MouseListener origin) {
        this.bombs = bombs;
        this.flags = flags;
        this.unopenedCells = unopenedCells;
        this.gameFinished = gameFinished;
        this.origin = origin;
    }

    @Override
    public final void mouseClicked(final MouseEvent e) {
        this.origin.mouseClicked(e);
        final int bombCount = this.bombs.count();
        final int flags = this.flags.count();
        final int unopened = this.unopenedCells.count();
        if (bombCount == flags + unopened) {
            this.gameFinished.set(true);
        }
    }
}
