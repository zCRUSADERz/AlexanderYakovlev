package ru.job4j.tracker;

/**
 * StubInput.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 1.0
 */
public class StubInput implements Input {
    private String[] answers;
    private int position = 0;

    /**
     * Constructor.
     * @param answers - array of answers.
     */
    public StubInput(String[] answers) {
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
}
