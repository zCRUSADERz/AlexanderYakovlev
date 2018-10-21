package ru.job4j.heroes.attack;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.observable.move.HeroMovedObserver;
import ru.job4j.observable.newhero.HeroCreatedObserver;

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

    public AttackStrengthModifiers() {
        this(new HashMap<>());
    }

    public AttackStrengthModifiers(
            Map<Hero, Collection<AttackStrengthModifier>> modifiersMap) {
        this.modifiersMap = modifiersMap;
    }

    /**
     * Добавить новый модификатор урона для героя.
     * @param hero герой, которому назначен новый модификатор урона.
     * @param modifier модификатор урона.
     */
    public void add(Hero hero, AttackStrengthModifier modifier) {
        this.modifiersMap.get(hero).add(modifier);
    }

    /**
     * Удалить модификатор урона для героя.
     * @param hero герой, у которого будет отменен модификатор урона.
     * @param modifier модификатор урона.
     */
    public void remove(Hero hero, AttackStrengthModifier modifier) {
        this.modifiersMap.get(hero).remove(modifier);
    }

    /**
     * Вернет коллекцию модификаторов урона для выбранного героя.
     * @param hero герой, для которого будут возвращены все модификаторы.
     * @return коллекция всех модификаторов для выбранного героя.
     */
    public Collection<AttackStrengthModifier> modifiersFor(Hero hero) {
        return this.modifiersMap.get(hero);
    }

    /**
     * Герой был убит. Удаляем все хранящиеся данные для этого героя.
     * @param hero убитый герой.
     */
    @Override
    public void heroDied(Hero hero) {
        this.modifiersMap.remove(hero);
    }

    /**
     * Герой сделал ход. Все модификаторы урона удаляются.
     * @param hero герой сделавший ход.
     */
    @Override
    public void heroMoved(Hero hero) {
        this.modifiersMap.get(hero).clear();
    }

    /**
     * Герой был создан. Заносим героя в словарь с пустой коллекцией модификаторов.
     * @param hero созданный герой.
     */
    @Override
    public void heroCreated(Hero hero) {
        this.modifiersMap.put(hero, new ArrayList<>());
    }
}
