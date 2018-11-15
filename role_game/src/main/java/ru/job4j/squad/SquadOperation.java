package ru.job4j.squad;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroAddedObserver;
import ru.job4j.observers.HeroRemovedObserver;
import ru.job4j.observers.Observable;
import ru.job4j.observers.SquadObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SquadOperation implements SquadHeroes {
    private final String squadName;
    private final List<Hero> heroes;
    private final Observable<HeroAddedObserver> addedObservable;
    private final Observable<HeroRemovedObserver> removedObservable;
    private final Logger logger = Logger.getLogger(SquadOperation.class);

    public SquadOperation(String squadName) {
        this.squadName = squadName;
        this.heroes = new ArrayList<>();
        this.addedObservable = new Observable<>(HeroAddedObserver.ADDED);
        this.removedObservable = new Observable<>(HeroRemovedObserver.REMOVED);
    }

    @Override
    public Optional<Hero> hero() {
        final Optional<Hero> result;
        if (this.heroes.isEmpty()) {
            result = Optional.empty();
        } else {
            result = Optional.of(this.heroes.get(0));
        }
        return result;
    }

    public List<Hero> heroes() {
        return this.heroes;
    }

    public void add(Hero hero) {
        this.heroes.add(hero);
        this.logger.info(String.format(
                "%s was added from squad %s.",
                hero, this.squadName
        ));
        this.addedObservable.notifyObservers(hero);
    }

    public void remove(Hero hero) {
        this.heroes.remove(hero);
        this.logger.info(String.format(
                "%s was removed from squad %s.",
                hero, this.squadName
        ));
        this.removedObservable.notifyObservers(hero);
    }

    public void addObserver(SquadObserver observer) {
        this.addedObservable.addObserver(observer);
        this.removedObservable.addObserver(observer);

    }

    public boolean squadDestroyed() {
        return this.heroes.isEmpty();
    }

    @Override
    public String toString() {
        return this.squadName;
    }
}
