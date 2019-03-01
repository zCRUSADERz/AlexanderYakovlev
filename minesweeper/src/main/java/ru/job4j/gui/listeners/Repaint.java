package ru.job4j.gui.listeners;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public final class Repaint extends MouseAdapter {
    private final JPanel panel;
    private final MouseListener origin;

    public Repaint(final JPanel panel, final MouseListener origin) {
        this.panel = panel;
        this.origin = origin;
    }

    @Override
    public final void mouseClicked(final MouseEvent e) {
        this.origin.mouseClicked(e);
        this.panel.repaint();
    }
}
