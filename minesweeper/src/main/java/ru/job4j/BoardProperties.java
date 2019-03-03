package ru.job4j;

/**
 * BoardProperties.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.03.2019
 */
public class BoardProperties {
    private final int width;
    private final int height;
    private final int bombs;

    public BoardProperties(final int width, final int height, final int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
    }

    public final int width() {
        return this.width;
    }

    public final int height() {
        return this.height;
    }

    public final int bombs() {
        return this.bombs;
    }
}
