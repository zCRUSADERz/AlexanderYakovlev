package ru.job4j.xml.races;

import org.w3c.dom.Node;
import ru.job4j.races.Race;
import ru.job4j.xml.heroes.types.XMLHeroType;

import java.util.Set;

/**
 * XMLRaceParser.
 * Парсер рас из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface XMLRaceParser {

    /**
     * Парсит данные расы из xml документа.
     * @param nodeRace xml узел c данными расы.
     * @param types множество всех типов героев.
     * @return расу.
     */
    Race parseRace(Node nodeRace, Set<XMLHeroType> types);
}
