package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import ru.job4j.actions.HeroAction;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.Collection;
import java.util.Optional;

/**
 * XMLDefaultParser.
 * Парсер действия по умолчанию, для конкретного действия.
 * Действие по умолчанию должно быть обязательно одно.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class XMLDefaultParser implements XMLDefaultActionParser {
    private final static String XML_TAG_NAME = "defaultAction";
    private final XPath xPath;
    private final Collection<XMLActionParser> parsers;

    public XMLDefaultParser(XPath xPath, Collection<XMLActionParser> parsers) {
        this.xPath = xPath;
        this.parsers = parsers;
    }

    /**
     * Распарсить дейстие по умолчанию.
     * @param actions узел конкретного действия.
     * @return действие по умолчанию.
     */
    @Override
    public HeroAction parseDefaultAction(Node actions) {
        try {
            Optional<HeroAction> defaultAction = Optional.empty();
            final Node defaultActionNode = (Node) this.xPath.evaluate(
                    XML_TAG_NAME, actions, XPathConstants.NODE
            );
            for (XMLActionParser parser : parsers) {
                final Collection<HeroAction> defaultActions
                        = parser.parseAllActions(defaultActionNode);
                if (!defaultActions.isEmpty()) {
                    if (defaultActions.size() != 1) {
                        throw new IllegalArgumentException(
                                "There can be only one default action."
                        );
                    }
                    defaultAction = Optional.of(defaultActions.iterator().next());
                    break;
                }
            }
            if (!defaultAction.isPresent()) {
                throw new IllegalArgumentException("No action found by default");
            }
            return defaultAction.get();
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }
}
