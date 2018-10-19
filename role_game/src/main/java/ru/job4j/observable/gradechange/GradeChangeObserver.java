package ru.job4j.observable.gradechange;

import ru.job4j.heroes.Hero;

public interface GradeChangeObserver {

    void upgraded(Hero hero);

    void degraded(Hero hero);
}
