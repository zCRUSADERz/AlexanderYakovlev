package ru.job4j.actions;

import ru.job4j.GameEnvironment;
import ru.job4j.xml.actions.XMLActionParser;

import javax.xml.xpath.XPath;
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
     * @param xPath XPath.
     * @param environment основные игровые объекты.
     * @return коллекцию всех парсеров действий.
     */
    Collection<XMLActionParser> allParsers(XPath xPath,
                                           GameEnvironment environment);
}
