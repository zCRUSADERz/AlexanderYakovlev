package ru.job4j.bomberman;

/**
 * Направления движения героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public enum DirectionOfMove {

    /**
     * Движение вверх.
     */
    UP {
        public int x() {
            return 0;
        }

        public int y() {
            return 1;
        }
    },

    /**
     * Движение влево.
     */
    LEFT {
        public int x() {
            return -1;
        }

        public int y() {
            return 0;
        }
    },

    /**
     * Движение вправо.
     */
    RIGHT {
        public int x() {
            return 1;
        }

        public int y() {
            return 0;
        }
    },

    /**
     * Движение вниз.
     */
    DOWN {
        public int x() {
            return 0;
        }

        public int y() {
            return -1;
        }
    };

    /**
     * Изменение Х координаты в определенном направлении.
     * @return - изменение X координаты.
     */
    public abstract int x();

    /**
     * Изменение Y координаты в определенном направлении.
     * @return - изменение Y координаты.
     */
    public abstract int y();
}
