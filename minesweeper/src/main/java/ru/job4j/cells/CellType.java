package ru.job4j.cells;

/**
 * CellType.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public enum CellType {
    UN_OPENED,
    UN_OPENED_BOMB,
    FLAG,
    BOMB_WITH_FLAG,
    EMPTY,
    /**
     * Ячейка вокруг которой есть одна или более бомб.
     */
    DANGER,
    BOMB,
    NO_BOMB,
    EXPLODED_BOMB
}
