package ru.job4j.broker.store.utils;

import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.OrderPartition;
import ru.job4j.broker.orders.type.TypeOrders;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;
import ru.job4j.broker.store.OrderRepositories;

import java.util.*;

/**
 * Итератор ордеров с накоплением в буфер.
 * Накапливает простые ордера в буфер, и по команде receive() возвращает все
 * ордера. Если указан объем (receive(volume)), то из буфера будут выданы
 * ордера с общим объемом равным запрашиваемому. Лишние ордера в буфере будут
 * немедленно возвращены в хранилище.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 08.03.2018
 */
public class BufferedOrderIterator<T extends TypeOrders> implements BufferedIterator<T> {
    private final OrderRepositories store;
    private final TypeOrdersWrapper<T> wrapper;
    private final List<Order> list;
    private int volume;

    public BufferedOrderIterator(OrderRepositories store, TypeOrdersWrapper<T> wrapper) {
        this.store = store;
        this.wrapper = wrapper;
        this.list = new LinkedList<>();
        this.volume = 0;
    }

    /**
     * Проверяет есть ли еще элементы в хранилище.
     * @return - false, если хранилище пусто.
     */
    @Override
    public boolean storeIsEmpty() {
        return this.store.isEmpty();
    }

    /**
     * Взять еще один ордер из хранилища.
     */
    @Override
    public void takeNext() {
        Order result = this.store.withdrawOrder();
        if (!result.isEmpty()) {
            this.volume += result.volume();
            this.list.add(result);
        }
    }

    /**
     * Общий объем ордеров накопленных в буфере.
     * @return - объем ордеров в буфере.
     */
    @Override
    public int volume() {
        return this.volume;
    }

    /**
     * Получить объем requiredVolume ордеров из буфера.
     * @param requiredVolume - требуемый объем ордеров.
     * @return - список ордеров.
     */
    @Override
    public Collection<T> receive(int requiredVolume) {
        checkRequiredVolume(requiredVolume);
        List<Order> result = new ArrayList<>();
        int resultVolume = 0;
        ListIterator<Order> it = list.listIterator();
        while (it.hasNext() && resultVolume != requiredVolume) {
            Order order = it.next();
            int orderVolume = order.volume();
            int sum = resultVolume + orderVolume;
            if (requiredVolume < sum) {
                int newVolume = requiredVolume - resultVolume;
                OrderPartition partition = new OrderPartition(
                        order, newVolume
                );
                Iterator<Order> iterator = partition.divide();
                result.add(iterator.next());
                it.set(iterator.next());
                break;
            } else {
                result.add(order);
                it.remove();
                resultVolume = sum;
            }
        }
        returnExtraOrders();
        return packOrders(result);
    }

    private Collection<T> packOrders(List<Order> list) {
        Collection<T> result = new ArrayList<>();
        for (Order  order : list) {
            result.add(wrapper.wrap(order));
        }
        return result;
    }

    private void returnExtraOrders() {
        ListIterator<Order> it = list.listIterator(list.size());
        while (it.hasPrevious()) {
            store.returnOrder(it.previous());
        }
        list.clear();
    }

    private void checkRequiredVolume(int requiredVolume) {
        if (requiredVolume > this.volume) {
            throw new IllegalStateException(String.format(
                    "The buffer order does not have the required volume.%n"
                            + "Buffer volume: %d, required: %d",
                    this.volume, requiredVolume
            ));
        }
        if (requiredVolume < 0) {
            throw new IllegalStateException(String.format(
                    "The requested volume can not be negative. Required volume: %d",
                    requiredVolume
            ));
        }
    }
}
