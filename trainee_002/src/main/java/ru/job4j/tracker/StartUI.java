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
        menu = new MenuTracker(input, tracker);
        working = true;
        menu.addActions(
                new Exit(
                        (menu.getRange().size()),
                        "Закончить работу", this
                )
        );
    }

    /**
     * Launch program.
     * @param args - args.
     */
    public static void main(String[] args) throws DBException {
        try (PostgresDB db = new PostgresDB()) {
            try (Tracker tracker = new Tracker(db.getConnection())) {
                new StartUI(
                        new ValidateInput(new ConsoleInput()),
                        tracker
                ).init();
            }
        } catch (DBException e) {
            System.out.println(
                    "Обнаружена ошибка с доступом к БД, дальнейшая работа не возможна."
            );
            throw e;
        }
    }

    /**
     * Init console program.
     */
    public void init() throws DBException {
        do {
            menu.show();
            menu.select(
                    input.ask("Введите пункт меню: ", menu.getRange())
            );
        } while (working);
    }

    public void stop() {
        working = false;
    }
}
