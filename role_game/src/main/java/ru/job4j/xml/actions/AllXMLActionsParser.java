package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;

import java.util.List;

/**
 * AllXMLActionsParser.
 * Парсер всех действий из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public interface AllXMLActionsParser {

    /**
     * Распарсить все действия.
     * @param actions узел с первым действием.
     * @return список всех действий героя.
     */
    List<HeroAction> parseAll(Node actions);
}
