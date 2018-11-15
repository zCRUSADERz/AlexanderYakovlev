package ru.job4j.squad.observers;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.DegradeObserver;
import ru.job4j.squad.observers.actions.ObserverAction;

public class DegradeObserverSquad implements DegradeObserver {
    private final String observerName;
    private final ObserverAction action;

    public DegradeObserverSquad(String observerName, ObserverAction action) {
        this.observerName = observerName;
        this.action = action;
    }

    @Override
    public void degraded(Hero hero) {
        this.action.act(hero);
    }

    @Override
    public String toString() {
        return this.observerName;
    }
}
