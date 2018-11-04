package ru.job4j.xml.races;

import org.w3c.dom.Document;
import ru.job4j.races.Race;
import ru.job4j.xml.heroes.types.XMLHeroType;

import java.util.Set;

/**
 * RaceSquadsParser.
 * Парсер отрядов. Парсит рандомную расу из выбранного отряда.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface RaceSquadsParser {

    /**
     * Парсит рандомную расу из выбранного отряда.
     * @param document xml документ с описанием рас.
     * @param squadIndex индекс отряда из которого будет выбрана рандомная раса.
     * @param heroTypes множество всех типов героев.
     * @return рандомная раса из выбранного отряда.
     */
    Race parseRandomRace(
            Document document, int squadIndex, Set<XMLHeroType> heroTypes
    );
}
