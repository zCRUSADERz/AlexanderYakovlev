package ru.job4j.squad.observers.actions;

import ru.job4j.squad.Squad;
import ru.job4j.squad.SquadSubType;

public interface ObserverActionFactory {

    ObserverAction action(Squad squad, SquadSubType type);
}
