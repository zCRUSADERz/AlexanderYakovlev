package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.DefaultAction;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.grade.DegradeAction;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.squad.Squads;
import ru.job4j.utils.RandomElementFromList;

import java.util.Arrays;
import java.util.Collections;

public class OrcsRaceFactory implements RaceFactory {
    private final Squads squads;
    private final HealthHeroes healthHeroes;
    private final AttackStrengthModifiers attackStrengthModifiers;
    private final RandomElementFromList random;

    public OrcsRaceFactory(Squads squads, HealthHeroes healthHeroes,
                           AttackStrengthModifiers attackStrengthModifiers,
                           RandomElementFromList random) {
        this.squads = squads;
        this.healthHeroes = healthHeroes;
        this.attackStrengthModifiers = attackStrengthModifiers;
        this.random = random;
    }

    @Override
    public Race createRace() {
        final HeroAction defaultAction = new DefaultAction();
        return new RaceSimple(
                "Орки",
                new HeroFactorySimple(
                        "шаман",
                        Arrays.asList(
                                new GradeActionSimple(
                                        this.random,
                                        defaultAction,
                                        new UpgradeAction(this.squads)
                                ),
                                new GradeActionSimple(
                                        random,
                                        defaultAction,
                                        new DegradeAction(this.squads)
                                )
                        ), this.random
                ),
                new HeroFactorySimple(
                        "лучник",
                        Arrays.asList(
                                new AttackEnemy(
                                        "Стрелять из лука в ", 3,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                ), new AttackEnemy(
                                        "Удар клинком по ", 2,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                )
                        ), this.random
                ),
                new HeroFactorySimple(
                        "гоблин",
                        Collections.singletonList(
                                new AttackEnemy(
                                        "Атака дубиной по ", 20,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                )
                        ), this.random
                ),
                this.squads
        );
    }
}
