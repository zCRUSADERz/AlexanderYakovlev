package ru.job4j.broker.store;

import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.type.TypeOrders;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;
import ru.job4j.broker.store.utils.BufferedIterator;
import ru.job4j.broker.store.utils.BufferedOrderIterator;
import ru.job4j.broker.store.utils.OrderDemandById;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * Хранилище ордеров.
 * TODO Требования:
 * 1. Должна быть реализована FIFO структура.
 * 2. Обработка в многопоточном режиме запросов без блокировки в начало списка
 * и в конец.
 * 3. Все новые заявки добавляются в конец очереди.
 * 4. Изымаются из начала очереди(т.е. заявки созданные ранее).
 * 5. Должна быть возможность работать с серединой очереди с блокировкой,
 * которую можно быстро снять.
 * 6. Исходя из всех требований предполагаю возможное решение в применении
 * динамического массива. Заявки изъятые из первых ячеек массива заполняются
 * пустыми ордерами(смещения всего массива не происходит).
 * Для операции get() будет взято первое значение, ячейка массива будет заполнена
 * пустым ордером, инкрементирован индекс указывающий на следующую ячейку.
 *
 * Текущая реализация хранилища не устраивает по многим параметрам.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 11.03.2018
 */
// TODO изменить на структуру позволяющую работать с ней многим потокам
// TODO одновременно в разных местах структуры
public class OrderRepository<T extends TypeOrders> implements TypeOrderRepositories<T>, OrderRepositories {
    private final TypeOrdersWrapper<T> wrapper;
    // TODO перейти на очередь FIFO.
    private final NavigableSet<Order> orders;
    // TODO избавиться от этой mutable переменной!
    private int volume;

    public OrderRepository(TypeOrdersWrapper<T> wrapper) {
        this.wrapper = wrapper;
        this.orders = new TreeSet<>();
        this.volume = 0;
    }

    /**
     * Добавляет простой ордер в хранилище.
     * @param order - добавляемый ордер
     */
    @Override
    public void addOrder(Order order) {
        changeVolume(order.volume());
        orders.add(order);
    }

    /**
     * Удаляет из хранилища и возвращает вызывающему.
     * @return - наименьший ордер из хранилища.
     */
    @Override
    public Order withdrawOrder() {
        Order result = orders.pollFirst();
        if (result == null) {
            result = new Order.EmptyOrder();
        }
        changeVolume(-result.volume());
        return result;
    }

    /**
     * Удаляет ордер эквивалентный переданному, и передает дальше.
     * @param order - ордер, на основе которого будет найден ордер в хранилище.
     * @return - ордер из хранилища, если был найден, или пустой ордер.
     */
    @Override
    public Order withdrawOrder(Order order) {
        Order result = null;
        Iterator<Order> it = orders.iterator();
        while (it.hasNext()) {
            Order o = it.next();
            if (o.equals(order)) {
                result = o;
                changeVolume(-result.volume());
                it.remove();
                break;
            }
        }
        if (result == null) {
            result = new Order.EmptyOrder();
        }
        return result;
    }

    /**
     * Добавляет типизировнный ордер(группа TypeOrders).
     * @param order - типизированный ордер.
     */
    @Override
    public void addOrder(T order) {
        addOrder(order.order());
    }

    /**
     * Удаляет ордер из хранилища на основе типизированного ордера, возвращает
     * ордер с типом группы TypeOrders
     * @param order - типизированный ордер
     * @return - типизированный ордер из хранилища.
     */
    @Override
    public T withdrawOrder(T order) {
        return new OrderDemandById<>(order, this, wrapper).request();
    }

    /**
     * Проверяет пусто ли хранилище.
     * @return - true, если пусто.
     */
    @Override
    public boolean isEmpty() {
        return orders.isEmpty();
    }

    /**
     * Вовзвращает объем всех ордеров в хранилище.
     * @return - объем всех ордеров в хранилище.
     */
    @Override
    public int volume() {
        return this.volume;
    }

    /**
     * Возвращает буфферизированный итератор типизированных ордеров, работающий
     * с этим хранилищем.
     * @return - буфферизированный итератор.
     */
    @Override
    public BufferedIterator<T> iterator() {
        return new BufferedOrderIterator<>(this, wrapper);
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        for (Order order : orders) {
            result.add(order.toString());
        }
        return result.toString();
    }

    /**
     * Change volume.
     * @param change - volume change.
     */
    private void changeVolume(int change) {
        volume += change;
    }
}
