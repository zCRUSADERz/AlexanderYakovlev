package ru.job4j.squad.observers;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.squad.observers.actions.ObserverAction;

public class DiedObserver implements HeroDiedObserver {
    private final String observerName;
    private final ObserverAction action;

    public DiedObserver(String observerName, ObserverAction action) {
        this.observerName = observerName;
        this.action = action;
    }

    @Override
    public void heroDied(Hero hero) {
        this.action.act(hero);
    }

    @Override
    public String toString() {
        return this.observerName;
    }
}
