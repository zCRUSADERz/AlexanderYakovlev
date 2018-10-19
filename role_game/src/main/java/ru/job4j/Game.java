package ru.job4j;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.grade.DegradeAction;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
import ru.job4j.heroes.attack.AttackModifierChangeByGrade;
import ru.job4j.heroes.attack.AttackStrengthModifier;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.heroes.health.HealthSimple;
import ru.job4j.heroes.health.HeroHealth;
import ru.job4j.heroes.Hero;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.heroes.Race;
import ru.job4j.heroes.RaceSimple;
import ru.job4j.observable.die.HeroDied;
import ru.job4j.observable.die.HeroDieObservable;
import ru.job4j.observable.move.HeroMoved;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.gradechange.GradeChange;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.utils.RandomElementFromList;

import java.util.*;

public class Game {

    public void start() {
        final RandomElementFromList random = new RandomElementFromList(new Random());
        final StopSimple stopGame = new StopSimple();
        final Map<Hero, HeroHealth> heroHealthMap = new HashMap<>();
        final HealthHeroes healthHeroes = new HealthHeroes(heroHealthMap);
        final HeroDieObservable dieObservable = new HeroDied(new ArrayList<>());
        final GradeChangeObservable upgradeObservable = new GradeChange(new ArrayList<>());
        final HeroMovedObservable movedObservable = new HeroMoved(new ArrayList<>());
        final Set<Hero> squad1Heroes = new HashSet<>();
        final Set<Hero> squad1RegularHeroes = new HashSet<>();
        final String firstSquadName = "Красные";
        final SquadHeroes squad1 = new SquadSimple(
                firstSquadName,
                squad1Heroes,
                squad1RegularHeroes,
                upgradeObservable,
                random,
                stopGame
        );
        final Set<Hero> squad2Heroes = new HashSet<>();
        final Set<Hero> squad2RegularHeroes = new HashSet<>();
        final String secondSquadName = "Синие";
        final SquadHeroes squad2 = new SquadSimple(
                secondSquadName,
                squad2Heroes,
                squad2RegularHeroes,
                upgradeObservable,
                random,
                stopGame
        );
        final Collection<SquadHeroes> squads = Arrays.asList(squad1, squad2);
        final GameCycle gameCycle = new GameCycle(squads, stopGame);
        final Map<Hero, Set<AttackStrengthModifier>> modifiersMap = new HashMap<>();
        final AttackStrengthModifiers attackStrengthModifiers
                = new AttackStrengthModifiers(modifiersMap);
        final AttackModifierChangeByGrade attackModifierChangeByGrade
                = new AttackModifierChangeByGrade(
                        attackStrengthModifiers, new AttackStrengthModifierSimple(1.5d)
        );
        final HeroAction mageActionDefault = new AttackEnemy(
                "Атаковать магией ", 4,
                attackStrengthModifiers, squad2, healthHeroes
        );
        final HeroAction defaultAction = new HeroAction() {
            @Override
            public void act(Hero heroActor) {

            }

            @Override
            public String toString() {
                return "defaultAction";
            }
        };
        final Race humans = new RaceSimple(
                new HeroFactorySimple(
                        "маг",
                        Arrays.asList(
                                new GradeActionSimple(
                                        "Наложение улучшения на персонажа своего отряда",
                                        squad1, random,
                                        mageActionDefault,
                                        new UpgradeAction()
                                ),
                                mageActionDefault
                        ), random
                ),
                new HeroFactorySimple(
                        "арбалетчик",
                        Arrays.asList(
                                new AttackEnemy(
                                        "Стрелять из арбалета в ", 5,
                                        attackStrengthModifiers, squad2, healthHeroes
                                ), new AttackEnemy(
                                        "Атаковать ", 3,
                                        attackStrengthModifiers, squad2, healthHeroes
                                )
                        ), random
                ),
                new HeroFactorySimple(
                        "воин",
                        Collections.singletonList(
                                new AttackEnemy(
                                        "Атаковать мечом ", 18,
                                        attackStrengthModifiers, squad2, healthHeroes
                                )
                        ), random
                )
        );
        Race orcs = new RaceSimple(
                new HeroFactorySimple(
                        "шаман",
                        Arrays.asList(
                                new GradeActionSimple(
                                        "Наложение улучшения на персонажа своего отряда",
                                        squad2, random,
                                        defaultAction,
                                        new UpgradeAction()
                                ),
                                new GradeActionSimple(
                                        "Наложение проклятия (снятие улучшения с персонажа противника для следующего хода)",
                                        squad1, random,
                                        defaultAction,
                                        new DegradeAction()
                                )
                        ), random
                ),
                new HeroFactorySimple(
                        "лучник",
                        Arrays.asList(
                                new AttackEnemy(
                                        "Стрелять из лука в ", 3,
                                        attackStrengthModifiers, squad1, healthHeroes
                                ), new AttackEnemy(
                                        "Удар клинком по ", 2,
                                        attackStrengthModifiers, squad1, healthHeroes
                                )
                        ), random
                ),
                new HeroFactorySimple(
                        "гоблин",
                        Collections.singletonList(
                                new AttackEnemy(
                                        "Атака дубиной по ", 20,
                                        attackStrengthModifiers, squad1, healthHeroes
                                )
                        ), random
                )
        );
        squad1Heroes.addAll(humans.squadHeroes(1, 3, 4, firstSquadName));
        squad1RegularHeroes.addAll(squad1Heroes);
        squad1Heroes.forEach(hero -> {
            heroHealthMap.put(hero, new HealthSimple(dieObservable, hero));
            modifiersMap.put(hero, new HashSet<>());
        });
        squad2Heroes.addAll(orcs.squadHeroes(1, 3, 4, secondSquadName));
        squad2RegularHeroes.addAll(squad2Heroes);
        squad2Heroes.forEach(hero -> {
            heroHealthMap.put(hero, new HealthSimple(dieObservable, hero));
            modifiersMap.put(hero, new HashSet<>());
        });
        dieObservable.addObserver(squad1);
        dieObservable.addObserver(squad2);
        dieObservable.addObserver(healthHeroes);
        dieObservable.addObserver(attackStrengthModifiers);
        movedObservable.addObserver(squad1);
        movedObservable.addObserver(squad2);
        movedObservable.addObserver(attackStrengthModifiers);
        upgradeObservable.addObserver(attackModifierChangeByGrade);
        gameCycle.start(upgradeObservable, dieObservable, movedObservable);
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
