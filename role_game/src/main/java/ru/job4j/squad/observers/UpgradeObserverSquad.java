package ru.job4j.squad.observers;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.UpgradeObserver;
import ru.job4j.squad.observers.actions.ObserverAction;

public class UpgradeObserverSquad implements UpgradeObserver {
    private final String observerName;
    private final ObserverAction action;

    public UpgradeObserverSquad(String observerName, ObserverAction action) {
        this.observerName = observerName;
        this.action = action;
    }

    @Override
    public void upgraded(Hero hero) {
        this.action.act(hero);
    }

    @Override
    public String toString() {
        return this.observerName;
    }
}
