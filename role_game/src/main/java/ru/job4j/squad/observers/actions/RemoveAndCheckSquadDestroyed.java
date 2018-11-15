package ru.job4j.squad.observers.actions;

import ru.job4j.StopGame;
import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadOperation;
import ru.job4j.squad.Squad;
import ru.job4j.squad.SquadSubType;

public class RemoveAndCheckSquadDestroyed implements ObserverAction {
    private final Squad squad;
    private final SquadSubType type;
    private final StopGame stopGame;

    public RemoveAndCheckSquadDestroyed(Squad squad,
                                        SquadSubType type,
                                        StopGame stopGame) {
        this.squad = squad;
        this.type = type;
        this.stopGame = stopGame;
    }

    public void act(Hero hero) {
        final SquadOperation operation = this.squad.operation(this.type);
        operation.remove(hero);
        if (operation.squadDestroyed()) {
            this.stopGame.stopGame();
        }
    }
}
