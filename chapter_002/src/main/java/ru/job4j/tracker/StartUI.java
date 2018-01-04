package ru.job4j.tracker;

import ru.job4j.tracker.models.Item;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Start User Interface.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 1.0
 */
public class StartUI {
    /**
     * Constants for menu.
     * Add new Item.
     */
    private static final String ADD = "0";

    /**
     * Show all Item in tracker.
     */
    private static final String SHOWALL = "1";

    /**
     * Edit Item.
     */
    private static final String EDIT = "2";

    /**
     * Delete item.
     */
    private static final String DELETE = "3";

    /**
     * Find item by Id.
     */
    private static final String FINDBYID = "4";

    /**
     * Find item by name item.
     */
    private static final String FINDBYNAME = "5";

    /**
     * Quit the programm.
     */
    private static final String EXIT = "6";

    /**
     * User input
     */
    private final Input input;

    /**
     * Tracker for items.
     */
    private final Tracker tracker;

    /**
     * Constructor user interface.
     * @param input - user input.
     * @param tracker - tracker for items.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
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
        boolean exit = false;
        while (!exit) {
            showMenu();
            String answer = input.ask("Введите пункт меню: ");
            if (ADD.equals(answer)) {
                createItem();
            } else if (SHOWALL.equals(answer)) {
                showAllItem();
            } else if (EDIT.equals(answer)) {
                editItem();
            } else if (DELETE.equals(answer)) {
                deleteItem();
            } else if (FINDBYID.equals(answer)) {
                findById();
            } else if (FINDBYNAME.equals(answer)) {
                findByName();
            } else if (EXIT.equals(answer)) {
                exit = true;
            }
        }
    }

    /**
     * Show menu in console.
     */
    private void showMenu() {
        String ln = System.lineSeparator();
        System.out.print("Меню." + ln
                + "0. Добавить новую заявку." + ln
                + "1. Показать все заявки." + ln
                + "2. Редактировать заявку." + ln
                + "3. Удалить заявку." + ln
                + "4. Найти заявку по Id." + ln
                + "5. Найти заявку по названию." + ln
                + "6. Закончить работу." + ln
                + "Выберите пункт: "
        );
    }

    /**
     * Create new item and add in tracker.
     */
    private void createItem() {
        System.out.println("---------- Добавление новой заявки ----------");
        String name = input.ask("Введите название заявки: ");
        String description = input.ask("Введите описание заявки: ");
        Item item = new Item(name, description, new Date().getTime());
        tracker.add(item);
        System.out.println("---------- Новая заявка с Id: " + item.getId() + " ----------");
    }

    /**
     * Show all items in tracker.
     */
    private void showAllItem() {
        Item[] items = tracker.findAll();
        if (items != null) {
            System.out.println("---------- Зарегистрированны следующие заявки: ----------");
            for (Item item : items) {
                printItem(item);
            }
        } else {
            System.out.println("- Зарегистрированных заявок в системе нет.");
        }

    }

    /**
     * Edit item in tracker.
     */
    private void editItem() {
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
     * Delete item in tracker.
     */
    private void deleteItem() {
        System.out.println("---------- Удаление заявки ----------");
        String id = input.ask("Введите Id заявки, которую желаете удалить: ");
        tracker.delete(id);
    }

    /**
     * Find item in tracker by Id.
     */
    private void findById() {
        System.out.println("---------- Поис заявки по Id ----------");
        String id = input.ask("Введите Id заявки, которую желаете найти: ");
        Item item = tracker.findById(id);
        if (item != null) {
            printItem(item);
        } else {
            System.out.println("- Заявка с Id: " + id + " не зарегистрированна в системе.");
        }
    }

    /**
     * Find item in tracker by name.
     */
    private void findByName() {
        System.out.println("---------- Поис заявки по названию ----------");
        String name = input.ask("Введите название заявки(ок), которую(ые) желаете найти: ");
        Item[] items = tracker.findByName(name);
        if (items != null) {
            System.out.println("---------- Зарегистрированны следующие заявки: ----------");
            for (Item item : items) {
                printItem(item);
            }
        } else {
            System.out.println("- Зарегистрированных заявок с названием " + name + " в системе нет.");
        }
    }

    /**
     * Print item in console.
     * @param item - item.
     */
    private void printItem(Item item) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        String ln = System.lineSeparator();
        System.out.print("Заявка: " + item.getName() + ", Id: " + item.getId() + ln
                + "Описание: " + item.getDesc() + ln
                + "Дата создания заявки: " + format.format(new Date(item.getCreated())) + ln
                + "-------------------------" + ln
        );
    }
}
