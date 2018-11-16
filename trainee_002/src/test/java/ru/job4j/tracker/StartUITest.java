package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.models.Item;

import java.util.*;
import java.util.function.Consumer;

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
    private final List<String> buffer = new ArrayList<>();
    private final Consumer<String> writer = this.buffer::add;
    private final List<String> menu = Arrays.asList(
            "Меню.",
            "0. Добавить новую заявку.",
            "1. Показать все заявки.",
            "2. Редактировать заявку.",
            "3. Удалить заявку.",
            "4. Найти заявку по Id.",
            "5. Найти заявку по названию.",
            "6. Закончить работу.",
            "Введите пункт меню: "
    );
    private final Tracker tracker = new Tracker(new PostgresDB().getConnection());

    public StartUITest() throws DBException {
    }

    @Before
    public void initTracker() throws Exception {
        this.tracker.deleteAllItems();
    }

    @After
    public void clearBuffer() {
        this.buffer.clear();
    }

    @Test
    public void whenUserRunProgramAndExitThenShowMenuAndExit() throws Exception {
        this.newStartUI("6");
        assertThat(this.buffer, is(this.menu));
    }

    @Test
    public void whenUserAddItemThenShowCreateItem() throws Exception {
        this.newStartUI("0", "test name", "desc", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Добавление новой заявки ----------",
                "Введите название заявки: ",
                "Введите описание заявки: ",
                "---------- Новая заявка с Id: 1 ----------"
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() throws Exception {
        this.newStartUI("0", "test name", "desc", "6");
        assertThat(tracker.findAll().get(0).getName(), is("test name"));
    }

    @Test
    public void whenAddItemAndUserSelectShowAllItemThenShowAllItem() throws Exception {
        Date created = new Date();
        this.tracker.add("1", "desc item", created.getTime());
        this.newStartUI("1", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Зарегистрированны следующие заявки: ----------",
                this.tracker.findById("1").toString()
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectShowAllThenShow() throws Exception {
        this.newStartUI("1", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.add("- Зарегистрированных заявок в системе нет.");
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenAddItemAndUserEditThisItemThenShowEdit() throws Exception {
        this.tracker.add("test name", "desc");
        this.newStartUI("2", "1", "test name", "desc", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Редактирование заявки ----------",
                "Введите Id заявки, которую желаете отредактировать: ",
                "Введите новое название заявки: ",
                "Введите новое описание заявки: "
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectEditItemWithIdOneThenShow() throws Exception {
        this.newStartUI("2", "1", "new name", "new desc", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Редактирование заявки ----------",
                "Введите Id заявки, которую желаете отредактировать: ",
                "Введите новое название заявки: ",
                "Введите новое описание заявки: ",
                "- Заявка с Id: 1 не зарегистрированна в системе."
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenUserEditItemThenTrackerHasUpdatedValue() throws Exception {
        this.tracker.add("new item", "new item desc");
        this.newStartUI("2", "1", "new name", "new desc", "6");
        assertThat(this.tracker.findById("1").getName(), is("new name"));
    }

    @Test
    public void whenUserSelectDeleteItemThenShowDeleteItem() throws Exception {
        this.newStartUI("3", "1", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Удаление заявки ----------",
                "Введите Id заявки, которую желаете удалить: "
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenUserDeleteItemWithIdTwoThenTrackerRemoveThisItem() throws Exception {
        this.tracker.add("first", "first desc");
        this.tracker.add("second", "second desc");
        this.tracker.add("third", "third desc");
        this.newStartUI("3", "2", "6");
        Item result = this.tracker.findById("2");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenAddItemAndUserSelectFindByIdThenShowThisItem() throws Exception {
        Date created = new Date();
        this.tracker.add("item", "item desc", created.getTime());
        this.newStartUI("4", "1", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Поис заявки по Id ----------",
                "Введите Id заявки, которую желаете найти: ",
                this.tracker.findById("1").toString()
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemByIdThenShow() throws Exception {
        this.newStartUI("4", "1", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Поис заявки по Id ----------",
                "Введите Id заявки, которую желаете найти: ",
                "- Заявка с Id: 1 не зарегистрированна в системе."
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenAddItemAndUserSelectFindByNameThisItemThenShowItem() throws Exception {
        Date created = new Date();
        this.tracker.add("tracker", "tracker desc", created.getTime());
        this.newStartUI("5", "tracker", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Поиск заявки по названию ----------",
                "Введите название заявки(ок), которую(ые) желаете найти: ",
                "---------- Зарегистрированны следующие заявки: ----------",
                this.tracker.findById("1").toString()
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    @Test
    public void whenTrackerEmptyAndUserSelectFindItemNameThenShow() throws Exception {
        this.newStartUI("5", "name", "6");
        final List<String> expected = new ArrayList<>(this.menu);
        expected.addAll(Arrays.asList(
                "---------- Поиск заявки по названию ----------",
                "Введите название заявки(ок), которую(ые) желаете найти: ",
                "- Зарегистрированных заявок с названием name в системе нет."
        ));
        expected.addAll(this.menu);
        assertThat(this.buffer, is(expected));
    }

    private void newStartUI(String... answers) throws DBException {
        new StartUI(
                new InputFromSupplier(
                        new LinkedList<>(Arrays.asList(answers))::poll,
                        this.writer
                ),
                this.writer,
                this.tracker
        ).init();
    }
}
