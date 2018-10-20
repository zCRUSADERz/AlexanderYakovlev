package ru.job4j;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.DefaultAction;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.SendAilment;
import ru.job4j.actions.grade.DegradeAction;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
import ru.job4j.heroes.attack.AttackModifierChangeByGrade;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.heroes.Race;
import ru.job4j.heroes.RaceSimple;
import ru.job4j.observable.die.HeroDied;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.move.HeroMoved;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChanged;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.observable.newhero.HeroCreated;
import ru.job4j.observable.newhero.HeroCreatedObservable;
import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.SquadSimple;
import ru.job4j.squad.Squads;
import ru.job4j.squad.SquadsSimple;
import ru.job4j.utils.RandomElementFromList;

import java.util.*;

public class Game {

    public void start() {
        final RandomElementFromList random = new RandomElementFromList(new Random());

        final StopSimple stopGame = new StopSimple();

        final HeroDiedObservable dieObservable = new HeroDied(new ArrayList<>());
        final GradeChangeObservable upgradeObservable = new GradeChanged(new ArrayList<>());
        final HeroMovedObservable movedObservable = new HeroMoved(new ArrayList<>());
        final HeroCreatedObservable heroCreatedObservable = new HeroCreated(new ArrayList<>());

        final HealthHeroes healthHeroes = new HealthHeroes(new HashMap<>(), dieObservable);

        final SquadHeroes squad1 = new SquadSimple(
                "Красные",
                new HashSet<>(),
                new HashSet<>(),
                upgradeObservable,
                random,
                stopGame
        );

        final SquadHeroes squad2 = new SquadSimple(
                "Синие",
                new HashSet<>(),
                new HashSet<>(),
                upgradeObservable,
                random,
                stopGame
        );

        final Squads squads = new SquadsSimple(
                new HashSet<>(), new HashMap<>(),
                new HashMap<>(),
                heroCreatedObservable
        );

        final GameCycle gameCycle = new GameCycle(squads, stopGame);
        final AttackStrengthModifiers attackStrengthModifiers
                = new AttackStrengthModifiers(new HashMap<>());
        final AttackModifierChangeByGrade attackModifierChangeByGrade
                = new AttackModifierChangeByGrade(
                        attackStrengthModifiers, new AttackStrengthModifierSimple(1.5d)
        );
        final HeroAction defaultAction = new DefaultAction();
        final HeroAction mageActionDefault = new AttackEnemy(
                "Атаковать магией ", 4,
                attackStrengthModifiers, squads, healthHeroes
        );
        final Race humans = new RaceSimple(
                new HeroFactorySimple(
                        "маг",
                        Arrays.asList(
                                new GradeActionSimple(
                                        random,
                                        mageActionDefault,
                                        new UpgradeAction(squads)
                                ),
                                mageActionDefault
                        ), random
                ),
                new HeroFactorySimple(
                        "арбалетчик",
                        Arrays.asList(
                                new AttackEnemy(
                                        "Стрелять из арбалета в ", 5,
                                        attackStrengthModifiers, squads, healthHeroes
                                ), new AttackEnemy(
                                        "Атаковать ", 3,
                                        attackStrengthModifiers, squads, healthHeroes
                                )
                        ), random
                ),
                new HeroFactorySimple(
                        "воин",
                        Collections.singletonList(
                                new AttackEnemy(
                                        "Атаковать мечом ", 18,
                                        attackStrengthModifiers, squads, healthHeroes
                                )
                        ), random
                ),
                squads
        );
        final Race orcs = new RaceSimple(
                new HeroFactorySimple(
                        "шаман",
                        Arrays.asList(
                                new GradeActionSimple(
                                        random,
                                        defaultAction,
                                        new UpgradeAction(squads)
                                ),
                                new GradeActionSimple(
                                        random,
                                        defaultAction,
                                        new DegradeAction(squads)
                                ),
                                new SendAilment(
                                        squads,
                                        new AttackStrengthModifierSimple(0.5d),
                                        attackStrengthModifiers
                                )
                        ), random
                ),
                new HeroFactorySimple(
                        "лучник",
                        Arrays.asList(
                                new AttackEnemy(
                                        "Стрелять из лука в ", 3,
                                        attackStrengthModifiers, squads, healthHeroes
                                ), new AttackEnemy(
                                        "Удар клинком по ", 2,
                                        attackStrengthModifiers, squads, healthHeroes
                                )
                        ), random
                ),
                new HeroFactorySimple(
                        "гоблин",
                        Collections.singletonList(
                                new AttackEnemy(
                                        "Атака дубиной по ", 20,
                                        attackStrengthModifiers, squads, healthHeroes
                                )
                        ), random
                ),
                squads
        );

        dieObservable.addObserver(healthHeroes);
        dieObservable.addObserver(attackStrengthModifiers);
        dieObservable.addObserver(squads);
        movedObservable.addObserver(attackStrengthModifiers);
        movedObservable.addObserver(squads);
        upgradeObservable.addObserver(attackModifierChangeByGrade);
        heroCreatedObservable.addObserver(healthHeroes);
        heroCreatedObservable.addObserver(attackStrengthModifiers);

        humans.createMagiciansHeroes(1, squad1, squad2);
        humans.createArchersHeroes(3, squad1, squad2);
        humans.createWarriorsHeroes(4, squad1, squad2);

        orcs.createMagiciansHeroes(1, squad2, squad1);
        orcs.createArchersHeroes(3, squad2, squad1);
        orcs.createWarriorsHeroes(4, squad2, squad1);

        gameCycle.start(upgradeObservable, dieObservable, movedObservable);
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
