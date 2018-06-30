package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.models.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Start user interface test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
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
    private final Connection connection = new PostgresDB().getConnection();
    private Tracker tracker;

    public StartUITest() throws Exception {
    }

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(out));
    }

    @Before
    public void initTracker() throws Exception {
        this.tracker = new Tracker(this.connection);
        tracker.deleteAllItems();
    }

    @After
    public void backOutput() {
        System.setOut(stdout);
    }

    @Test
    public void whenUserRunProgramAndExitThenShowMenuAndExit() throws Exception {
        Input input = new StubInput(new String[]{"6"});
        new StartUI(input, this.tracker).init();
        assertThat(new String(out.toByteArray()), is(menu));
    }

    @Test
    public void whenUserAddItemThenShowCreateItem() throws Exception {
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
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() throws Exception {
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("test name"));
    }

    @Test
    public void whenAddItemAndUserSelectShowAllItemThenShowAllItem() throws Exception {
        Date created = new Date();
        tracker.add("1", "desc item", created.getTime());
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
                dateFormat.format(created),
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectShowAllThenShow() throws Exception {
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format("%s- Зарегистрированных заявок в системе нет.%n%s", menu, menu);
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndUserEditThisItemThenShowEdit() throws Exception {
        tracker.add("test name", "desc");
        Input input = new StubInput(new String[]{"2", "1", "test name", "desc", "6"});
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
    public void whenTrackerEmptyAndUserSelectEditItemWithIdOneThenShow() throws Exception {
        Input input = new StubInput(new String[]{"2", "1", "new name", "new desc", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Редактирование заявки ----------%n"
                        + "Введите Id заявки, которую желаете отредактировать: %n"
                        + "Введите новое название заявки: %n"
                        + "Введите новое описание заявки: %n"
                        + "- Заявка с Id: 1 не зарегистрированна в системе.%n"
                        + "%s",
                menu,
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenUserEditItemThenTrackerHasUpdatedValue() throws Exception {
        tracker.add("new item", "new item desc");
        Input input = new StubInput(new String[]{"2", "1", "new name", "new desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById("1").getName(), is("new name"));
    }

    @Test
    public void whenUserSelectDeleteItemThenShowDeleteItem() throws Exception {
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
    public void whenUserDeleteItemWithIdTwoThenTrackerRemoveThisItem() throws Exception {
        tracker.add("first", "first desc");
        tracker.add("second", "second desc");
        tracker.add("third", "third desc");
        Input input = new StubInput(new String[]{"3", "2", "6"});
        new StartUI(input, tracker).init();
        Item result = tracker.findById("2");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenAddItemAndUserSelectFindByIdThenShowThisItem() throws Exception {
        Date created = new Date();
        tracker.add("item", "item desc", created.getTime());
        Input input = new StubInput(new String[]{"4", "1", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Поис заявки по Id ----------%n"
                        + "Введите Id заявки, которую желаете найти: %n"
                        + "Заявка: item, Id: 1%n"
                        + "Описание: item desc%n"
                        + "Дата создания заявки: %s%n"
                        + "-------------------------%n"
                        + "%s",
                menu,
                dateFormat.format(created),
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemByIdThenShow() throws Exception {
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
    public void whenAddItemAndUserSelectFindByNameThisItemThenShowItem() throws Exception {
        Date created = new Date();
        tracker.add("tracker", "tracker desc", created.getTime());
        Input input = new StubInput(new String[]{"5", "tracker", "6"});
        new StartUI(input, tracker).init();
        String expected = String.format(
                "%s"
                        + "---------- Поиск заявки по названию ----------%n"
                        + "Введите название заявки(ок), которую(ые) желаете найти: %n"
                        + "---------- Зарегистрированны следующие заявки: ----------%n"
                        + "Заявка: tracker, Id: 1%n"
                        + "Описание: tracker desc%n"
                        + "Дата создания заявки: %s%n"
                        + "-------------------------%n"
                        + "%s",
                menu,
                dateFormat.format(created),
                menu
        );
        String result = new String(out.toByteArray());
        assertThat(result, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemNameThenShow() throws Exception {
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
