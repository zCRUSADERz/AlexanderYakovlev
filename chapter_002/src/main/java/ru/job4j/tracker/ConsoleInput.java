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
}
