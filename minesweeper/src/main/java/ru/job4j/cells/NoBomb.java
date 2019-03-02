package ru.job4j.cells;

import javax.swing.*;
import java.awt.*;

/**
 * NoBomb.
 * Ошибочно установленный флажок.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class NoBomb implements CellImage {

    /**
     * Вернет картинку ячейки.
     * @return картинка ячейки.
     */
    @Override
    public final Image image() {
        return new ImageIcon(
                getClass().getResource("/img/nobomb.png")
        ).getImage();
    }
}
