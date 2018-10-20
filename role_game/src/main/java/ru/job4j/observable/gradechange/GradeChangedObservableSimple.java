package ru.job4j.observable.gradechange;

import ru.job4j.heroes.Hero;

import java.util.ArrayList;
import java.util.Collection;

public class GradeChangedObservableSimple implements GradeChangeObservable {
    private final Collection<GradeChangeObserver> observers;

    public GradeChangedObservableSimple() {
        this(new ArrayList<>());
    }

    public GradeChangedObservableSimple(Collection<GradeChangeObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(GradeChangeObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(GradeChangeObserver observer) {
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
