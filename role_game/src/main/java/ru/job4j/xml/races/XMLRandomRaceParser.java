package ru.job4j.xml.races;

import org.w3c.dom.Node;
import ru.job4j.races.Race;
import ru.job4j.xml.heroes.types.XMLHeroType;

import java.util.Set;

/**
 * XMLRandomRaceParser.
 * Парсер рандомной расы из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface XMLRandomRaceParser {

    /**
     * Парсит равндомную расу из xml документа.
     * @param squad xml узел c данными расы.
     * @return расу.
     */
    Race parse(Node squad);
}
