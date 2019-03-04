package ru.job4j.gui.listeners;

import ru.job4j.coordinates.Bombs;
import ru.job4j.coordinates.Flags;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * FlagsCounter.
 * Счетчик установленных флагов на поле. Результат записывается в
 * соответствующее текстовое поле.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public class FlagsCounter extends MouseAdapter {
    private final JTextField bombsField;
    private final Flags flags;
    private final Bombs bombs;
    private final MouseListener origin;

    public FlagsCounter(final JTextField bombsField, final Flags flags,
                        final Bombs bombs, final MouseListener origin) {
        this.bombsField = bombsField;
        this.flags = flags;
        this.bombs = bombs;
        this.origin = origin;
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        this.origin.mouseReleased(e);
        final int counter = this.bombs.count() - this.flags.count();
        this.bombsField.setText(Integer.toString(counter));
    }
}
