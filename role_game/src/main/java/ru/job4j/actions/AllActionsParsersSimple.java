package ru.job4j.actions;

import ru.job4j.actions.actiontarget.RandomTargetForHeroImpl;
import ru.job4j.actions.actiontarget.TargetTypes;
import ru.job4j.actions.actiontarget.Targets;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.observers.DegradeObserver;
import ru.job4j.observers.Observables;
import ru.job4j.observers.UpgradeObserver;
import ru.job4j.squad.SquadSubType;
import ru.job4j.xml.actions.*;
import ru.job4j.xml.actions.utils.FindNodeByXPath;
import ru.job4j.xml.actions.utils.FindNodeByXPathSimple;

import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * AllActionsParsers.
 * Возвращает коллекцию всех парсеров действий. По мере добавления новых
 * действий необходимо добавлять к ним соответствующие xml парсеры.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.11.2018
 */
public class AllActionsParsersSimple implements AllActionsParsers {
    private final XPath xPath;
    private final AttackStrengthModifiers attackModifiers;
    private final HealthHeroes healthHeroes;
    private final Targets targets;
    private final Observables<UpgradeObserver> upgradeObservables;
    private final Observables<DegradeObserver> degradeObservables;

    public AllActionsParsersSimple(XPath xPath,
                                   AttackStrengthModifiers attackModifiers,
                                   HealthHeroes healthHeroes,
                                   Targets targets,
                                   Observables<UpgradeObserver> upgradeObservables,
                                   Observables<DegradeObserver> degradeObservables) {
        this.xPath = xPath;
        this.attackModifiers = attackModifiers;
        this.healthHeroes = healthHeroes;
        this.targets = targets;
        this.upgradeObservables = upgradeObservables;
        this.degradeObservables = degradeObservables;
    }

    /**
     * Возвращает коллекцию всех парсеров действий.
     * @return коллекцию всех парсеров действий.
     */
    @Override
    public Collection<XMLActionParser> parsers() {
        final Collection<XMLActionParser> parsers = new ArrayList<>();
        final FindNodeByXPath findNodeByXPath = new FindNodeByXPathSimple(this.xPath);
        final XMLDefaultActionParser defaultParser = new XMLDefaultParser(
                this.xPath,
                parsers
        );
        parsers.addAll(
                Arrays.asList(
                        new XMLAttackParser(
                                this.xPath,
                                findNodeByXPath,
                                new RandomTargetForHeroImpl(
                                        TargetTypes.ENEMY,
                                        SquadSubType.MAIN,
                                        this.targets
                                ),
                                this.attackModifiers,
                                this.healthHeroes
                        ),
                        new XMLUpgradeParser(
                                findNodeByXPath,
                                defaultParser,
                                new RandomTargetForHeroImpl(
                                        TargetTypes.FRIENDLY,
                                        SquadSubType.REGULAR,
                                        this.targets
                                ),
                                this.upgradeObservables
                        ),
                        new XMLDegradeParser(
                                findNodeByXPath,
                                defaultParser,
                                new RandomTargetForHeroImpl(
                                        TargetTypes.ENEMY,
                                        SquadSubType.UPGRADED,
                                        this.targets
                                ),
                                this.degradeObservables
                        ),
                        new XMLSendAilmentParser(
                                this.xPath,
                                findNodeByXPath,
                                new RandomTargetForHeroImpl(
                                        TargetTypes.ENEMY,
                                        SquadSubType.MAIN,
                                        this.targets
                                ),
                                this.attackModifiers
                        ),
                        new XMLInactionParser(findNodeByXPath)
                )
        );
        return parsers;
    }
}
