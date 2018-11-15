package ru.job4j.squad;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroCreatedObserver;
import ru.job4j.observers.Observable;
import ru.job4j.observers.Observables;
import ru.job4j.races.Race;
import ru.job4j.squad.observers.CreatedObserver;
import ru.job4j.squad.observers.actions.ObserverActionFactory;
import ru.job4j.xml.heroes.NumberOfHeroesParser;

import java.util.Collection;
import java.util.stream.Stream;

public class SquadHeroesFactory {
    private final NumberOfHeroesParser squadSizeParser;
    private final Collection<SquadSubType> typesToWhichWillBeAdded;
    private final Observable<HeroCreatedObserver> observablesInitializer;
    private final Observables<HeroCreatedObserver> heroCreatedObservable;
    private final ObserverActionFactory actionFactory;

    public SquadHeroesFactory(NumberOfHeroesParser squadSizeParser,
                              Collection<SquadSubType> typesToWhichWillBeAdded,
                              Observable<HeroCreatedObserver> observablesInitializer,
                              Observables<HeroCreatedObserver> heroCreatedObservable,
                              ObserverActionFactory actionFactory) {
        this.squadSizeParser = squadSizeParser;
        this.typesToWhichWillBeAdded = typesToWhichWillBeAdded;
        this.observablesInitializer = observablesInitializer;
        this.heroCreatedObservable = heroCreatedObservable;
        this.actionFactory = actionFactory;
    }

    public void createSquad(Race race, Squad squad) {
        this.squadSizeParser.parse().forEach((type, number) ->
                Stream.iterate(1, n -> n + 1)
                        .limit(number)
                        .forEach((n) -> {
                            final Hero hero
                                    = race.createHero(type, squad.toString());
                            this.observablesInitializer.notifyObservers(hero);
                            this.typesToWhichWillBeAdded
                                    .forEach(squadType -> this.heroCreatedObservable
                                            .addObserverFor(
                                                    hero,
                                                    new CreatedObserver(
                                                            "123",
                                                            this.actionFactory.action(
                                                                    squad,
                                                                    squadType
                                                            ),
                                                            this.heroCreatedObservable
                                                    )
                                            )
                                    );
                            this.heroCreatedObservable.notifyObservers(hero);
                        }));

    }
}
