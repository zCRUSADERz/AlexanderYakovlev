package ru.job4j.cells;

import javax.swing.*;
import java.awt.*;

/**
 * Bishop figure.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public final class Bomb implements CellImage {

    @Override
    public final Image image() {
        return new ImageIcon(
                getClass().getResource("/img/bomb.png")
        ).getImage();
    }
}
