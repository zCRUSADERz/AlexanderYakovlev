package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.actiontarget.RandomTarget;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;

/**
 * XMLAttackParser.
 * Парсер действия атаковать противника из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class XMLAttackParser implements XMLActionParser {
    private final XPath xPath;
    private final RandomTarget randomTarget;
    private final AttackStrengthModifiers modifiers;
    private final HealthHeroes healthHeroes;

    public XMLAttackParser(XPath xPath, RandomTarget randomTarget,
                           AttackStrengthModifiers modifiers,
                           HealthHeroes healthHeroes) {
        this.xPath = xPath;
        this.randomTarget = randomTarget;
        this.modifiers = modifiers;
        this.healthHeroes = healthHeroes;
    }

    /**
     * XPath выражение для поиска узлов с действием атаковать противника.
     * @return XPath выражение для поиска.
     */
    @Override
    public String xPathExpressionToFind() {
        return "attackEnemy";
    }

    /**
     * Распарсить действие атаковать противника из предложенного узла.
     * @param action узел с данными действия атаковать противника.
     * @return действие атаковать противника.
     */
    @Override
    public HeroAction parse(Node action) {
        try {
            final String actionName = this.xPath.evaluate("name", action);
            final int damage = Integer.parseInt(
                    this.xPath.evaluate("damage", action)
            );
            return new AttackEnemy(
                    actionName,
                    damage,
                    this.randomTarget,
                    this.modifiers,
                    this.healthHeroes
            );
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }
}
