package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.SendAilment;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.squad.Squads;
import ru.job4j.utils.RandomElementFromList;

import java.util.Arrays;
import java.util.Collections;

public class UndeadRaceFactory implements RaceFactory {
    private final Squads squads;
    private final HealthHeroes healthHeroes;
    private final AttackStrengthModifiers attackStrengthModifiers;
    private final RandomElementFromList random;

    public UndeadRaceFactory(Squads squads, HealthHeroes healthHeroes,
                             AttackStrengthModifiers attackStrengthModifiers,
                             RandomElementFromList random) {
        this.squads = squads;
        this.healthHeroes = healthHeroes;
        this.attackStrengthModifiers = attackStrengthModifiers;
        this.random = random;
    }

    @Override
    public Race createRace() {
        return new RaceSimple(
                "Нежить",
                new HeroFactorySimple(
                        "некромант",
                        Arrays.asList(
                                new SendAilment(
                                        this.squads,
                                        new AttackStrengthModifierSimple(0.5d),
                                        this.attackStrengthModifiers
                                ),
                                new AttackEnemy(
                                        "Атаковать ", 5,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                )
                        ), this.random
                ),
                new HeroFactorySimple(
                        "охотник",
                        Arrays.asList(
                                new AttackEnemy(
                                        "Стрелять из лука в ", 4,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                ), new AttackEnemy(
                                        "Атаковать ", 2,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                )
                        ), this.random
                ),
                new HeroFactorySimple(
                        "зомби",
                        Collections.singletonList(
                                new AttackEnemy(
                                        "Атаковать копьем ", 18,
                                        this.attackStrengthModifiers, this.squads, this.healthHeroes
                                )
                        ), this.random
                ),
                this.squads
        );
    }
}
