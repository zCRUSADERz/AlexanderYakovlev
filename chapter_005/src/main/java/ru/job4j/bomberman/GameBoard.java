package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;

/**
 * Игровая доска.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public interface GameBoard {

    /**
     * Попытаться двигаться в заданном направлении.
     * @param identificationString идентификационная строка персонажа, который
     *                             желает передвинуться в двругое место на доске.
     * @param direction направление движения.
     * @param timeout длительность попытки.
     * @param unit timeUnit.
     * @return - true, если удалось передвинуться.
     * @throws InterruptedException, если поток был прерван.
     */
    boolean tryMove(String identificationString, DirectionOfMove direction,
                           long timeout, TimeUnit unit) throws InterruptedException;

    /**
     * Инициализировать персонажа на игровой доске. Вызывается единожды, перед
     * тем как передвинуться на игровой доске.
     * @param identificationString - идентификационная строка персонажа.
     */
    void initHero(String identificationString);
}
