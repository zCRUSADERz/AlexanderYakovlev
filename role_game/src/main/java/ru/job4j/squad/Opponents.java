package ru.job4j.squad;

import ru.job4j.heroes.HeroType;

import java.util.Map;

/**
 * Opponents.
 * Противоборствующие стороны. Две расы воюющих друг с другом.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface Opponents {

    /**
     * Создать отряды противоборствующих сторон, подготовить к бою.
     * @param numberOfHeroesByType количество героев каждого типа.
     */
    void createSquads(Map<HeroType, Integer> numberOfHeroesByType);
}
