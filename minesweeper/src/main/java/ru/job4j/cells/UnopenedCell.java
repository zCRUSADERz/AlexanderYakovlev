package ru.job4j.cells;

/**
 * UnopenedBomb.
 * Закрытые ячейки с возможностью установки флажка.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public interface UnopenedCell {

    /**
     * Пометить ячейку флажком.
     */
    void mark();
}
