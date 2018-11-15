package ru.job4j.squad.observers;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroCreatedObserver;
import ru.job4j.observers.Observables;
import ru.job4j.squad.observers.actions.ObserverAction;

public class CreatedObserver implements HeroCreatedObserver {
    private final String observerName;
    private final ObserverAction action;
    private final Observables<HeroCreatedObserver> createdObservables;

    public CreatedObserver(String observerName,
                           ObserverAction action,
                           Observables<HeroCreatedObserver> createdObservables) {
        this.observerName = observerName;
        this.action = action;
        this.createdObservables = createdObservables;
    }

    @Override
    public void heroCreated(Hero hero) {
        this.action.act(hero);
        this.createdObservables.removeObserverFor(hero, this);
    }

    @Override
    public String toString() {
        return this.observerName;
    }
}
