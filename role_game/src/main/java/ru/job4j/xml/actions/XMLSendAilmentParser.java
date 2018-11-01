package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.SendAilment;
import ru.job4j.actions.actiontarget.RandomTarget;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

/**
 * XMLSendAilmentParser.
 * Парсер действия наслать недуг из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class XMLSendAilmentParser implements XMLActionParser {
    private final XPath xPath;
    private final RandomTarget randomTarget;
    private final AttackStrengthModifiers modifiers;

    public XMLSendAilmentParser(XPath xPath, RandomTarget randomTarget,
                                AttackStrengthModifiers modifiers) {
        this.xPath = xPath;
        this.randomTarget = randomTarget;
        this.modifiers = modifiers;
    }

    /**
     * XPath выражение для поиска узлов с действием наслать недуг.
     * @return XPath выражение для поиска.
     */
    @Override
    public String xPathExpressionToFind() {
        return "sendAilment";
    }

    /**
     * Распарсить действие наслать недуг из предложенного узла.
     * @param action узел с данными действия наслать недуг.
     * @return действие наслать недуг.
     */
    @Override
    public HeroAction parse(Node action) {
        try {
            final double modifier = (Double) xPath.evaluate(
                    "strengthModifier", action, XPathConstants.NUMBER
            );
            return new SendAilment(
                    this.randomTarget,
                    new AttackStrengthModifierSimple(modifier),
                    this.modifiers
            );
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }
}
