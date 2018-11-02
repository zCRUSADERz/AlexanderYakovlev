package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;

import java.util.List;

/**
 * XMLActionParser.
 * Парсер конкретного действия из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public interface XMLActionParser {

    /**
     * Распарсить все выбранные действия из
     * предложенного документа с описанием действий.
     * @param actions xml node указывающая на первый узел в списке действий.
     * @return коллекцию действий, либо пустую коллекцию, если не найдено.
     */
    List<HeroAction> parseAll(Node actions);
}
