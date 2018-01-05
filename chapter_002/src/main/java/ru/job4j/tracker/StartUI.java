package ru.job4j.tracker;

/**
 * Start User Interface.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 2.0
 */
public class StartUI {
    /**
     * User input
     */
    private final Input input;

    /**
     * Tracker for items.
     */
    private final Tracker tracker;

    /**
     * Tracker menu.
     */
    private final MenuTracker menu;

    /**
     * Constructor user interface.
     * @param input - user input.
     * @param tracker - tracker for items.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        menu = new MenuTracker(input, tracker);
    }

    /**
     * Launch program.
     * @param args - args.
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }

    /**
     * Init console program.
     */
    public void init() {
        int key;
        do {
            menu.show();
            key = Integer.valueOf(input.ask("Введите пункт меню: "));
            menu.select(key);
        } while (key != 6);
    }
}
