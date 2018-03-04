package ru.job4j.broker.order;

import ru.job4j.broker.TypeOrder;

import java.util.Objects;

/**
 * SimpleOrder.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public class SimpleOrder {
    private final int id;
    private final TypeOrder type;
    private final int price;
    private final int volume;

    public SimpleOrder(int id, TypeOrder type, int price, int volume) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.volume = volume;
    }

    /**
     * Order type.
     * @return - type.
     */
    public TypeOrder type() {
        return type;
    }

    /**
     * Order price.
     * @return - price.
     */
    public int price() {
        return price;
    }

    /**
     * Order volume.
     * @return - volume.
     */
    public int volume() {
        return volume;
    }

    /**
     * Deletes a registered order with the same type, price, id.
     * Other order subtracts from the registered order volume and returns a new order.
     * @param other - delete order.
     * @return - new order.
     */
    public SimpleOrder delete(SimpleOrder other) {
        checkId(other.id);
        checkPrice(other.price);
        checkEqualsType(other.type);
        int newVolume = 0;
        if (this.volume > other.volume) {
            newVolume = this.volume - other.volume;
        }
        return new SimpleOrder(this.id, this.type, this.price, newVolume);
    }

    /**
     * Execution of two bids for the purchase and sale.
     * @param other - other order.
     * @return - new order, if the orders have a different volume.
     * A new order will take the id of a order with a large volume.
     */
    public SimpleOrder execute(SimpleOrder other) {
        checkPrice(other.price);
        checkNonEqualsType(other.type);
        SimpleOrder result;
        if (this.volume >= other.volume) {
            result = new SimpleOrder(this.id, this.type, this.price,
                    this.volume - other.volume
            );
        } else {
            result = new SimpleOrder(other.id, other.type, other.price,
                    other.volume - this.volume
            );
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "%s order id: %d, price: %d, volume: %d",
                type, id, price, volume
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleOrder order = (SimpleOrder) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void checkId(int other) {
        if (this.id != other) {
            throw new IllegalArgumentException(String.format(
                    "Other order have another id: %d, this.id: %d",
                    other, this.id
            ));
        }
    }

    private void checkNonEqualsType(TypeOrder other) {
        if (this.type == other) {
            throw new IllegalArgumentException(String.format(
                    "The order types must not be the same. "
                            + "This type: %s, other: %s",
                    this.type, other
            ));
        }
    }

    private void checkEqualsType(TypeOrder other) {
        if (this.type != other) {
            throw new IllegalArgumentException(String.format(
                    "The order types must be the same. "
                            + "This type: %s, other: %s",
                    this.type, other
            ));
        }
    }

    private void checkPrice(int price) {
        if (this.price != price) {
            throw new IllegalArgumentException(String.format(
                    "The bid prices must be the same. "
                            + "Price this: %d, other: %d",
                    this.price, price
            ));
        }
    }
}
