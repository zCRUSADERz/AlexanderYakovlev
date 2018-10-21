package ru.job4j.races;

/**
 * Race factory.
 * Фабрика расы.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface RaceFactory {

    /**
     * Создать расу.
     * @return созданная раса.
     */
    Race createRace();
}
