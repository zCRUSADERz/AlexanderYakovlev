package ru.job4j.broker.orders;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Делитель простых ордеров.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 08.03.2018
 */
public class OrderPartition {
    private final Order originOrder;
    private final int requiredVolume;

    public OrderPartition(Order originOrder, int requiredVolume) {
        this.originOrder = originOrder;
        this.requiredVolume = requiredVolume;
    }

    /**
     * Делит оригинальный ордер на два новых ордера, у которых различается только
     * объем.
     * @return - итератор с ДВУМЯ ордерами внутри. Первый ордер имеет запрашиваемый
     * объем, второй разницу между запрашиваемым и доступным.
     * @throws IllegalStateException - если требуемый объем больше объема ордера
     * или если запрашиваемый объем отрицателен.
     */
    public Iterator<Order> divide() {
        int originVolume = originOrder.volume();
        if (this.requiredVolume > originVolume) {
            throw new IllegalStateException(String.format(
                    "The original order does not have the required volume.%n"
                            + "Order volume: %d, required: %d",
                    originVolume, requiredVolume
            ));
        }
        if (this.requiredVolume < 0) {
            throw new IllegalStateException(String.format(
                    "The requested volume can not be negative. Required volume: %d",
                    requiredVolume
            ));
        }
        Order newOrigin = new SimpleOrder((SimpleOrder) originOrder, requiredVolume);
        Order extraOrigin = new SimpleOrder((SimpleOrder) originOrder, originVolume - requiredVolume);
        return Arrays.asList(newOrigin, extraOrigin).iterator();
    }
}
