package ru.job4j.tracker;

import java.util.List;

/**
 * StubInput.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 2.0
 */
public class StubInput implements Input {
    private String[] answers;
    private int position = 0;

    /**
     * Constructor.
     * @param answers - array of answers.
     */
    StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     * Ask.
     * @param question - question.
     * @return - answer.
     */
    public String ask(String question) {
        System.out.print(question);
        System.out.println();
        return answers[position++];
    }

    /**
     * Ask user valid answer.
     * @param question - question.
     * @param range - range of key menu.
     * @return - key menu.
     */
    public int ask(String question, List<Integer> range) {
        return Integer.valueOf(ask(question));
    }
}
