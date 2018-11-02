package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;

/**
 * XMLDefaultActionParser.
 * Парсер действия по умолчанию, для конкретного действия.
 * Действие по умолчанию должно быть обязательно одно.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface XMLDefaultActionParser {

    /**
     * Распарсить дейстие по умолчанию.
     * @param actions узел конкретного действия.
     * @return действие по умолчанию.
     */
    HeroAction parseDefaultAction(Node actions);
}
