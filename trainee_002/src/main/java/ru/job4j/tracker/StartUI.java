package ru.job4j.tracker;

import java.util.function.Consumer;

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

    public StartUI(Input input, Tracker tracker) {
        this(input, System.out::println, tracker);
    }

    public StartUI(Input input, Consumer<String> writer, Tracker tracker) {
        this.input = input;
        this.menu = new MenuTracker(input, writer, tracker);
        this.menu.addActions(
                new Exit(this.menu.getRange().size(),
                        "Закончить работу", this
                )
        );
        this.working = true;

    }

    /**
     * Launch program.
     * @param args - args.
     */
    public static void main(String[] args) throws DBException {
        try (PostgresDB db = new PostgresDB()) {
            try (Tracker tracker = new Tracker(db.getConnection())) {
                new StartUI(
                        new ValidateInput(
                                new InputFromSupplier()
                        ),
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
            this.menu.show();
            this.menu.select(
                    this.input.ask(
                            "Введите пункт меню: ",
                            this.menu.getRange()
                    )
            );
        } while (this.working);
    }

    public void stop() {
        this.working = false;
    }
}
