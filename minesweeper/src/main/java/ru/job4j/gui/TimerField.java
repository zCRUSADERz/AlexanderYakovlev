package ru.job4j.gui;

import javax.swing.*;
import java.awt.*;

/**
 * TimerField.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2019
 */
public class TimerField extends JTextField {

    public TimerField() {
        super("000", 3);
    }

    public void init() {
        this.setEditable(false);
        this.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        this.setBackground(Color.BLACK);
        this.setForeground(Color.RED);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
    }
}
