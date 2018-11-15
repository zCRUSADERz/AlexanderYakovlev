package ru.job4j.xml.races;

import org.w3c.dom.Document;
import ru.job4j.races.Race;
import ru.job4j.squad.Squads;
import ru.job4j.xml.heroes.types.XMLHeroType;

import java.util.Set;

/**
 * SquadsParser.
 * Парсер отрядов. Парсит рандомную расу из выбранного отряда.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface SquadsParser {

    /**
     * Парсит рандомную расу из выбранного отряда.
     * @param document xml документ с описанием рас.
     * @return рандомная раса из выбранного отряда.
     */
    Squads parse(Document document);
}
