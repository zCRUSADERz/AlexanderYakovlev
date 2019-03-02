package ru.job4j.utils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Images.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.03.2019
 */
public class Images {
    public final Iterable<String> files;

    public Images(final Iterable<String> files) {
        this.files = files;
    }

    /**
     * Создаст карту название картинки - картинка.
     * @return картинки для ячеек.
     */
    public Map<String, Image> images() {
        final Map<String, Image> result = new HashMap<>();
        this.files.forEach(file -> result.put(
                file,
                new ImageIcon(
                        getClass().getResource(String.format("/img/%s.png", file))
                ).getImage()
        ));
        return result;
    }
}
