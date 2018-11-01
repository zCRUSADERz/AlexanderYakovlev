package ru.job4j.xml.actions;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.job4j.actions.HeroAction;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AllXMLActionsParser.
 * Парсер всех действий из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.11.2018
 */
public class AllXMLActionsParserSimple implements AllXMLActionsParser {
   private final XPath xPath;
   private final Collection<XMLActionParser> parsers;

    public AllXMLActionsParserSimple(XPath xPath,
                                     Collection<XMLActionParser> parsers) {
        this.xPath = xPath;
        this.parsers = parsers;
    }

    /**
     * Распарсить все действия.
     * @param actions узел с первым действием.
     * @return список всех действий героя.
     */
    @Override
    public List<HeroAction> parseAll(Node actions) {
        final List<HeroAction> result = new ArrayList<>();
        parsers.forEach((parser) -> {
            try {
                NodeList concreteActions = (NodeList) this.xPath.evaluate(
                        parser.xPathExpressionToFind(), actions, XPathConstants.NODESET
                );
                if (concreteActions.getLength() != 0) {
                    for (int i = 0; i <= concreteActions.getLength(); i++) {
                        final Node action = concreteActions.item(i);
                        result.add(parser.parse(action));
                    }
                }
            } catch (XPathExpressionException e) {
                throw new IllegalStateException("Wrong XPath expression.");
            }
        });
        return result;
    }
}
