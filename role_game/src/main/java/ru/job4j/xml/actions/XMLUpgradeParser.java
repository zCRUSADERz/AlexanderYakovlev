package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.GameEnvironment;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTargetForUpgrade;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
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
    private final GameEnvironment environment;

    public XMLUpgradeParser(FindNodeByXPath findNodeByXPath,
                            XMLDefaultActionParser defaultActionParser,
                            GameEnvironment environment) {
        this.findNodeByXPath = findNodeByXPath;
        this.defaultActionParser = defaultActionParser;
        this.environment = environment;
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
                        new GradeActionSimple(
                                this.defaultActionParser.parseDefaultAction(action),
                                new UpgradeAction(
                                        this.environment.getSquadsMapper()
                                ),
                                new RandomTargetForUpgrade(
                                        this.environment.getSquadsMapper()
                                )
                        )
                ));
        return result;
    }
}
