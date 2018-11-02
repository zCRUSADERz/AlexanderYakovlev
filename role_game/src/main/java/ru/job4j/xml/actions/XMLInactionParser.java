package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.Inaction;
import ru.job4j.xml.actions.utils.FindNodeByXPath;

import java.util.ArrayList;
import java.util.List;

/**
 * XMLInactionParser.
 * Парсер действия бездействовать из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class XMLInactionParser implements XMLActionParser {
    private final static String XML_TAG_NAME = "inaction";
    private final FindNodeByXPath findNodeByXPath;

    public XMLInactionParser(FindNodeByXPath findNodeByXPath) {
        this.findNodeByXPath = findNodeByXPath;
    }

    /**
     * Распарсить все действия бездействовать из
     * предложенного документа с описанием действий.
     * @param actions xml node указывающая на первый узел в списке действий.
     * @return коллекцию действий, либо пустую коллекцию, если не найдено.
     */
    @Override
    public List<HeroAction> parseAllActions(Node actions) {
        final List<HeroAction> result = new ArrayList<>();
        this.findNodeByXPath
                .findNode(XML_TAG_NAME, actions)
                .forEach((action) -> result.add(new Inaction()));
        return result;
    }
}
