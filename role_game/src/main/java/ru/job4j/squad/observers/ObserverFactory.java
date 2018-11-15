package ru.job4j.squad.observers;

import ru.job4j.observers.Observables;
import ru.job4j.observers.SquadObserver;
import ru.job4j.squad.Squad;

import java.util.function.Function;

public class ObserverFactory<T> {
    private final Observables<T> observables;
    private final Function<Squad, T> function;

    public ObserverFactory(Observables<T> observables,
                           Function<Squad, T> function) {
        this.observables = observables;
        this.function = function;
    }

    public SquadObserver observer(Squad squad) {
        return new SquadObserverImpl<>(
                squad.toString(),
                this.observables,
                this.function.apply(squad)
        );
    }
}
