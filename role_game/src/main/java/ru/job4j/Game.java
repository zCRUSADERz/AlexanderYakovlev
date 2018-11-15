package ru.job4j;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.job4j.actions.AllActionsParsersSimple;
import ru.job4j.actions.actiontarget.Targets;
import ru.job4j.heroes.attack.AttackModifierChangeByGrade;
import ru.job4j.heroes.attack.AttackStrengthModifier;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.heroes.health.HealthHeroesSimple;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.observers.DegradeObserver;
import ru.job4j.observers.Observable;
import ru.job4j.observers.UpgradeObserver;
import ru.job4j.observers.HeroMovedObserver;
import ru.job4j.observers.HeroCreatedObserver;
import ru.job4j.observers.Observables;
import ru.job4j.squad.SquadHeroesFactory;
import ru.job4j.squad.SquadRegister;
import ru.job4j.squad.SquadSubType;
import ru.job4j.squad.observers.*;
import ru.job4j.squad.observers.actions.*;
import ru.job4j.utils.RandomElementFromList;
import ru.job4j.xml.heroes.NumberOfHeroesParserSimple;
import ru.job4j.xml.heroes.XMLHeroParserSimple;
import ru.job4j.xml.heroes.types.HeroTypesParserSimple;
import ru.job4j.xml.races.SquadsParserSimple;
import ru.job4j.xml.races.XMLRandomRaceParserSimple;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Game.
 * Основной класс игры.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class Game {
    private final Logger logger = Logger.getLogger(Game.class);

    public static void main(String[] args) {
        new Game().startGame();
    }

    /**
     * Запустить игру.
     */
    public void startGame() {
        this.logger.info("Role game app run.");
        final Observables<HeroDiedObserver> diedObservables
                = new Observables<>(HeroDiedObserver.DIED);
        final HealthHeroes healthHeroes
                = new HealthHeroesSimple(diedObservables);
        final AttackStrengthModifiers attackModifiers
                = new AttackStrengthModifiers();
        final AttackStrengthModifier modifierForUpgraded
                = new AttackStrengthModifierSimple(1.5d);
        final AttackModifierChangeByGrade attackModifierChangeByGrade =
                new AttackModifierChangeByGrade(attackModifiers, modifierForUpgraded);
        final RandomElementFromList random = new RandomElementFromList();
        final Targets targets = new Targets(random);
        final XPath xPath = XPathFactory.newInstance().newXPath();
        final StopGame game = new StopGameSimple();
        final Observables<HeroCreatedObserver> createdObservables
                = new Observables<>(
                Arrays.asList(
                        attackModifiers,
                        healthHeroes
                ),
                HeroCreatedObserver.CREATED
        );
        final Observables<HeroMovedObserver> movedObservables =
                new Observables<>(
                        Collections.singleton(
                                attackModifiers
                        ),
                        HeroMovedObserver.MOVED
                );
        final Observables<UpgradeObserver> upgradeObservables
                = new Observables<>(
                Collections.singleton(
                        attackModifierChangeByGrade
                ),
                UpgradeObserver.UPGRADED
        );
        final Observables<DegradeObserver> degradeObservables
                = new Observables<>(
                Collections.singleton(
                        attackModifierChangeByGrade
                ),
                DegradeObserver.DEGRADED
        );
        diedObservables.addObserver(healthHeroes);
        diedObservables.addObserver(attackModifiers);
        diedObservables.addObserver(targets);
        diedObservables.addObserver(movedObservables);
        diedObservables.addObserver(upgradeObservables);
        diedObservables.addObserver(degradeObservables);
        diedObservables.addObserver(diedObservables);
        final Document document = this.configurationXMLDoc();
        new GameCycle(
                new SquadsParserSimple(
                        xPath,
                        new XMLRandomRaceParserSimple(
                                xPath,
                                new XMLHeroParserSimple(
                                        xPath,
                                        new AllActionsParsersSimple(
                                                xPath,
                                                attackModifiers,
                                                healthHeroes,
                                                targets,
                                                upgradeObservables,
                                                degradeObservables
                                        ),
                                        random),
                                new HeroTypesParserSimple(
                                        xPath,
                                        document
                                ),
                                new Random()
                        ),
                        targets,
                        new SquadRegister(
                                this.observersForMainSquads(
                                        diedObservables,
                                        movedObservables,
                                        upgradeObservables,
                                        degradeObservables,
                                        game
                                )
                        ),
                        new SquadHeroesFactory(
                                new NumberOfHeroesParserSimple(
                                        xPath,
                                        document,
                                        new HeroTypesParserSimple(
                                                xPath,
                                                document
                                        )
                                ),
                                Arrays.asList(
                                        SquadSubType.MAIN,
                                        SquadSubType.REGULAR
                                ),
                                new Observable<>(
                                        Arrays.asList(
                                                createdObservables,
                                                movedObservables,
                                                upgradeObservables,
                                                degradeObservables,
                                                diedObservables
                                        ),
                                        HeroCreatedObserver.CREATED
                                ),
                                createdObservables,
                                AddHero::new
                        )
                ),
                new SquadRegister(
                        this.observersForMoveSequenceSquads(
                                diedObservables,
                                movedObservables,
                                upgradeObservables,
                                degradeObservables
                        )
                ),
                movedObservables,
                game
        ).startGame(document);
    }

    private Document configurationXMLDoc() {
        final String configPath = Game.class
                .getResource("/configuration.xml")
                .getPath();
        this.logger.info(String.format(
                "Configuration xml file path: %s.",
                configPath
                )
        );
        try {
            return DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new File(configPath));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<SquadSubType, Collection<ObserverFactory>> observersForMainSquads(
            Observables<HeroDiedObserver> diedObservables,
            Observables<HeroMovedObserver> movedObservables,
            Observables<UpgradeObserver> upgradeObservables,
            Observables<DegradeObserver> degradeObservables,
            StopGame game) {
        final Map<SquadSubType, Collection<ObserverFactory>> observers = new HashMap<>();
        observers.put(
                SquadSubType.MAIN,
                Collections.singleton(
                        new ObserverFactory<>(
                                diedObservables,
                                squad -> new DiedObserver(
                                        "Died",
                                        new RemoveAndCheckSquadDestroyed(
                                                squad,
                                                SquadSubType.MAIN,
                                                game
                                        )
                                )
                        )
                )
        );
        observers.put(
                SquadSubType.REGULAR,
                Arrays.asList(
                        new ObserverFactory<>(
                                upgradeObservables,
                                squad -> new UpgradeObserverSquad(
                                        "Upgrade",
                                        new TransferHero(
                                                squad,
                                                SquadSubType.REGULAR,
                                                SquadSubType.UPGRADED
                                        )
                                )
                        ),
                        new ObserverFactory<>(
                                diedObservables,
                                squad -> new DiedObserver(
                                        "Died",
                                        new RemoveHero(
                                                squad,
                                                SquadSubType.REGULAR
                                        )
                                )
                        )
                )
        );
        observers.put(
                SquadSubType.UPGRADED,
                Arrays.asList(
                        new ObserverFactory<>(
                                movedObservables,
                                squad -> new MovedObserver(
                                        "Moved",
                                        new TransferHero(
                                                squad,
                                                SquadSubType.UPGRADED,
                                                SquadSubType.REGULAR
                                        )
                                )
                        ),
                        new ObserverFactory<>(
                                degradeObservables,
                                squad -> new DegradeObserverSquad(
                                        "Degrade",
                                        new TransferHero(
                                                squad,
                                                SquadSubType.UPGRADED,
                                                SquadSubType.REGULAR
                                        )
                                )
                        ),
                        new ObserverFactory<>(
                                diedObservables,
                                squad -> new DiedObserver(
                                        "Died",
                                        new RemoveHero(
                                                squad,
                                                SquadSubType.UPGRADED
                                        )
                                )
                        )
                )
        );
        return observers;
    }

    private Map<SquadSubType, Collection<ObserverFactory>> observersForMoveSequenceSquads(
            Observables<HeroDiedObserver> diedObservables,
            Observables<HeroMovedObserver> movedObservables,
            Observables<UpgradeObserver> upgradeObservables,
            Observables<DegradeObserver> degradeObservables) {
        final Map<SquadSubType, Collection<ObserverFactory>> observers = new HashMap<>();
        observers.put(
                SquadSubType.REGULAR,
                Arrays.asList(
                        new ObserverFactory<>(
                                movedObservables,
                                squad -> new MovedObserver(
                                        "Moved",
                                        new RemoveHero(
                                                squad,
                                                SquadSubType.REGULAR
                                        )
                                )
                        ),
                        new ObserverFactory<>(
                                upgradeObservables,
                                squad -> new UpgradeObserverSquad(
                                        "Upgrade",
                                        new TransferHero(
                                                squad,
                                                SquadSubType.REGULAR,
                                                SquadSubType.UPGRADED
                                        )
                                )
                        ),
                        new ObserverFactory<>(
                                diedObservables,
                                squad -> new DiedObserver(
                                        "Died",
                                        new RemoveHero(
                                                squad,
                                                SquadSubType.REGULAR
                                        )
                                )
                        )
                )
        );
        observers.put(
                SquadSubType.UPGRADED,
                Arrays.asList(
                        new ObserverFactory<>(
                                movedObservables,
                                squad -> new MovedObserver(
                                        "Moved",
                                        new RemoveHero(
                                                squad,
                                                SquadSubType.UPGRADED
                                        )
                                )
                        ),
                        new ObserverFactory<>(
                                degradeObservables,
                                squad -> new DegradeObserverSquad(
                                        "Degrade",
                                        new TransferHero(
                                                squad,
                                                SquadSubType.UPGRADED,
                                                SquadSubType.REGULAR
                                        )
                                )
                        ),
                        new ObserverFactory<>(
                                diedObservables,
                                squad -> new DiedObserver(
                                        "Died",
                                        new RemoveHero(
                                                squad,
                                                SquadSubType.REGULAR
                                        )
                                )
                        )
                )
        );
        return observers;
    }
}
