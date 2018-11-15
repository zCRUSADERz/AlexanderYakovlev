package ru.job4j.squad.observers.actions;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.Squad;
import ru.job4j.squad.SquadSubType;

public class AddHero implements ObserverAction {
    private final Squad squad;
    private final SquadSubType type;

    public AddHero(Squad squad, SquadSubType type) {
        this.squad = squad;
        this.type = type;
    }

    @Override
    public void act(Hero hero) {
        this.squad
                .operation(this.type)
                .add(hero);
    }
}
