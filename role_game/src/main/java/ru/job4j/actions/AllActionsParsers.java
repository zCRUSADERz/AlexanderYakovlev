package ru.job4j.actions;

import ru.job4j.xml.actions.XMLActionParser;

import java.util.Collection;

/**
 * AllActionsParsers.
 * Возвращает коллекцию всех парсеров действий. По мере добавления новых
 * действий необходимо добавлять к ним соответствующие xml парсеры.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.11.2018
 */
public interface AllActionsParsers {

    /**
     * Возвращает коллекцию всех парсеров действий.
     * @return коллекцию всех парсеров действий.
     */
    Collection<XMLActionParser> parsers();
}
