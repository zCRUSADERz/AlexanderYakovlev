package ru.job4j.observable.upgrade;

import ru.job4j.heroes.Hero;

import java.util.Collection;

public class HeroUpgrade implements HeroUpgradeObservable {
    private final Collection<UpgradeObserver> observers;

    public HeroUpgrade(Collection<UpgradeObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(UpgradeObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(UpgradeObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void upgraded(Hero hero) {
        this.observers.forEach(observer -> observer.upgraded(hero));
    }

    @Override
    public void degraded(Hero hero) {
        this.observers.forEach(observer -> observer.degraded(hero));
    }
}
