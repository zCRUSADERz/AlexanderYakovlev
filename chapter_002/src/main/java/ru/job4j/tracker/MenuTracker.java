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
    private UserAction[] actions = new UserAction[7];
    /**
     * Range of key menu.
     */
    private final int[] range = new int[7];

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

    /**
     * Show menu.
     */
    public void show() {
        System.out.println("Меню.");
        for (UserAction action : actions) {
            System.out.println(action.info());
        }
    }

    /**
     * Select user actions.
     * @param key - key action.
     */
    public void select(int key) {
        actions[key].execute(input, tracker);
    }

    public int[] getRange() {
        return range;
    }

    /**
     * Fill all action in array.
     */
    private void fillActions() {
        actions[0] = new AddItem();
        actions[1] = new ShowAllItem();
        actions[2] = new EditItem();
        actions[3] = new DeleteItem();
        actions[4] = new FindById();
        actions[5] = new FindByName();
        actions[6] = new Exit();
        for (UserAction action : actions) {
            int key = action.key();
            range[key] = key;
        }
    }

    /**
     * User action: add item in tracker.
     */
    private class AddItem implements UserAction {
        /**
         * Key action.
         * @return - key.
         */
        public int key() {
            return 0;
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

        /**
         * Print info.
         * @return - info.
         */
        public String info() {
            return key() + ". Добавить новую заявку.";
        }
    }

    /**
     * User action: show all item in tracker.
     */
    private static class ShowAllItem implements UserAction {
        /**
         * Key action.
         * @return - key.
         */
        public int key() {
            return 1;
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

        /**
         * Print info.
         * @return - info.
         */
        public String info() {
            return key() + ". Показать все заявки.";
        }
    }

    /**
     * User action: edit item in tracker.
     */
    private class EditItem implements UserAction {
        /**
         * Key action.
         * @return - key.
         */
        public int key() {
            return 2;
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

        /**
         * Print info.
         * @return - info.
         */
        public String info() {
            return key() + ". Редактировать заявку.";
        }
    }

    /**
     * User action: delete item in tracker.
     */
    private class DeleteItem implements UserAction {
        /**
         * Key action.
         * @return - key.
         */
        public int key() {
            return 3;
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

        /**
         * Print info.
         * @return - info.
         */
        public String info() {
            return key() + ". Удалить заявку.";
        }
    }

    /**
     * User action: find item by Id in tracker.
     */
    private class FindById implements UserAction {
        /**
         * Key action.
         * @return - key.
         */
        public int key() {
            return 4;
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

        /**
         * Print info.
         * @return - info.
         */
        public String info() {
            return key() + ". Найти заявку по Id.";
        }
    }
}

/**
 * User action: find items by name in tracker.
 */
class FindByName implements UserAction {
    /**
     * Key action.
     * @return - key.
     */
    public int key() {
        return 5;
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

    /**
     * Print info.
     * @return - info.
     */
    public String info() {
        return key() + ". Найти заявку по названию.";
    }
}

/**
 * User action: exit.
 */
class Exit implements UserAction {
    /**
     * Key action.
     * @return - key.
     */
    public int key() {
        return 6;
    }

    /**
     * Exit.
     * @param input - user input.
     * @param tracker - tracker.
     */
    public void execute(Input input, Tracker tracker) {
    }

    /**
     * Print info.
     * @return - info.
     */
    public String info() {
        return key() + ". Закончить работу.";
    }
}
