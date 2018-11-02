package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTargetForUpgrade;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
import ru.job4j.squad.SquadsMapper;
import ru.job4j.xml.actions.utils.FindNodeByXPath;

import java.util.ArrayList;
import java.util.Collection;

/**
 * XMLUpgradeParser.
 * Парсер действия улучшить из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class XMLUpgradeParser implements XMLActionParser {
    private final static String XML_TAG_NAME = "upgrade";
    private final FindNodeByXPath findNodeByXPath;
    private final XMLDefaultActionParser defaultActionParser;
    private final SquadsMapper squads;

    public XMLUpgradeParser(FindNodeByXPath findNodeByXPath,
                            XMLDefaultActionParser defaultActionParser,
                            SquadsMapper squads) {
        this.findNodeByXPath = findNodeByXPath;
        this.defaultActionParser = defaultActionParser;
        this.squads = squads;
    }

    /**
     * Распарсить все действия улучшить из
     * предложенного документа с описанием действий.
     * @param actions xml node указывающая на первый узел в списке действий.
     * @return коллекцию действий, либо пустую коллекцию, если не найдено.
     */
    @Override
    public Collection<HeroAction> parseAll(Node actions) {
        final Collection<HeroAction> result = new ArrayList<>();
        this.findNodeByXPath
                .find(XML_TAG_NAME, actions)
                .forEach((action) -> result.add(
                        new GradeActionSimple(
                                this.defaultActionParser.parseDefaultAction(action),
                                new UpgradeAction(this.squads),
                                new RandomTargetForUpgrade(this.squads)
                        )
                ));
        return result;
    }
}
