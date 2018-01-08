package ru.job4j.tracker;

/**
 * Start User Interface.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 2.0
 */
public class StartUI implements Stop {
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
     * Flag to exit the program;
     */
    private boolean working;

    /**
     * Constructor user interface.
     * @param input - user input.
     * @param tracker - tracker for items.
     */
    StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        menu = new MenuTracker(input, tracker);
        working = true;
        menu.addActions(new Exit((menu.getRange().length), "Закончить работу", this));
    }

    /**
     * Launch program.
     * @param args - args.
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }

    /**
     * Init console program.
     */
    public void init() {
        do {
            menu.show();
            menu.select(input.ask("Введите пункт меню: ", menu.getRange()));
        } while (working);
    }

    public void stop() {
        working = false;
    }
}
