package ru.job4j.actions.actiontarget;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.squad.Squad;
import ru.job4j.squad.SquadSubType;
import ru.job4j.utils.RandomElementFromList;

import java.util.*;

public class Targets implements HeroDiedObserver {
    private final Map<Hero, Map<TargetTypes, RandomTarget>> squads;
    private final RandomElementFromList random;
    private final Logger logger = Logger.getLogger(Targets.class);

    public Targets(RandomElementFromList random) {
        this.random = random;
        this.squads = new HashMap<>();
    }

    public Optional<Hero> target(TargetTypes targetType,
                                 SquadSubType squadType,
                                 Hero hero) {
        return this.squads
                .get(hero)
                .get(targetType)
                .target(squadType);
    }

    public void newSquads(Squad firstSquad, Squad secondSquad) {
        this.addSquad(firstSquad, secondSquad);
        this.addSquad(secondSquad, firstSquad);
    }

    private void addSquad(Squad addedSquad, Squad enemySquad) {
        for (Hero hero : addedSquad.operation(SquadSubType.MAIN).heroes()) {
            final Map<TargetTypes, RandomTarget> typesMap = new HashMap<>();
            typesMap.put(
                    TargetTypes.FRIENDLY,
                    new RandomTargetImpl(addedSquad, this.random)
            );
            typesMap.put(
                    TargetTypes.ENEMY,
                    new RandomTargetImpl(enemySquad, this.random)
            );
            this.squads.put(hero, typesMap);
        }
    }

    @Override
    public void heroDied(Hero hero) {
        this.logger.info(String.format("%s targets have been removed.", hero));
        this.squads.remove(hero);
    }

    @Override
    public String toString() {
        return "Targets for heroes";
    }
}
