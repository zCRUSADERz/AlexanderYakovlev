package ru.job4j.squad.observers;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroMovedObserver;
import ru.job4j.squad.observers.actions.ObserverAction;

public class MovedObserver implements HeroMovedObserver {
    private final String observerName;
    private final ObserverAction action;

    public MovedObserver(String observerName, ObserverAction action) {
        this.observerName = observerName;
        this.action = action;
    }

    @Override
    public void heroMoved(Hero hero) {
        this.action.act(hero);
    }

    @Override
    public String toString() {
        return this.observerName;
    }
}
