package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;

/**
 * XMLActionParser.
 * Парсер действия из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public interface XMLActionParser {

    /**
     * XPath выражение для поиска узлов с действием.
     * @return XPath выражение для поиска.
     */
    String xPathExpressionToFind();

    /**
     * Распарсить действие из предложенного узла.
     * @param action узел с данными действия.
     * @return действие.
     */
    HeroAction parse(Node action);
}
