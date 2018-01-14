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
    private final String menu = String.format(
            "Меню.%n"
                    + "0. Добавить новую заявку.%n"
                    + "1. Показать все заявки.%n"
                    + "2. Редактировать заявку.%n"
                    + "3. Удалить заявку.%n"
                    + "4. Найти заявку по Id.%n"
                    + "5. Найти заявку по названию.%n"
                    + "6. Закончить работу.%n"
                    + "Введите пункт меню: %n"
    );
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

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
        String expected = String.format(
                "%s"
                        + "---------- Добавление новой заявки ----------%n"
                        + "Введите название заявки: %n"
                        + "Введите описание заявки: %n"
                        + "---------- Новая заявка с Id: 1 ----------%n"
                        + "%s",
                menu,
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("test name"));
    }

    @Test
    public void whenAddItemAndUserSelectShowAllItemThenShowAllItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("1", "desc item");
        tracker.add(item);
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Зарегистрированны следующие заявки: ----------%n"
                        + "Заявка: 1, Id: 1%n"
                        + "Описание: desc item%n" + "Дата создания заявки: %s%n"
                        + "-------------------------%n"
                        + "%s",
                menu,
                dateFormat.format(item.getDateCreated()),
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectShowAllThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format("%s- Зарегистрированных заявок в системе нет.%n%s", menu, menu);
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndUserEditThisItemThenShowEdit() {
        Tracker tracker = new Tracker();
        Item item = new Item("test name", "desc");
        tracker.add(item);
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Редактирование заявки ----------%n"
                        + "Введите Id заявки, которую желаете отредактировать: %n"
                        + "Введите новое название заявки: %n"
                        + "Введите новое описание заявки: %n"
                        + "%s",
                menu,
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectEditItemWithIdOneThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"2", "1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Редактирование заявки ----------%n"
                        + "Введите Id заявки, которую желаете отредактировать: %n"
                        + "- Заявка с Id: 1 не зарегистрированна в системе.%n"
                        + "%s",
                menu,
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserEditItemThenTrackerHasUpdatedValue() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("", ""));
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    @Test
    public void whenUserSelectDeleteItemThenShowDeleteItem() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"3", "1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Удаление заявки ----------%n"
                        + "Введите Id заявки, которую желаете удалить: %n"
                        + "%s",
                menu,
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserDeleteItemWithIdTwoThenTrackerRemoveThisItem() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("", ""));
        tracker.add(new Item("", ""));
        tracker.add(new Item("", ""));
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
        String expected = String.format(
                "%s"
                        + "---------- Поис заявки по Id ----------%n"
                        + "Введите Id заявки, которую желаете найти: %n"
                        + "Заявка: 1, Id: 1%n"
                        + "Описание: desc%n"
                        + "Дата создания заявки: %s%n"
                        + "-------------------------%n"
                        + "%s",
                menu,
                dateFormat.format(item.getDateCreated()),
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemByIdThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"4", "1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Поис заявки по Id ----------%n"
                        + "Введите Id заявки, которую желаете найти: %n"
                        + "- Заявка с Id: 1 не зарегистрированна в системе.%n"
                        + "%s",
                menu,
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndUserSelectFindByNameThisItemThenShowItem() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("1", "desc"));
        Input input = new StubInput(new String[]{"5", item.getName(), "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Поиск заявки по названию ----------%n"
                        + "Введите название заявки(ок), которую(ые) желаете найти: %n"
                        + "---------- Зарегистрированны следующие заявки: ----------%n"
                        + "Заявка: 1, Id: 1%n"
                        + "Описание: desc%n"
                        + "Дата создания заявки: %s%n"
                        + "-------------------------%n"
                        + "%s",
                menu,
                dateFormat.format(item.getDateCreated()),
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemNameThenShow() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"5", "name", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Поиск заявки по названию ----------%n"
                        + "Введите название заявки(ок), которую(ые) желаете найти: %n"
                        + "- Зарегистрированных заявок с названием name в системе нет.%n"
                        + "%s",
                menu,
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }
}
