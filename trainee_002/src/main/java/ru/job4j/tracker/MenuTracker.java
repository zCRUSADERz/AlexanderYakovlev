package ru.job4j.tracker;

import ru.job4j.tracker.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Menu tracker.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.01.2017
 * @version 2.0
 */
public class MenuTracker {
    /**
     * User input.
     */
    private final Input input;
    private final Consumer<String> writer;
    /**
     * Tracker for items.
     */
    private final Tracker tracker;
    /**
     * User actions.
     */
    private final ArrayList<UserAction> actions = new ArrayList<>();
    /**
     * Range of key menu.
     */
    private final ArrayList<Integer> range  = new ArrayList<>();

    /**
     * @param input - user input.
     * @param writer - string writer.
     * @param tracker - tracker.
     */
    public MenuTracker(Input input, Consumer<String> writer, Tracker tracker) {
        this.input = input;
        this.writer = writer;
        this.tracker = tracker;
        fillActions();
    }

    public List<Integer> getRange() {
        return new ArrayList<>(range);
    }

    /**
     * Show menu.
     */
    public void show() {
        this.writer.accept("Меню.");
        for (UserAction action : actions) {
            this.writer.accept(action.info());
        }
    }

    /**
     * Select user actions.
     * @param key - key action.
     */
    public void select(int key) throws DBException {
        for (UserAction action : actions) {
            if (action.key() == key) {
                action.execute(input, tracker);
                break;
            }
        }
    }

    /**
     * Add new user action in menu.
     * @param action - user action.
     */
    public void addActions(UserAction action) {
        actions.add(action);
        updateRange();
    }

    /**
     * Update range of key menu.
     */
    private void updateRange() {
        range.add(range.size());
    }

    /**
     * Fill all action in array.
     */
    private void fillActions() {
        addActions(new AddItem(0, "Добавить новую заявку"));
        addActions(new ShowAllItem(1, "Показать все заявки"));
        addActions(new EditItem(2, "Редактировать заявку"));
        addActions(new DeleteItem(3, "Удалить заявку"));
        addActions(new FindById(4, "Найти заявку по Id"));
        addActions(new FindByName(5, "Найти заявку по названию"));
    }

    /**
     * User action: add item in tracker.
     */
    private class AddItem extends BaseAction {

        /**
         * Constructor.
         * @param key - menu key.
         * @param name - name action;
         */
        AddItem(int key, String name) {
            super(key, name);
        }

        /**
         * Add item in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) throws DBException {
            writer.accept("---------- Добавление новой заявки ----------");
            String name = input.ask("Введите название заявки: ");
            String description = input.ask("Введите описание заявки: ");
            int resultId = tracker.add(name, description);
            writer.accept(
                    "---------- Новая заявка с Id: " + resultId + " ----------"
            );
        }
    }

    /**
     * User action: show all item in tracker.
     */
    private class ShowAllItem extends BaseAction {

        /**
         * Constructor.
         * @param key - menu key.
         * @param name - name action;
         */
        ShowAllItem(int key, String name) {
            super(key, name);
        }

        /**
         * Show all items in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) throws DBException {
            List<Item> items = tracker.findAll();
            if (items.size() != 0) {
                writer.accept(
                        "---------- Зарегистрированны следующие заявки: ----------"
                );
                for (Item item : items) {
                    writer.accept(item.toString());
                }
            } else {
                writer.accept("- Зарегистрированных заявок в системе нет.");
            }
        }
    }

    /**
     * User action: edit item in tracker.
     */
    private class EditItem extends BaseAction {

        /**
         * Constructor.
         * @param key - menu key.
         * @param name - name action;
         */
        EditItem(int key, String name) {
            super(key, name);
        }

        /**
         * Edit item in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) throws DBException {
            writer.accept("---------- Редактирование заявки ----------");
            String id = input.ask(
                    "Введите Id заявки, которую желаете отредактировать: "
            );
            String name = input.ask("Введите новое название заявки: ");
            String desc = input.ask("Введите новое описание заявки: ");
            if (!tracker.replace(id, name, desc)) {
                writer.accept(
                        "- Заявка с Id: " + id + " не зарегистрированна в системе."
                );
            }
        }
    }

    /**
     * User action: delete item in tracker.
     */
    private class DeleteItem extends BaseAction {

        /**
         * Constructor.
         * @param key - menu key.
         * @param name - name action;
         */
        DeleteItem(int key, String name) {
            super(key, name);
        }

        /**
         * Delete item in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) throws DBException {
            writer.accept("---------- Удаление заявки ----------");
            String id = input.ask(
                    "Введите Id заявки, которую желаете удалить: "
            );
            tracker.delete(id);
        }
    }

    /**
     * User action: find item by Id in tracker.
     */
    private class FindById extends BaseAction {

        /**
         * Constructor.
         * @param key - menu key.
         * @param name - name action;
         */
        FindById(int key, String name) {
            super(key, name);
        }

        /**
         * Find item by Id.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) throws DBException {
            writer.accept("---------- Поис заявки по Id ----------");
            String id = input.ask(
                    "Введите Id заявки, которую желаете найти: "
            );
            Item item = tracker.findById(id);
            if (item != null) {
                writer.accept(item.toString());
            } else {
                writer.accept(
                        "- Заявка с Id: " + id + " не зарегистрированна в системе."
                );
            }
        }
    }

    /**
     * User action: find items by name in tracker.
     */
    class FindByName extends BaseAction {

        /**
         * Constructor.
         * @param key - menu key.
         * @param name - name action;
         */
        FindByName(int key, String name) {
            super(key, name);
        }

        /**
         * Find items by name in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) throws DBException {
            writer.accept("---------- Поиск заявки по названию ----------");
            String name = input.ask(
                    "Введите название заявки(ок), которую(ые) желаете найти: "
            );
            List<Item> items = tracker.findByName(name);
            if (items.size() != 0) {
                writer.accept(
                        "---------- Зарегистрированны следующие заявки: ----------"
                );
                for (Item item : items) {
                    writer.accept(item.toString());
                }
            } else {

                writer.accept(
                        "- Зарегистрированных заявок с названием " + name + " в системе нет."
                );
            }
        }
    }
}

/**
 * User action: exit.
 */
class Exit extends BaseAction {
    /**
     * Object to be stopped.
     */
    private final Stop stop;

    /**
     * Constructor.
     * @param key - Key menu.
     * @param name - Name user action.
     * @param stop - Object to be stopped.
     */
    Exit(int key, String name, Stop stop) {
        super(key, name);
        this.stop = stop;
    }

    /**
     * Exit.
     * @param input - user input.
     * @param tracker - tracker.
     */
    public void execute(Input input, Tracker tracker) {
        stop.stop();
    }
}
