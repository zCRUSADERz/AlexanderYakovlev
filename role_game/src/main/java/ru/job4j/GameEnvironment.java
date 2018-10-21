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

/**
 * Game environment.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class GameEnvironment {
    private final HeroCreatedObservable createdObservable;
    private final HeroMovedObservable movedObservable;
    private final GradeChangeObservable upgradeObservable;
    private final HeroDiedObservable diedObservable;

    public GameEnvironment(HeroCreatedObservable createdObservable,
                           HeroMovedObservable movedObservable,
                           GradeChangeObservable upgradeObservable,
                           HeroDiedObservable diedObservable) {
        this.createdObservable = createdObservable;
        this.movedObservable = movedObservable;
        this.upgradeObservable = upgradeObservable;
        this.diedObservable = diedObservable;
    }

    /**
     * Создает контроллер отряов.
     * @return контроллер отрядов.
     */
    public SquadsMapper createSquads() {
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
    public HealthHeroes createHeroesHealths() {
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
    public AttackStrengthModifiers createAttackModifiers() {
        final AttackStrengthModifiers modifiers = new AttackStrengthModifiers();
        this.diedObservable.addObserver(modifiers);
        this.movedObservable.addObserver(modifiers);
        this.createdObservable.addObserver(modifiers);
        this.initAttackModifiersByGrade(modifiers);
        return modifiers;
    }

    /**
     * @return стоп игра.
     */
    public StopGame createStopGame() {
        return new StopGameSimple();
    }

    /**
     * @return рандомайзер.
     */
    public RandomElementFromList createRandomize() {
        return new RandomElementFromList();
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
}
