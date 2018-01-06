package ru.job4j.tracker;

import java.util.Scanner;

/**
 * Console input.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 1.0
 */
public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Ask user.
     * @param question - question for user.
     * @return - answer.
     */
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    /**
     * Ask user valid answer.
     * @param question - question.
     * @param range - range of key menu.
     * @return - key menu.
     */
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Выберите значение из диапазона меню.");
        }
    }
}
