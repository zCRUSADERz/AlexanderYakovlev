package ru.job4j;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.job4j.actions.AllActionsParsersSimple;
import ru.job4j.heroes.attack.AttackModifierChangeByGrade;
import ru.job4j.heroes.attack.AttackStrengthModifier;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.heroes.health.HealthHeroesSimple;
import ru.job4j.observable.die.HeroDiedObservableSimple;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.move.HeroMovedObservableSimple;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChangedObservableSimple;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.observable.newhero.HeroCreatedObservable;
import ru.job4j.observable.newhero.HeroCreatedObservableSimple;
import ru.job4j.squad.OpponentsSimple;
import ru.job4j.squad.SquadsMapper;
import ru.job4j.squad.SquadsMapperSimple;
import ru.job4j.utils.RandomElementFromList;
import ru.job4j.xml.heroes.NumberOfHeroesParserSimple;
import ru.job4j.xml.heroes.XMLHeroParserSimple;
import ru.job4j.xml.heroes.types.HeroTypesParserSimple;
import ru.job4j.xml.races.RaceSquadsParserSimple;
import ru.job4j.xml.races.XMLRaceParserSimple;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

/**
 * Game.
 * Основной класс игры.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class Game {
    private final HeroCreatedObservable createdObservable;
    private final HeroMovedObservable movedObservable;
    private final GradeChangeObservable upgradeObservable;
    private final HeroDiedObservable diedObservable;
    private final Logger logger = Logger.getLogger(Game.class);

    public Game(HeroCreatedObservable createdObservable,
                HeroMovedObservable movedObservable,
                GradeChangeObservable upgradeObservable,
                HeroDiedObservable diedObservable) {
        this.createdObservable = createdObservable;
        this.movedObservable = movedObservable;
        this.upgradeObservable = upgradeObservable;
        this.diedObservable = diedObservable;
    }

    public static void main(String[] args) {
        new Game(
                new HeroCreatedObservableSimple(),
                new HeroMovedObservableSimple(),
                new GradeChangedObservableSimple(),
                new HeroDiedObservableSimple()
        ).startGame();
    }

    /**
     * Запустить игру.
     */
    public void startGame() {
        this.logger.info("Role game app run.");
        final GameEnvironment environment = new GameEnvironment(
                new StopGameSimple(),
                this.createSquadsMapper(),
                this.createHeroesHealths(),
                this.createAttackModifiers(),
                new RandomElementFromList()
        );
        final String configPath = Game.class
                .getResource("/configuration.xml")
                .getPath();
        this.logger.info(String.format(
                "Configuration xml file path: %s.",
                configPath
                )
        );
        final Document document;
        try {
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new File(configPath));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new IllegalStateException(e);
        }
        final XPath xPath = XPathFactory.newInstance().newXPath();
        new OpponentsSimple(
                new RaceSquadsParserSimple(
                        xPath,
                        new XMLRaceParserSimple(
                                xPath,
                                new XMLHeroParserSimple(
                                        xPath,
                                        new AllActionsParsersSimple(),
                                        environment
                                )
                        )
                ),
                new NumberOfHeroesParserSimple(xPath),
                new HeroTypesParserSimple(xPath),
                upgradeObservable,
                environment
        ).createSquads(document);
        new GameCycle(
                diedObservable,
                upgradeObservable,
                movedObservable,
                environment
        ).startGame();
    }

    /**
     * Создает контроллер отряов.
     */
    private SquadsMapper createSquadsMapper() {
        final SquadsMapper squadsMapper
                = new SquadsMapperSimple(this.createdObservable);
        this.diedObservable.addObserver(squadsMapper);
        this.movedObservable.addObserver(squadsMapper);
        this.logger.info("Squads controller created and initialized.");
        return squadsMapper;
    }

    /**
     * Создает хранилище здоровья всех героев.
     */
    private HealthHeroes createHeroesHealths() {
        final HealthHeroes healthHeroes
                = new HealthHeroesSimple(this.diedObservable);
        this.diedObservable.addObserver(healthHeroes);
        this.createdObservable.addObserver(healthHeroes);
        this.logger.info("Hero health created and initialized.");
        return healthHeroes;
    }

    /**
     * Создает хранилище модификаторов атаки всех героев.
     */
    private AttackStrengthModifiers createAttackModifiers() {
        final AttackStrengthModifiers modifiers
                = new AttackStrengthModifiers();
        this.diedObservable.addObserver(modifiers);
        this.movedObservable.addObserver(modifiers);
        this.createdObservable.addObserver(modifiers);
        this.initializeAttackModifiersByGrade(modifiers);
        this.logger.info(
                "Attack strength modifiers created and initialized."
        );
        return modifiers;
    }

    /**
     * Инициализирует объект наблюдающий изменения привилегий героев.
     * @param modifiers модификаторы силы атаки героев.
     */
    private void initializeAttackModifiersByGrade(
            AttackStrengthModifiers modifiers) {
        final AttackStrengthModifier modifier
                = new AttackStrengthModifierSimple(1.5d);
        this.logger.info(String.format(
                "Attack modifier for upgraded heroes: %s",
                modifier
                )
        );
        this.upgradeObservable.addObserver(
                new AttackModifierChangeByGrade(
                        modifiers,
                        modifier
                )
        );
        this.logger.info(
                "Attack modifier change by grade created and initialized."
        );
    }
}
