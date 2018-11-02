package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTarget;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.xml.actions.utils.FindNodeByXPath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * XMLAttackParser.
 * Парсер действия атаковать противника из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class XMLAttackParser implements XMLActionParser {
    private final static String XML_TAG_NAME = "attackEnemy";
    private final XPath xPath;
    private final FindNodeByXPath findNodeByXPath;
    private final RandomTarget randomTarget;
    private final AttackStrengthModifiers modifiers;
    private final HealthHeroes healthHeroes;

    public XMLAttackParser(XPath xPath, FindNodeByXPath findNodeByXPath,
                           RandomTarget randomTarget,
                           AttackStrengthModifiers modifiers,
                           HealthHeroes healthHeroes) {
        this.xPath = xPath;
        this.findNodeByXPath = findNodeByXPath;
        this.randomTarget = randomTarget;
        this.modifiers = modifiers;
        this.healthHeroes = healthHeroes;
    }

    /**
     * Распарсить все действия атаковать противника из
     * предложенного документа с описанием действий.
     * @param actions xml node указывающая на первый узел в списке действий.
     * @return коллекцию действий, либо пустую коллекцию, если не найдено.
     */
    @Override
    public Collection<HeroAction> parseAll(Node actions) {
        final Collection<HeroAction> result = new ArrayList<>();
        this.findNodeByXPath
                .find(XML_TAG_NAME, actions)
                .forEach((action) -> {
                    try {
                        final String actionName
                                = this.xPath.evaluate("name", action);
                        final int damage = Integer.parseInt(
                                this.xPath.evaluate("damage", action)
                        );
                        result.add(
                                new AttackEnemy(
                                        actionName,
                                        damage,
                                        this.randomTarget,
                                        this.modifiers,
                                        this.healthHeroes
                                )
                        );
                    } catch (XPathExpressionException e) {
                        throw new IllegalStateException(e);
                    }
                });
        return result;
    }
}
