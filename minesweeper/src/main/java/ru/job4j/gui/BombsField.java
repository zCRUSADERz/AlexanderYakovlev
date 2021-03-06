package ru.job4j.gui;

import javax.swing.*;
import java.awt.*;

/**
 * BombsField.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public class BombsField extends JTextField {

    public BombsField(int bombs) {
        super(Integer.toString(bombs), 3);
    }

    public final void init() {
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        this.setBackground(Color.BLACK);
        this.setForeground(Color.RED);
        this.setEditable(false);
    }
}
