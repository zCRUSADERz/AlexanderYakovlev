package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

import java.util.Collection;

/**
 * Degrade action.
 * Улучшение персонажа. Персонаж добавляется в привилегированную группу.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class UpgradeAction implements GradeAction {
    private final SquadsMapper squadsMapper;

    public UpgradeAction(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    /**
     * Возвращает коллекцию не привилегированных героев из дружественного отряда.
     * @param heroActor герой выполняющий данное действие.
     * @return коллекция привилегированных героев из дружественного отряда.
     */
    @Override
    public Collection<Hero> gradedHeroes(Hero heroActor) {
        return this.squadsMapper.ownSquadFor(heroActor).regularHeroes();
    }

    /**
     * Улучшает выбранного героя.
     * @param gradedHero герой который будет улучшен.
     */
    @Override
    public void grade(Hero gradedHero) {
        this.squadsMapper.ownSquadFor(gradedHero).upgradeHero(gradedHero);
    }

    /**
     * Возвращает название действия.
     * @return название действия.
     */
    @Override
    public String actionName() {
        return "Наложение улучшения на персонажа своего отряда";
    }
}
