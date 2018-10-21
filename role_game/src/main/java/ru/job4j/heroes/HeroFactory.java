package ru.job4j.heroes;

/**
 * Hero factory.
 * Фабрика героев.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface HeroFactory {

    /**
     * Вернет нового героя.
     * @param squadName название отряда.
     * @param raceName название расы.
     * @return новый герой.
     */
    Hero hero(String squadName, String raceName);
}
