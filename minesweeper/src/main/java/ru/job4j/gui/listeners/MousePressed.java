package ru.job4j.gui.listeners;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * MousePressed.
 * Отслеживает нажатие и удерживание кнопки мыши. При удержании меняет иконку
 * на кнопке рестарта.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public class MousePressed extends MouseAdapter {
    private final JButton reset;
    private final Icon pressed;
    private final Icon released;
    private final MouseListener origin;

    public MousePressed(final JButton reset, final Icon pressed,
                        final Icon released, final MouseListener origin) {
        this.reset = reset;
        this.pressed = pressed;
        this.released = released;
        this.origin = origin;
    }

    @Override
    public final void mousePressed(final MouseEvent e) {
        this.reset.setIcon(this.pressed);
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        this.reset.setIcon(this.released);
        this.origin.mouseReleased(e);
    }
}
