package ru.job4j.squad.observers.actions;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.Squad;
import ru.job4j.squad.SquadSubType;

public class TransferHero implements ObserverAction {
    private final Squad squad;
    private final SquadSubType typeFromRemove;
    private final SquadSubType typeToAdded;

    public TransferHero(Squad squad, SquadSubType typeFromRemove, SquadSubType typeToAdded) {
        this.squad = squad;
        this.typeFromRemove = typeFromRemove;
        this.typeToAdded = typeToAdded;
    }

    public void act(Hero hero) {
        this.squad
                .operation(this.typeFromRemove)
                .remove(hero);
        this.squad
                .operation(this.typeToAdded)
                .add(hero);
    }
}
