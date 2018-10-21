package ru.job4j.squad;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.newhero.HeroCreatedObservable;

import java.util.*;

/**
 * Squad mapper.
 * Контроллер отрядов.
 * Знает какой отряд для героя вражеский или дружественный.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class SquadsMapperSimple implements SquadsMapper {
    private final Set<SquadHeroes> squads;
    private final Map<Hero, SquadHeroes> ownSquads;
    private final Map<Hero, SquadHeroes> enemySquads;
    private final HeroCreatedObservable heroCreatedObservable;

    public SquadsMapperSimple(HeroCreatedObservable heroCreatedObservable) {
        this(
                new HashSet<>(),
                new HashMap<>(),
                new HashMap<>(),
                heroCreatedObservable
        );
    }

    public SquadsMapperSimple(Set<SquadHeroes> squads,
                              Map<Hero, SquadHeroes> ownSquads,
                              Map<Hero, SquadHeroes> enemySquads,
                              HeroCreatedObservable heroCreatedObservable) {
        this.squads = squads;
        this.ownSquads = ownSquads;
        this.enemySquads = enemySquads;
        this.heroCreatedObservable = heroCreatedObservable;
    }

    /**
     * Возвращает все отряды.
     * @return все отряды.
     */
    @Override
    public Collection<SquadHeroes> allSquads() {
        return this.squads;
    }

    /**
     * Собственный отряд для выбранного героя.
     * @param hero выбранный герой.
     * @return собственный отряд для выбранного героя.
     */
    @Override
    public SquadHeroes enemySquadFor(Hero hero) {
        return this.enemySquads.get(hero);
    }

    /**
     * Вражеский отряд для выбранного героя.
     * @param hero выбранный герой.
     * @return вражеский отряд для выбранного героя.
     */
    @Override
    public SquadHeroes ownSquadFor(Hero hero) {
        return this.ownSquads.get(hero);
    }

    /**
     * Был создан новый герой. Будет создана карта отрядов для нового героя.
     * Будет оповещен собственный отряд героя,
     * а так же наблюдатели данного события.
     * @param hero новый герой.
     * @param ownSquad собственный отряд.
     * @param enemySquad вражеский отряд.
     */
    @Override
    public void newHeroCreated(Hero hero, SquadHeroes ownSquad,
                               SquadHeroes enemySquad) {
        this.ownSquads.put(hero, ownSquad);
        this.enemySquads.put(hero, enemySquad);
        this.squads.add(ownSquad);
        this.squads.add(enemySquad);
        ownSquad.heroCreated(hero);
        this.heroCreatedObservable.heroCreated(hero);
    }

    /**
     * Герой был убит. Будет оповещен дружественный отряд героя
     * и зачищены ссылки на этого героя.
     * @param hero убитый герой.
     */
    @Override
    public void heroDied(Hero hero) {
        this.ownSquads.get(hero).heroDied(hero);
        this.ownSquads.remove(hero);
        this.enemySquads.remove(hero);
    }

    /**
     * Герой сделал ход. Будет оповещен дружественный отряд героя.
     * @param hero герой сделавший ход.
     */
    @Override
    public void heroMoved(Hero hero) {
        this.ownSquads.get(hero).heroMoved(hero);
    }
}
