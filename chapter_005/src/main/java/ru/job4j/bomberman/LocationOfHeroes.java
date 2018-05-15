package ru.job4j.bomberman;

import java.util.concurrent.ConcurrentMap;

/**
 * Местоположение всех игровых персонажей на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class LocationOfHeroes {
    /**
     * Местоположение задается уникальной строкой для всех игровых персонажей.
     */
    private final ConcurrentMap<String, BoardPosition> positions;

    public LocationOfHeroes(ConcurrentMap<String, BoardPosition> positions) {
        this.positions = positions;
    }

    /**
     * Возвращает местоположение персонажа по идентификационной строке.
     * @param identificationString идентификационная строка.
     * @return местоположение персонажа.
     */
    public BoardPosition heroPosition(String identificationString) {
        BoardPosition result = positions.get(identificationString);
        if (result == null) {
            invalidIdentificationString(identificationString);
        }
        return result;
    }

    /**
     * Устанавливает новое местоположение персонажа.
     * @param identificationString идентификационная строка.
     * @param position местоположение персонажа.
     */
    public void newHeroPosition(String identificationString, BoardPosition position) {
        BoardPosition result = positions.put(identificationString, position);
        if (result == null) {
            invalidIdentificationString(identificationString);
        }
    }

    /**
     * Если идентификационная строка не найдена, будет выброшенно исключение.
     * @param identificationString идентификационная строка.
     */
    private void invalidIdentificationString(String identificationString) {
        throw new IllegalArgumentException(
                    "A hero with an identification string: "
                            + identificationString + ", does not exist"
        );
    }
}
