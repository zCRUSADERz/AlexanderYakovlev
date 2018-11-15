package ru.job4j.heroes.attack;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.observers.HeroMovedObserver;
import ru.job4j.observers.HeroCreatedObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Attack strength modifiers.
 * Коллекция модификаторов силы урона для каждого героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class AttackStrengthModifiers implements HeroDiedObserver, HeroMovedObserver, HeroCreatedObserver {
    private final Map<Hero, Collection<AttackStrengthModifier>> modifiersMap;
    private final Logger logger;

    public AttackStrengthModifiers() {
        this(
                new HashMap<>(),
                Logger.getLogger(AttackStrengthModifiers.class)
        );
    }

    public AttackStrengthModifiers(
            Map<Hero, Collection<AttackStrengthModifier>> modifiersMap,
            Logger logger) {
        this.modifiersMap = modifiersMap;
        this.logger = logger;
    }

    /**
     * Добавить новый модификатор урона для героя.
     * @param hero герой, которому назначен новый модификатор урона.
     * @param modifier модификатор урона.
     */
    public void add(Hero hero, AttackStrengthModifier modifier) {
        this.checkHeroExist(hero);
        final Collection<AttackStrengthModifier> modifiers
                = this.modifiersMap.get(hero);
        modifiers.add(modifier);
        this.logger.info(String.format(
                "%s received attack modifier: %s. All modifiers: %s.",
                hero, modifier, modifiers
        ));
    }

    /**
     * Удалить модификатор урона для героя.
     * @param hero герой, у которого будет отменен модификатор урона.
     * @param modifier модификатор урона.
     */
    public void remove(Hero hero, AttackStrengthModifier modifier) {
        this.checkHeroExist(hero);
        final Collection<AttackStrengthModifier> modifiers
                = this.modifiersMap.get(hero);
        modifiers.remove(modifier);
        this.logger.info(String.format(
                "%s lose attack modifier: %s. All modifiers: %s.",
                hero, modifier, modifiers
        ));
    }

    /**
     * Применит все модификаторы героя для переданного урона,
     * вернет урон с учетом модификаторов атаки.
     * @param hero герой модификаторы которого будут применены к переданному урону.
     * @param damage изначальный урон.
     * @return урон с учетом модификаторов атаки.
     */
    public int applyModifiersFor(Hero hero, int damage) {
        this.checkHeroExist(hero);
        int resultDamage = damage;
        final Collection<AttackStrengthModifier> modifiers
                = this.modifiersMap.get(hero);
        for (AttackStrengthModifier modifier : modifiers) {
            resultDamage = modifier.damage(resultDamage);
        }
        this.logger.info(String.format(
                "%s, applying attack modifiers for damage: %d. "
                        + "Result damage: %d. Modifiers: %s.",
                hero, damage, resultDamage, modifiers
        ));
        return resultDamage;
    }

    /**
     * Герой был убит. Удаляем все хранящиеся данные для этого героя.
     * @param hero убитый герой.
     */
    @Override
    public void heroDied(Hero hero) {
        this.checkHeroExist(hero);
        this.modifiersMap.remove(hero);
    }

    /**
     * Герой сделал ход. Все модификаторы урона удаляются.
     * @param hero герой сделавший ход.
     */
    @Override
    public void heroMoved(Hero hero) {
        this.checkHeroExist(hero);
        this.modifiersMap.get(hero).clear();
    }

    /**
     * Герой был создан. Заносим героя в словарь с пустой коллекцией модификаторов.
     * @param hero созданный герой.
     */
    @Override
    public void heroCreated(Hero hero) {
        if (this.modifiersMap.containsKey(hero)) {
            throw new IllegalStateException(String.format(
                    "%s already have its attack modifiers.", hero
            ));
        }
        this.modifiersMap.put(hero, new ArrayList<>());
    }

    private void checkHeroExist(Hero hero) {
        if (!this.modifiersMap.containsKey(hero)) {
            throw new IllegalStateException(String.format(
                    "%s don't have its attack modifiers.", hero
            ));
        }
    }

    @Override
    public String toString() {
        return "Attack strength modifiers";
    }
}
