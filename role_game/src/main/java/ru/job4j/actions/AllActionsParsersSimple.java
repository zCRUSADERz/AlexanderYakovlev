package ru.job4j.actions;

import ru.job4j.GameEnvironment;
import ru.job4j.xml.actions.*;
import ru.job4j.xml.actions.utils.FindNodeByXPath;
import ru.job4j.xml.actions.utils.FindNodeByXPathSimple;

import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.Collection;

/**
 * AllActionsParsers.
 * Возвращает коллекцию всех парсеров действий. По мере добавления новых
 * действий необходимо добавлять к ним соответствующие xml парсеры.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.11.2018
 */
public class AllActionsParsersSimple implements AllActionsParsers {

    /**
     * Возвращает коллекцию всех парсеров действий.
     * @param xPath XPath.
     * @param environment основные игровые объекты.
     * @return коллекцию всех парсеров действий.
     */
    @Override
    public Collection<XMLActionParser> allParsers(
            XPath xPath, GameEnvironment environment) {
        final Collection<XMLActionParser> parsers = new ArrayList<>();
        final FindNodeByXPath findNodeByXPath = new FindNodeByXPathSimple(xPath);
        final XMLDefaultActionParser defaultParser = new XMLDefaultParser(
                xPath,
                parsers
        );
        parsers.add(
                new XMLAttackParser(
                        xPath,
                        findNodeByXPath,
                        environment
                )
        );
        parsers.add(
                new XMLUpgradeParser(
                        findNodeByXPath,
                        defaultParser,
                        environment
                )
        );
        parsers.add(
                new XMLDegradeParser(
                        findNodeByXPath,
                        defaultParser,
                        environment
                )
        );
        parsers.add(
                new XMLSendAilmentParser(
                        xPath,
                        findNodeByXPath,
                        environment
                )
        );
        parsers.add(
                new XMLInactionParser(findNodeByXPath)
        );
        return parsers;
    }
}
