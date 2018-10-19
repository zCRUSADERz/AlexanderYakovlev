package ru.job4j.observable.gradechange;

import ru.job4j.heroes.Hero;

public interface GradeChangeObservable {

    void addObserver(GradeChangeObserver observer);
    void removeObserver(GradeChangeObserver observer);
    void upgraded(Hero hero);
    void degraded(Hero hero);
}
