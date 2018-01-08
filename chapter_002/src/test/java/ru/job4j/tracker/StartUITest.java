package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.models.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
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
    private final String menu = new StringBuilder()
            .append(String.format("Меню.%n"))
            .append(String.format("0. Добавить новую заявку.%n"))
            .append(String.format("1. Показать все заявки.%n"))
            .append(String.format("2. Редактировать заявку.%n"))
            .append(String.format("3. Удалить заявку.%n"))
            .append(String.format("4. Найти заявку по Id.%n"))
            .append(String.format("5. Найти заявку по названию.%n"))
            .append(String.format("6. Закончить работу.%n"))
            .append(String.format("Введите пункт меню: %n"))
            .toString();
    private final int menuLength = menu.length();

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
        assertThat(new String(out.toByteArray()), is(menu));
    }

    @Test
    public void whenUserAddItemThenShowCreateItem() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Добавление новой заявки ----------%n"))
                .append(String.format("Введите название заявки: %n"))
                .append(String.format("Введите описание заявки: %n"))
                .append(String.format("---------- Новая заявка с Id: 1 ----------%n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
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
        Item item = new Item("1", "desc item");
        tracker.add(item);
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Зарегистрированны следующие заявки: ----------%n"))
                .append(String.format("Заявка: 1, Id: 1%n"))
                .append(String.format("Описание: desc item%n"))
                .append("Дата создания заявки: ")
                .append(String.format(
                        "%s%n",
                        new SimpleDateFormat("dd.MM.yyyy hh:mm")
                                .format(item.getDateCreated()))
                )
                .append(String.format("-------------------------%n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectShowAllThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format("- Зарегистрированных заявок в системе нет.%n");
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndUserEditThisItemThenShowEdit() {
        Tracker tracker = new Tracker();
        Item item = new Item("test name", "desc");
        tracker.add(item);
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Редактирование заявки ----------%n"))
                .append(String.format("Введите Id заявки, которую желаете отредактировать: %n"))
                .append(String.format("Введите новое название заявки: %n"))
                .append(String.format("Введите новое описание заявки: %n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectEditItemWithIdOneThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"2", "1", "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Редактирование заявки ----------%n"))
                .append(String.format("Введите Id заявки, которую желаете отредактировать: %n"))
                .append(String.format("- Заявка с Id: 1 не зарегистрированна в системе.%n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
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
        String expected = new StringBuilder()
                .append(String.format("---------- Удаление заявки ----------%n"))
                .append(String.format("Введите Id заявки, которую желаете удалить: %n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserDeleteItemWithIdTwoThenTrackerRemoveThisItem() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item());
        tracker.add(new Item());
        tracker.add(new Item());
        String itemId = item1.getId();
        Input input = new StubInput(new String[]{"3", itemId, "6"});
        new StartUI(input, tracker).init();
        Item result = tracker.findById(itemId);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenAddItemAndUserSelectFindByIdThenShowThisItem() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("1", "desc"));
        String itemId = item.getId();
        Input input = new StubInput(new String[]{"4", itemId, "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Поис заявки по Id ----------%n"))
                .append(String.format("Введите Id заявки, которую желаете найти: %n"))
                .append(String.format("Заявка: 1, Id: 1%n"))
                .append(String.format("Описание: desc%n"))
                .append("Дата создания заявки: ")
                .append(String.format(
                        "%s%n",
                        new SimpleDateFormat("dd.MM.yyyy hh:mm")
                                .format(item.getDateCreated())
                        )
                )
                .append(String.format("-------------------------%n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemByIdThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"4", "1", "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Поис заявки по Id ----------%n"))
                .append(String.format("Введите Id заявки, которую желаете найти: %n"))
                .append(String.format("- Заявка с Id: 1 не зарегистрированна в системе.%n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndUserSelectFindByNameThisItemThenShowItem() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("1", "desc"));
        Input input = new StubInput(new String[]{"5", item.getName(), "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Поиск заявки по названию ----------%n"))
                .append(String.format("Введите название заявки(ок), которую(ые) желаете найти: %n"))
                .append(String.format("---------- Зарегистрированны следующие заявки: ----------%n"))
                .append(String.format("Заявка: 1, Id: 1%n"))
                .append(String.format("Описание: desc%n"))
                .append("Дата создания заявки: ")
                .append(String.format(
                        "%s%n",
                        new SimpleDateFormat("dd.MM.yyyy hh:mm")
                                .format(item.getDateCreated())
                        )
                )
                .append(String.format("-------------------------%n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemNameThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"5", "name", "6"});
        new StartUI(input, tracker).init();
        String expected = new StringBuilder()
                .append(String.format("---------- Поиск заявки по названию ----------%n"))
                .append(String.format("Введите название заявки(ок), которую(ые) желаете найти: %n"))
                .append(String.format("- Зарегистрированных заявок с названием name в системе нет.%n"))
                .toString();
        String result = new String(out.toByteArray())
                .substring(menuLength, menuLength + expected.length());
        assertThat(result, is(expected));
    }
}