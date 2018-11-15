package ru.job4j.xml.heroes.types;

import org.w3c.dom.Document;

import java.util.Set;

/**
 * HeroTypesParser.
 * Парсер всех типов героев.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface HeroTypesParser {

    /**
     * Парсит все типы героев.
     * @return типы героев.
     */
    Set<XMLHeroType> parse();
}
