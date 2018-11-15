package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTargetForHero;
import ru.job4j.actions.GradeActionSimple;
import ru.job4j.observers.DegradeObserver;
import ru.job4j.observers.Observables;
import ru.job4j.xml.actions.utils.FindNodeByXPath;

import java.util.ArrayList;
import java.util.List;

/**
 * XMLDegradeParser.
 * Парсер действия снять улучшение из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class XMLDegradeParser implements XMLActionParser {
    private final static String XML_TAG_NAME = "degrade";
    private final FindNodeByXPath findNodeByXPath;
    private final XMLDefaultActionParser defaultActionParser;
    private final RandomTargetForHero target;
    private final Observables<DegradeObserver> degradeObservables;

    public XMLDegradeParser(FindNodeByXPath findNodeByXPath,
                            XMLDefaultActionParser defaultActionParser,
                            RandomTargetForHero target,
                            Observables<DegradeObserver> degradeObservables) {
        this.findNodeByXPath = findNodeByXPath;
        this.defaultActionParser = defaultActionParser;
        this.target = target;
        this.degradeObservables = degradeObservables;
    }

    /**
     * Распарсить все действия снять улучшение из
     * предложенного документа с описанием действий.
     * @param actions xml node указывающая на первый узел в списке действий.
     * @return коллекцию действий, либо пустую коллекцию, если не найдено.
     */
    @Override
    public List<HeroAction> parseAllActions(Node actions) {
        final List<HeroAction> result = new ArrayList<>();
        this.findNodeByXPath
                .findNode(XML_TAG_NAME, actions)
                .forEach((action) -> result.add(
                        new GradeActionSimple<>(
                                "Наложение проклятия (снятие улучшения с "
                                        + "персонажа противника для следующего хода)",
                                this.defaultActionParser
                                        .parseDefaultAction(action),
                                this.degradeObservables,
                                this.target
                        )
                ));
        return result;
    }
}
