package ru.job4j.cells;

import javax.swing.*;
import java.awt.*;

/**
 * Empty.
 * Взорвавшаяся бомба.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class ExplodedBomb implements CellImage {

    /**
     * Вернет картинку взорвавшейся бомбы.
     * @return картинка взорвавшейся бомбы.
     */
    @Override
    public final Image image() {
        return new ImageIcon(
                getClass().getResource("/img/bombed.png")
        ).getImage();
    }
}
