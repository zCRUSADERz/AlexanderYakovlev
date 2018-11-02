package ru.job4j;

import ru.job4j.heroes.attack.AttackModifierChangeByGrade;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.heroes.health.HealthHeroesSimple;
import ru.job4j.observable.die.HeroDiedObservable;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.observable.move.HeroMovedObservable;
import ru.job4j.observable.newhero.HeroCreatedObservable;
import ru.job4j.squad.SquadsMapper;
import ru.job4j.squad.SquadsMapperSimple;
import ru.job4j.utils.RandomElementFromList;

public class GameEnvironmentInitialize {
    private final HeroCreatedObservable createdObservable;
    private final HeroMovedObservable movedObservable;
    private final GradeChangeObservable upgradeObservable;
    private final HeroDiedObservable diedObservable;

    public GameEnvironmentInitialize(HeroCreatedObservable createdObservable,
                           HeroMovedObservable movedObservable,
                           GradeChangeObservable upgradeObservable,
                           HeroDiedObservable diedObservable) {
        this.createdObservable = createdObservable;
        this.movedObservable = movedObservable;
        this.upgradeObservable = upgradeObservable;
        this.diedObservable = diedObservable;
    }

    public GameEnvironment init() {
        return new GameEnvironment(
                createStopGame(),
                createSquads(),
                createHeroesHealths(),
                createAttackModifiers(),
                createRandomize()
        );
    }

    /**
     * @return стоп игра.
     */
    private StopGame createStopGame() {
        return new StopGameSimple();
    }

    /**
     * Создает контроллер отряов.
     * @return контроллер отрядов.
     */
    private SquadsMapper createSquads() {
        final SquadsMapper squadsMapper
                = new SquadsMapperSimple(this.createdObservable);
        this.diedObservable.addObserver(squadsMapper);
        this.movedObservable.addObserver(squadsMapper);
        return squadsMapper;
    }

    /**
     * Создает хранилище здоровья всех героев.
     * @return хранилище здоровья всех героев.
     */
    private HealthHeroes createHeroesHealths() {
        final HealthHeroes healthHeroes
                = new HealthHeroesSimple(this.diedObservable);
        this.diedObservable.addObserver(healthHeroes);
        this.createdObservable.addObserver(healthHeroes);
        return healthHeroes;
    }

    /**
     * Создает хранилище модификаторов атаки всех героев.
     * @return хранилище модификаторов атаки всех героев.
     */
    private AttackStrengthModifiers createAttackModifiers() {
        final AttackStrengthModifiers modifiers = new AttackStrengthModifiers();
        this.diedObservable.addObserver(modifiers);
        this.movedObservable.addObserver(modifiers);
        this.createdObservable.addObserver(modifiers);
        this.initAttackModifiersByGrade(modifiers);
        return modifiers;
    }

    /**
     * Инициализирует объект наблюдающий изменения привилегий героев.
     * @param modifiers модификаторы урона героев.
     */
    private void initAttackModifiersByGrade(AttackStrengthModifiers modifiers) {
        this.upgradeObservable.addObserver(
                new AttackModifierChangeByGrade(
                        modifiers,
                        new AttackStrengthModifierSimple(1.5d)
                )
        );
    }

    /**
     * @return рандомайзер.
     */
    private RandomElementFromList createRandomize() {
        return new RandomElementFromList();
    }
}
