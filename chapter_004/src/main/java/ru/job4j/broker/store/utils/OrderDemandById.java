package ru.job4j.broker.store.utils;

import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.OrderPartition;
import ru.job4j.broker.orders.type.TypeOrders;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;
import ru.job4j.broker.store.OrderRepositories;

import java.util.Iterator;

/**
 * Получает из хранилища ордер с таким же Id как и у requestedOrder.
 * В случае если требуемый ордер имеет меньший объем, чем у полученного,
 * то в хранилище будет возвращен новый ордер с остатком объема.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 06.03.2018
 */
// TODO При изменении структуры NavigableSet в будущем, нужно будет реализовать
// TODO возврат ордера на то место где он был взят.
public class OrderDemandById<T extends TypeOrders> {
    private final T requestedOrder;
    private final OrderRepositories orderRepository;
    private final TypeOrdersWrapper<T> wrapper;

    public OrderDemandById(T requestedOrder, OrderRepositories orderRepository,
                           TypeOrdersWrapper<T> wrapper) {
        this.requestedOrder = requestedOrder;
        this.orderRepository = orderRepository;
        this.wrapper = wrapper;
    }

    /**
     * Получает ордер из хранилища основываясь на переданном ему ордере.
     * Возвращает ордер из хранилища с тем же Id. Если ордер из хранилища имеет
     * больший объем чем запрашиваемый, то будет создан новый ордер с лишним
     * объемом и возвращен на прежнее место в хранилище.
     * @return - пустой ордер, если запрашиваемый ордер не найден в хранилище;
     * Ордер с меньшим или таким же объемом, что и запрашиваемый.
     */
    public T request() {
        T result;
        Order orderFromStore = orderRepository.withdrawOrder(requestedOrder.order());
        if (!orderFromStore.isEmpty()) {
            int storeOrderVolume = orderFromStore.volume();
            int requestedVolume = requestedOrder.volume();
            if (storeOrderVolume > requestedVolume) {
                OrderPartition partition = new OrderPartition(orderFromStore, requestedVolume);
                Iterator<Order> iterator = partition.divide();
                result = wrapper.wrap(iterator.next());
                orderFromStore = iterator.next();
                orderRepository.addOrder(orderFromStore);
            } else if (storeOrderVolume == requestedVolume) {
                result = requestedOrder;
            } else {
                result = wrapper.wrap(orderFromStore);
            }
        } else {
            result = wrapper.wrap(orderFromStore);
        }
        return result;
    }
}
