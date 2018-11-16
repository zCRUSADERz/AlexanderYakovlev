package ru.job4j.broker.orders;

/**
 * Интерфейс любого ордера в системе.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 08.03.2018
 */
public interface Order {

    /**
     * Объем ордера.
     * @return - объем.
     */
    int volume();

    /**
     * Проверяет пуст ли ордер.
     * @return - true, if empty.
     */
    boolean isEmpty();

    /**
     * Пустой простой ордер.
     * Замена null.
     */
    // TODO Обдумать вариант использования EmptyOrder для всех подвидов.
    class EmptyOrder implements Order {

        /**
         * Строковое представление ордера.
         * @return - всегда возвращает представление пустого ордера.
         */
        @Override
        public String toString() {
            return "order id: 0, volume: 0";
        }

        /**
         * Объем ордера
         * @return - всегда вернет 0.
         */
        @Override
        public int volume() {
            return 0;
        }

        /**
         * Проверяет пуст ли ордер.
         * @return - всегда true.
         */
        @Override
        public boolean isEmpty() {
            return true;
        }

        // TODO Надо будет обдумать это. Пока не представляю последствия.
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
