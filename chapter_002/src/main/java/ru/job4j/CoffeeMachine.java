package ru.job4j;

import java.util.ArrayList;

/**
 * Coffe machine.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.01.2017
 * @version 1.0
 */
public class CoffeeMachine {
    private final int[] coins = new int[] {1, 2, 5, 10};

    /**
     * Calculates and issues change.
     * @param value - money.
     * @param price - price.
     * @return - array of coins.
     */
    int[] changes(int value, int price) {
        ArrayList<Integer> list = new ArrayList<>();
        int[] result = new int[0];
        int change = value - price;
        if (change > 0) {
            for (int i = coins.length - 1; i >= 0; i--) {
                while (true) {
                    int coin = coins[i];
                    if (change >= coin) {
                        list.add(coin);
                        change -= coin;
                    } else {
                        break;
                    }
                }
                if (change == 0) {
                    break;
                }
            }
            result = new int[list.size()];
            int i = 0;
            for (Integer coin : list) {
                result[i++] = coin;
            }
        }
        return result;
    }
}
