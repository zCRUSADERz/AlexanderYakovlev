package ru.job4j.tracker;

import ru.job4j.tracker.models.Item;

import java.util.Date;

/**
 * Menu tracker.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.01.2017
 * @version 1.0
 */
public class MenuTracker {
    /**
     * User input.
     */
    private Input input;
    /**
     * Tracker for items.
     */
    private Tracker tracker;
    /**
     * User actions.
     */
    private UserAction[] actions = new UserAction[20];
    /**
     * Range of key menu.
     */
    private int[] range = new int[20];
    /**
     * Position in actions array;
     */
    private int position;

    /**
     * Constructor menu tracker.
     * @param input - user input.
     * @param tracker - tracker.
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        fillActions();
    }

    public int[] getRange() {
        return range;
    }

    /**
     * Show menu.
     */
    public void show() {
        System.out.println("Меню.");
        for (int i = 0; i < position; i++) {
            System.out.println(actions[i].info());
        }
    }

    /**
     * Select user actions.
     * @param key - key action.
     */
    public void select(int key) {
        actions[key].execute(input, tracker);
    }

    /**
     * Add new user action in menu.
     * @param action - user action.
     */
    public void addActions(UserAction action) {
        actions[position++] = action;
        updateRange();
    }

    /**
     * Update range of key menu.
     */
    private void updateRange() {
        range[position - 1] = position - 1;
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
        addActions(new Exit(6, "Закончить работу"));
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
        public AddItem(int key, String name) {
            super(key, name);
        }

        /**
         * Add item in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------- Добавление новой заявки ----------");
            String name = input.ask("Введите название заявки: ");
            String description = input.ask("Введите описание заявки: ");
            Item item = new Item(name, description, new Date().getTime());
            tracker.add(item);
            System.out.println("---------- Новая заявка с Id: " + item.getId() + " ----------");
        }
    }

    /**
     * User action: show all item in tracker.
     */
    private static class ShowAllItem extends BaseAction {

        /**
         * Constructor.
         * @param key - menu key.
         * @param name - name action;
         */
        public ShowAllItem(int key, String name) {
            super(key, name);
        }

        /**
         * Show all items in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) {
            Item[] items = tracker.findAll();
            if (items != null) {
                System.out.println("---------- Зарегистрированны следующие заявки: ----------");
                for (Item item : items) {
                    System.out.println(item);
                }
            } else {
                System.out.println("- Зарегистрированных заявок в системе нет.");
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
        public EditItem(int key, String name) {
            super(key, name);
        }

        /**
         * Edit item in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------- Редактирование заявки ----------");
            String id = input.ask("Введите Id заявки, которую желаете отредактировать: ");
            Item item = tracker.findById(id);
            if (item != null) {
                String name = input.ask("Введите новое название заявки: ");
                item.setName(name);
                String desc = input.ask("Введите новое описание заявки: ");
                item.setDesc(desc);
                tracker.replace(id, item);
            } else {
                System.out.println("- Заявка с Id: " + id + " не зарегистрированна в системе.");
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
        public DeleteItem(int key, String name) {
            super(key, name);
        }

        /**
         * Delete item in tracker.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------- Удаление заявки ----------");
            String id = input.ask("Введите Id заявки, которую желаете удалить: ");
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
        public FindById(int key, String name) {
            super(key, name);
        }

        /**
         * Find item by Id.
         * @param input - user input.
         * @param tracker - tracker.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------- Поис заявки по Id ----------");
            String id = input.ask("Введите Id заявки, которую желаете найти: ");
            Item item = tracker.findById(id);
            if (item != null) {
                System.out.println(item);
            } else {
                System.out.println("- Заявка с Id: " + id + " не зарегистрированна в системе.");
            }
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
    public FindByName(int key, String name) {
        super(key, name);
    }

    /**
     * Find items by name in tracker.
     * @param input - user input.
     * @param tracker - tracker.
     */
    public void execute(Input input, Tracker tracker) {
        System.out.println("---------- Поис заявки по названию ----------");
        String name = input.ask("Введите название заявки(ок), которую(ые) желаете найти: ");
        Item[] items = tracker.findByName(name);
        if (items != null) {
            System.out.println("---------- Зарегистрированны следующие заявки: ----------");
            for (Item item : items) {
                System.out.println(item);
            }
        } else {
            System.out.println("- Зарегистрированных заявок с названием " + name + " в системе нет.");
        }
    }
}

/**
 * User action: exit.
 */
class Exit extends BaseAction {

    /**
     * Constructor.
     * @param key - menu key.
     * @param name - name action;
     */
    public Exit(int key, String name) {
        super(key, name);
    }

    /**
     * Exit.
     * @param input - user input.
     * @param tracker - tracker.
     */
    public void execute(Input input, Tracker tracker) {
    }
}
