package ru.job4j.squad;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.observable.move.HeroMovedObserver;

import java.util.Collection;

/**
 * Squad mapper.
 * Контроллер отрядов.
 * Знает какой отряд для героя вражеский или дружественный.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface SquadsMapper extends HeroDiedObserver, HeroMovedObserver {

    /**
     * Возвращает все отряды.
     * @return все отряды.
     */
    Collection<SquadHeroes> allSquads();

    /**
     * Собственный отряд для выбранного героя.
     * @param hero выбранный герой.
     * @return собственный отряд для выбранного героя.
     */
    SquadHeroes ownSquadFor(Hero hero);

    /**
     * Вражеский отряд для выбранного героя.
     * @param hero выбранный герой.
     * @return вражеский отряд для выбранного героя.
     */
    SquadHeroes enemySquadFor(Hero hero);

    /**
     * Был создан новый герой. Будет создана карта отрядов для нового героя.
     * @param hero новый герой.
     * @param ownSquad собственный отряд.
     * @param enemySquad вражеский отряд.
     */
    void newHeroCreated(Hero hero, SquadHeroes ownSquad, SquadHeroes enemySquad);
}
