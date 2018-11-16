package ru.job4j.tracker;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Console input.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 2.0
 */
public class ConsoleInput implements Input {
    private Scanner scanner;

    /**
     * Default constructor.
     */
    ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    /**
     * Constructor for testing.
     * @param in - input stream;
     */
    ConsoleInput(InputStream in) {
        scanner = new Scanner(in);
    }
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
    public int ask(String question, List<Integer> range) {
        Integer key = Integer.valueOf(ask(question));
        boolean exist = false;
        for (Integer value : range) {
            if (value.compareTo(key) == 0) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Выберите значение из диапазона меню.");
        }
        return key;
    }
}
