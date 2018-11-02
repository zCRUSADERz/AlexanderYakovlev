package ru.job4j.xml.heroes;

import org.w3c.dom.Node;
import ru.job4j.heroes.HeroFactory;

/**
 * XMLHeroParser.
 * Парсер героев из xml документа.
 * Возвращает фабрику для героев определенной расы и типа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface XMLHeroParser {

    /**
     * Парсит данные героя и возвращает фабрику героев данной расы и типа.
     * @param heroNode узел содержащий данные конкретного героя.
     * @return фабрику героев определенной расы и типа.
     */
    HeroFactory parseHero(Node heroNode);
}
