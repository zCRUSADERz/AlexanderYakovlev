package ru.job4j.xml.heroes;

import org.w3c.dom.Document;
import ru.job4j.xml.heroes.types.XMLHeroType;

import java.util.Map;
import java.util.Set;

/**
 * NumberOfHeroesParser.
 * Парсер количества героев каждого типа в отряде.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface NumberOfHeroesParser {

    /**
     * Возвращает словарь, в котором каждому типу героя
     * соответствует количество героев в отряде данного типа.
     * @param document xml документ с описанием размера отряда.
     * @return словарь, в котором каждому типу героя
     * соответствует количество героев в отряде данного типа.
     */
    Map<XMLHeroType, Integer> parseNumberOfHeroes(
            Document document, Set<XMLHeroType> heroTypes);
}