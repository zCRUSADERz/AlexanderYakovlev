package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Observables<T> implements HeroCreatedObserver, HeroDiedObserver {
    private final Map<Hero, Observable<T>> observableForHero = new HashMap<>();
    private final Observable<T> observable;
    private final BiConsumer<Hero, T> consumer;

    public Observables(BiConsumer<Hero, T> consumer) {
        this.observable = new Observable<>(consumer);
        this.consumer = consumer;
    }

    public Observables(Collection<T> observers,
                       BiConsumer<Hero, T> consumer) {
        this.observable = new Observable<>(observers, consumer);
        this.consumer = consumer;
    }

    public void addObserver(T observer) {
        this.observable.addObserver(observer);
    }

    public void addObserverFor(Hero hero, T observer) {
        this.observableForHero.get(hero).addObserver(observer);
    }

    public void removeObserverFor(Hero hero, T observer) {
        this.observableForHero.get(hero).removeObserver(observer);
    }

    public void notifyObservers(Hero hero) {
        this.observableForHero.get(hero).notifyObservers(hero);
        this.observable.notifyObservers(hero);
    }

    @Override
    public void heroCreated(Hero hero) {
        this.observableForHero.put(
                hero,
                new Observable<>(this.consumer)
        );
    }

    @Override
    public void heroDied(Hero hero) {
        this.observableForHero.remove(hero);
    }
}
