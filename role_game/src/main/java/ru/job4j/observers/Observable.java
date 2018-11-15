package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiConsumer;

public class Observable<T> {
    private final Collection<T> observers;
    private final BiConsumer<Hero, T> consumer;

    public Observable(BiConsumer<Hero, T> consumer) {
        this.observers = new HashSet<>();
        this.consumer = consumer;
    }

    public Observable(Collection<T> observers,
                      BiConsumer<Hero, T> consumer) {
        this.observers = new HashSet<>(observers);
        this.consumer = consumer;
    }

    public void addObserver(T observer) {
        this.observers.add(observer);
    }

    public void removeObserver(T observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(Hero hero) {
        new ArrayList<>(this.observers)
                .forEach(observer -> consumer.accept(hero, observer));

    }
}
