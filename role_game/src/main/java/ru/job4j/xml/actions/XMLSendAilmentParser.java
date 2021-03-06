package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.SendAilment;
import ru.job4j.actions.actiontarget.RandomTargetForHero;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.xml.actions.utils.FindNodeByXPath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

/**
 * XMLSendAilmentParser.
 * Парсер действия наслать недуг из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class XMLSendAilmentParser implements XMLActionParser {
    private final static String XML_TAG_NAME = "sendAilment";
    private final XPath xPath;
    private final FindNodeByXPath findNodeByXPath;
    private final RandomTargetForHero target;
    private final AttackStrengthModifiers attackModifiers;

    public XMLSendAilmentParser(XPath xPath,
                                FindNodeByXPath findNodeByXPath,
                                RandomTargetForHero target,
                                AttackStrengthModifiers attackModifiers) {
        this.xPath = xPath;
        this.findNodeByXPath = findNodeByXPath;
        this.target = target;
        this.attackModifiers = attackModifiers;
    }


    /**
     * Распарсить все действия наслать недуг из
     * предложенного документа с описанием действий.
     * @param actions xml node указывающая на первый узел в списке действий.
     * @return коллекцию действий, либо пустую коллекцию, если не найдено.
     */
    @Override
    public List<HeroAction> parseAllActions(Node actions) {
        final List<HeroAction> result = new ArrayList<>();
        this.findNodeByXPath
                .findNode(XML_TAG_NAME, actions)
                .forEach((action) -> {
                    try {
                        final double modifier = (Double) xPath.evaluate(
                                "strengthModifier", action, XPathConstants.NUMBER
                        );
                        result.add(
                                new SendAilment(
                                        this.target,
                                        new AttackStrengthModifierSimple(modifier),
                                        this.attackModifiers
                                )
                        );
                    } catch (XPathExpressionException e) {
                        throw new IllegalStateException(e);
                    }
                });
        return result;
    }

}
