package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.Inaction;

/**
 * XMLInactionParser.
 * Парсер действия бездействовать из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class XMLInactionParser implements XMLActionParser {

    /**
     * XPath выражение для поиска узлов с действием бездействовать.
     * @return XPath выражение для поиска.
     */
    @Override
    public String xPathExpressionToFind() {
        return "inaction";
    }

    /**
     * Распарсить действие бездействовать из предложенного узла.
     * @param action узел с данными действия бездействовать.
     * @return действие бездействовать.
     */
    @Override
    public HeroAction parse(Node action) {
        return new Inaction();
    }
}
