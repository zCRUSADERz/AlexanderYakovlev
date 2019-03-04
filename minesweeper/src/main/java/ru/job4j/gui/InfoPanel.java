package ru.job4j.gui;

import javax.swing.*;
import java.awt.*;

/**
 * InfoPanel.
 * Информационная панель с двумя текстовыми полями(прошедшее время с начала
 * раунда, необходимое количество флажков для покрытия всех бомб), и кнопкой
 * рестарта раунда.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2019
 */
public class InfoPanel extends JPanel {
    private final TimerField timeField;
    private final ResetButton reset;
    private final BombsField bombsField;

    public InfoPanel(final TimerField timeField, final ResetButton reset,
                     final BombsField bombsField) {
        super(new BorderLayout());
        this.timeField = timeField;
        this.reset = reset;
        this.bombsField = bombsField;
    }

    public final void init(final JPanel panel) {
        this.timeField.init();
        this.reset.init(panel);
        this.bombsField.init();
        this.add(timeField, BorderLayout.WEST);
        this.add(reset, BorderLayout.CENTER);
        this.add(bombsField, BorderLayout.EAST);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
    }
}
