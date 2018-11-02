package ru.job4j.xml.races;

import org.w3c.dom.Document;
import ru.job4j.races.RaceSquads;

/**
 * RaceSquadsParserSimple.
 * Парсер отрядов. Парсит противоборствующие расы и создает два отряда.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface RaceSquadsParser {

    /**
     * Парсит расы отрядов.
     * @param document xml документ с описанием рас отрядов.
     * @return расы отрядов.
     */
    RaceSquads parseRaceSquads(Document document);
}
