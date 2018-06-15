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
        @Override
        public int x() {
            return 0;
        }

        @Override
        public int y() {
            return 1;
        }
    },

    /**
     * Движение влево.
     */
    LEFT {
        @Override
        public int x() {
            return -1;
        }

        @Override
        public int y() {
            return 0;
        }
    },

    /**
     * Движение вправо.
     */
    RIGHT {
        @Override
        public int x() {
            return 1;
        }

        @Override
        public int y() {
            return 0;
        }
    },

    /**
     * Движение вниз.
     */
    DOWN {
        @Override
        public int x() {
            return 0;
        }

        @Override
        public int y() {
            return -1;
        }
    };

    /**
     * Изменение Х координаты в определенном направлении.
     * @return изменение X координаты.
     */
    public abstract int x();

    /**
     * Изменение Y координаты в определенном направлении.
     * @return изменение Y координаты.
     */
    public abstract int y();
}
