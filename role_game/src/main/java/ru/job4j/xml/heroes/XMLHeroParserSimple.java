package ru.job4j.xml.heroes;

import org.w3c.dom.Node;
import ru.job4j.GameEnvironment;
import ru.job4j.actions.HeroAction;
import ru.job4j.heroes.HeroFactory;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.xml.actions.XMLActionParser;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * XMLHeroParserSimple.
 * Парсер героев из xml документа.
 * Возвращает фабрику для героев определенной расы и типа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class XMLHeroParserSimple implements XMLHeroParser {
    private final XPath xPath;
    private final Collection<XMLActionParser> actionParsers;
    private final GameEnvironment environment;

    public XMLHeroParserSimple(XPath xPath,
                               Collection<XMLActionParser> actionParsers,
                               GameEnvironment environment) {
        this.xPath = xPath;
        this.actionParsers = actionParsers;
        this.environment = environment;
    }

    /**
     * Парсит данные героя и возвращает фабрику героев данной расы и типа.
     * @param heroNode узел содержащий данные конкретного героя.
     * @return фабрику героев определенной расы и типа.
     */
    @Override
    public HeroFactory parseHero(Node heroNode) {
        try {
            final String heroTypeDescription = this.xPath.evaluate(
                    "name", heroNode
            );
            final List<HeroAction> heroActions = new ArrayList<>();
            final Node actions = (Node) this.xPath.evaluate(
                    "actions", heroNode, XPathConstants.NODE
            );
            this.actionParsers.forEach(
                    (parser) -> heroActions.addAll(parser.parseAll(actions))
            );
            return new HeroFactorySimple(
                    heroTypeDescription,
                    heroActions,
                    environment.getRandom()
            );
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression");
        }
    }
}