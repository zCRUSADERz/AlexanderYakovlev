package ru.job4j.tracker;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Input from supplier.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 17.11.2017
 */
public class InputFromSupplier implements Input {
    private final Supplier<String> scanner;
    private final Consumer<String> writer;

    InputFromSupplier() {
        this(new Scanner(System.in)::nextLine, System.out::print);
    }

    InputFromSupplier(Supplier<String> scanner, Consumer<String> writer) {
        this.scanner = scanner;
        this.writer = writer;
    }

    /**
     * Ask user.
     * @param question - question for user.
     * @return - answer.
     */
    public String ask(String question) {
        this.writer.accept(question);
        return this.scanner.get();
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
