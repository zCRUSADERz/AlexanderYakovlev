package ru.job4j.squad.observers.actions;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.Squad;
import ru.job4j.squad.SquadSubType;

public class RemoveHero implements ObserverAction {
    private final Squad squad;
    private final SquadSubType type;

    public RemoveHero(Squad squad, SquadSubType type) {
        this.squad = squad;
        this.type = type;
    }

    public void act(Hero hero) {
        this.squad
                .operation(this.type)
                .remove(hero);
    }
}
