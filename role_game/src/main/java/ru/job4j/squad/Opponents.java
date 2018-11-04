package ru.job4j.squad;

import org.w3c.dom.Document;


/**
 * Opponents.
 * Противоборствующие стороны. Две расы воюющих друг с другом.
 * Класс отвечает за создание противоборствующих отрядов и героев в них.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface Opponents {

    /**
     * Создать противоборствующие отряды, подготовить к бою.
     * @param document xml документ с описанием отрядов.
     */
    void createSquads(Document document);
}
