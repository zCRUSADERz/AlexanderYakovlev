package ru.job4j.squad;

import ru.job4j.squad.observers.ObserverFactory;

import java.util.Collection;
import java.util.Map;

public class SquadRegister {
    private final Map<SquadSubType, Collection<ObserverFactory>> observersFactory;

    public SquadRegister(Map<SquadSubType, Collection<ObserverFactory>> observersFactory) {
        this.observersFactory = observersFactory;
    }

    public void register(Squad squad) {
        for (Map.Entry<SquadSubType, Collection<ObserverFactory>> entry
                : this.observersFactory.entrySet()) {
            final SquadOperation squadOperation = squad.operation(entry.getKey());
            for (ObserverFactory factory : entry.getValue()) {
                squadOperation.addObserver(factory.observer(squad));
            }
        }
    }
}
