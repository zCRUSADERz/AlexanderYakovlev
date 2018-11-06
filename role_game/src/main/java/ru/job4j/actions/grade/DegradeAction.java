package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

/**
 * Degrade action.
 * Снятие улучшения с персонажа. Персонаж удаляется из привилегированной группы.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class DegradeAction implements GradeAction {
    private final SquadsMapper squadsMapper;

    public DegradeAction(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    /**
     * Снимает улучшение с выбранного героя.
     * @param gradedHero герой с которого будет снято улучшение.
     */
    @Override
    public void grade(Hero gradedHero) {
        this.squadsMapper.ownSquadFor(gradedHero).degradeHero(gradedHero);
    }

    /**
     * Возвращает название действия.
     * @return название действия.
     */
    @Override
    public String actionName() {
        return "Наложение проклятия "
                + "(снятие улучшения с персонажа противника для следующего хода)";
    }
}
