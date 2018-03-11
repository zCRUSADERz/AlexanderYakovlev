package ru.job4j.broker.orders;

import java.util.Date;
import java.util.Objects;

/**
 * Простой ордер.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 08.03.2018
 */
public class SimpleOrder implements Order, Comparable<SimpleOrder> {
    /**
     * Уникальный Id ордера в системе.
     */
    private final int id;
    private final int volume;
    // TODO Это поле должно быть чисто информационным!
    // TODO Но сначала обеспечить уникальность Id и их выдачу по порядку.
    private final long created;

    /**
     * Стандартный конструктор.
     * @param id - уникальный идентификатор ордера.
     * @param volume - объем ордера.
     */
    public SimpleOrder(int id, int volume) {
        this(id, volume, new Date().getTime());
    }

    /**
     * Конструктор на основе другого ордера, но с новым объемом.
     * @param other - другой простой ордер.
     * @param volume - новый объем.
     */
    public SimpleOrder(SimpleOrder other, int volume) {
        this(other.id, volume, other.created);
    }

    /**
     * Основной конструктор.
     * Private - мы не должны давать возможность задавать любое время создания.
     * @param id - уникальный идентификатор ордера.
     * @param volume - объем ордера.
     * @param created - дата создания.
     */
    private SimpleOrder(int id, int volume, long created) {
        this.id = id;
        this.volume = volume;
        this.created = created;
    }

    /**
     * Объем ордера.
     * @return - объем.
     */
    @Override
    public int volume() {
        return volume;
    }

    /**
     * Проверяет пуст ли ордер.
     * @return - если volume == 0, то вернет true.
     */
    @Override
    public boolean isEmpty() {
        boolean result = false;
        if (volume == 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleOrder that = (SimpleOrder) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    /**
     * Сравнивает ордера по дате создания, если равны, то сравнивает по id.
     * @param o - другой простой объект.
     * @return - результат сравнения.
     */
    @Override
    public int compareTo(SimpleOrder o) {
        int result = 1;
        if (this.created < o.created) {
            result = -1;
        } else if (this.created == o.created) {
            result = Integer.compare(this.id, o.id);
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "order id: %d, volume: %d",
                id, volume
        );
    }
}
