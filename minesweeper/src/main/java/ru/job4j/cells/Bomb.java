package ru.job4j.cells;

import javax.swing.*;
import java.awt.*;

/**
 * Bomb.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class Bomb implements CellImage {

    /**
     * Вернет картинку соответствующую бомбе.
     * @return картинка бомбы.
     */
    @Override
    public final Image image() {
        return new ImageIcon(
                getClass().getResource("/img/bomb.png")
        ).getImage();
    }
}
