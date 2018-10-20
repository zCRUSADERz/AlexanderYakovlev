package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.squad.Squads;
import ru.job4j.utils.RandomElementFromList;

import java.util.Arrays;
import java.util.Collections;

public class HumansRaceFactory implements RaceFactory {
    private final Squads squads;
    private final HealthHeroes healthHeroes;
    private final AttackStrengthModifiers attackStrengthModifiers;
    private final RandomElementFromList random;

    public HumansRaceFactory(Squads squads, HealthHeroes healthHeroes,
                             AttackStrengthModifiers attackStrengthModifiers,
                             RandomElementFromList random) {
        this.squads = squads;
        this.healthHeroes = healthHeroes;
        this.attackStrengthModifiers = attackStrengthModifiers;
        this.random = random;
    }

    @Override
    public Race createRace() {
        final HeroAction mageActionDefault = new AttackEnemy(
                "Атаковать магией ", 4,
                this.attackStrengthModifiers, this.squads, this.healthHeroes
        );
        return new RaceSimple(
                "Люди",
                new HeroFactorySimple(
                        "маг",
                        Arrays.asList(
                                new GradeActionSimple(
                                        this.random,
                                        mageActionDefault,
                                        new UpgradeAction(this.squads)
                                ),
                                mageActionDefault
                        ), this.random
                ),
                new HeroFactorySimple(
                        "арбалетчик",
                        Arrays.asList(
                                new AttackEnemy(
                                        "Стрелять из арбалета в ", 5,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                ), new AttackEnemy(
                                        "Атаковать ", 3,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                )
                        ), this.random
                ),
                new HeroFactorySimple(
                        "воин",
                        Collections.singletonList(
                                new AttackEnemy(
                                        "Атаковать мечом ", 18,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                )
                        ), this.random
                ),
                this.squads
        );
    }
}
