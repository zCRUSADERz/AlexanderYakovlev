package ru.job4j.gui.listeners;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Repaint.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class Repaint extends MouseAdapter {
    private final JPanel panel;
    private final MouseListener origin;

    public Repaint(final JPanel panel, final MouseListener origin) {
        this.panel = panel;
        this.origin = origin;
    }

    /**
     * При каждом клике игровое поле будет перерисованно.
     * @param e MouseEvent.
     */
    @Override
    public final void mouseReleased(final MouseEvent e) {
        this.origin.mouseReleased(e);
        this.panel.repaint();
    }
}
