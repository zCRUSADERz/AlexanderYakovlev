package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.models.Item;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Start user interface test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 1.0
 */
public class StartUITest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOutput() {
        System.setOut(stdout);
    }

    @Test
    public void whenUserRunProgramAndExitThenShowMenuAndExit() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(String.format("Меню.%n"))
                                .append(String.format("0. Добавить новую заявку.%n"))
                                .append(String.format("1. Показать все заявки.%n"))
                                .append(String.format("2. Редактировать заявку.%n"))
                                .append(String.format("3. Удалить заявку.%n"))
                                .append(String.format("4. Найти заявку по Id.%n"))
                                .append(String.format("5. Найти заявку по названию.%n"))
                                .append(String.format("6. Закончить работу.%n"))
                                .append(String.format("Введите пункт меню: %n"))
                                .toString()
                )
        );
    }

    @Test
    public void whenUserAddItemThenShowCreateItem() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = new StringBuilder()
                .append(strings[9])
                .append(strings[10])
                .append(strings[11])
                .append(strings[12])
                .toString();
        assertThat(
                result,
                is(
                        new StringBuilder()
                                .append("---------- Добавление новой заявки ----------")
                                .append("Введите название заявки: ")
                                .append("Введите описание заявки: ")
                                .append("---------- Новая заявка с Id: 1 ----------")
                                .toString()
                )
        );
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("test name"));
    }

    @Test
    public void whenAddItemAndUserSelectShowAllItemThenShowAllItem() {
        Tracker tracker = new Tracker();
        Date itemCreatedDate = new Date();
        Item item = new Item("1", "desc item", itemCreatedDate.getTime());
        tracker.add(item);
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = new StringBuilder()
                .append(strings[9])
                .append(strings[10])
                .append(strings[11])
                .append(strings[12])
                .append(strings[13])
                .toString();
        assertThat(
                result,
                is(
                        new StringBuilder()
                                .append("---------- Зарегистрированны следующие заявки: ----------")
                                .append("Заявка: 1, Id: 1")
                                .append("Описание: desc item")
                                .append("Дата создания заявки: ")
                                .append(
                                        new SimpleDateFormat("dd.MM.yyyy hh:mm")
                                                .format(itemCreatedDate)
                                )
                                .append("-------------------------")
                                .toString()
                )
        );
    }

    @Test
    public void whenTrackerEmptyAndUserSelectShowAllThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = strings[9];
        String expected = "- Зарегистрированных заявок в системе нет.";
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndUserEditThisItemThenShowEdit() {
        Tracker tracker = new Tracker();
        Item item = new Item("test name", "desc", 1);
        tracker.add(item);
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = new StringBuilder()
                .append(strings[9])
                .append(strings[10])
                .append(strings[11])
                .append(strings[12])
                .toString();
        assertThat(
                result,
                is(
                        new StringBuilder()
                                .append("---------- Редактирование заявки ----------")
                                .append("Введите Id заявки, которую желаете отредактировать: ")
                                .append("Введите новое название заявки: ")
                                .append("Введите новое описание заявки: ")
                                .toString()
                )
        );
    }

    @Test
    public void whenTrackerEmptyAndUserSelectEditItemWithIdOneThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"2", "1", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = strings[11];
        String expected = "- Заявка с Id: 1 не зарегистрированна в системе.";
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserEditItemThenTrackerHasUpdatedValue() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item());
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    @Test
    public void whenUserSelectDeleteItemThenShowDeleteItem() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"3", "1", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = new StringBuilder()
                .append(strings[9])
                .append(strings[10])
                .toString();
        assertThat(
                result,
                is(
                        new StringBuilder()
                                .append("---------- Удаление заявки ----------")
                                .append("Введите Id заявки, которую желаете удалить: ")
                                .toString()
                )
        );
    }

    @Test
    public void whenUserDeleteItemWithIdTwoThenTrackerRemoveThisItem() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item());
        Item item2 = tracker.add(new Item());
        Item item3 = tracker.add(new Item());
        String itemId = item1.getId();
        Input input = new StubInput(new String[]{"3", itemId, "6"});
        new StartUI(input, tracker).init();
        Item result = tracker.findById(itemId);
        Item expected = null;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndUserSelectFindByIdThenShowThisItem() {
        Tracker tracker = new Tracker();
        Date itemCreated = new Date();
        Item item = tracker.add(new Item("1", "desc", itemCreated.getTime()));
        String itemId = item.getId();
        Input input = new StubInput(new String[]{"4", itemId, "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = new StringBuilder()
                .append(strings[9])
                .append(strings[10])
                .append(strings[11])
                .append(strings[12])
                .append(strings[13])
                .append(strings[14])
                .toString();
        assertThat(
                result,
                is(
                        new StringBuilder()
                                .append("---------- Поис заявки по Id ----------")
                                .append("Введите Id заявки, которую желаете найти: ")
                                .append("Заявка: 1, Id: 1")
                                .append("Описание: desc")
                                .append("Дата создания заявки: ")
                                .append(
                                        new SimpleDateFormat("dd.MM.yyyy hh:mm")
                                                .format(itemCreated)
                                )
                                .append("-------------------------")
                                .toString()
                )
        );
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemByIdThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"4", "1", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = strings[11];
        String expected = "- Заявка с Id: 1 не зарегистрированна в системе.";
    }

    @Test
    public void whenAddItemAndUserSelectFindByNameThisItemThenShowItem() {
        Tracker tracker = new Tracker();
        Date itemCreated = new Date();
        Item item = tracker.add(new Item("1", "desc", itemCreated.getTime()));
        String itemId = item.getId();
        Input input = new StubInput(new String[]{"5", item.getName(), "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = new StringBuilder()
                .append(strings[9])
                .append(strings[10])
                .append(strings[11])
                .append(strings[12])
                .append(strings[13])
                .append(strings[14])
                .append(strings[15])
                .toString();
        assertThat(
                result,
                is(
                        new StringBuilder()
                                .append("---------- Поис заявки по названию ----------")
                                .append("Введите название заявки(ок), которую(ые) желаете найти: ")
                                .append("---------- Зарегистрированны следующие заявки: ----------")
                                .append("Заявка: 1, Id: 1")
                                .append("Описание: desc")
                                .append("Дата создания заявки: ")
                                .append(
                                        new SimpleDateFormat("dd.MM.yyyy hh:mm")
                                                .format(itemCreated)
                                )
                                .append("-------------------------")
                                .toString()
                )
        );
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemNameThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"5", "name", "6"});
        new StartUI(input, tracker).init();
        String outString = new String(out.toByteArray());
        String[] strings = outString.split(System.lineSeparator());
        String result = strings[11];
        String expected = "- Зарегистрированных заявок с названием name в системе нет.";
    }
}