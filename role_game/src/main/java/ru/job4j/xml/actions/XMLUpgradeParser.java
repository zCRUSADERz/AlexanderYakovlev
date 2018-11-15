package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTargetForHero;
import ru.job4j.actions.GradeActionSimple;
import ru.job4j.observers.Observables;
import ru.job4j.observers.UpgradeObserver;
import ru.job4j.xml.actions.utils.FindNodeByXPath;

import java.util.ArrayList;
import java.util.List;

/**
 * XMLUpgradeParser.
 * Парсер действия улучшить из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class XMLUpgradeParser implements XMLActionParser {
    private final static String XML_TAG_NAME = "upgrade";
    private final FindNodeByXPath findNodeByXPath;
    private final XMLDefaultActionParser defaultActionParser;
    private final RandomTargetForHero friendlyFromRegular;
    private final Observables<UpgradeObserver> upgradeObservables;

    public XMLUpgradeParser(FindNodeByXPath findNodeByXPath,
                            XMLDefaultActionParser defaultActionParser,
                            RandomTargetForHero friendlyFromRegular,
                            Observables<UpgradeObserver> upgradeObservables) {
        this.findNodeByXPath = findNodeByXPath;
        this.defaultActionParser = defaultActionParser;
        this.friendlyFromRegular = friendlyFromRegular;
        this.upgradeObservables = upgradeObservables;
    }

    /**
     * Распарсить все действия улучшить из
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
                                "Наложение улучшения на персонажа своего отряда",
                                this.defaultActionParser.parseDefaultAction(action),
                                this.upgradeObservables,
                                this.friendlyFromRegular
                        )
                ));
        return result;
    }
}
