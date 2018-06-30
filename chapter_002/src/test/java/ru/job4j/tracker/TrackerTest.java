package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.models.Item;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tracker test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 */

public class TrackerTest {
    private final Connection connection = new PostgresDB().getConnection();
    private Tracker tracker;
    private Date created;

    public TrackerTest() throws Exception {
    }

    @Before
    public void initTracker() throws Exception {
        this.tracker = new Tracker(this.connection);
        this.tracker.deleteAllItems();
        created = new Date();
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() throws Exception {
        Item item = new Item("1", "test1", "testDescription", created.getTime());
        tracker.add("test1", "testDescription", created.getTime());
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceItemThenReturnNewItem() throws Exception {
        tracker.add("test1", "testDescription", created.getTime());
        tracker.replace("1", "new item", "new description");
        Item expected = new Item(
                "1", "new item", "new description", created.getTime()
        );
        assertThat(tracker.findById("1"), is(expected));
    }

    @Test
    public void whenReplaceNonExistItemThenNothing() throws Exception {
        Item expected = new Item(
                "1", "test1", "testDescription", created.getTime()
        );
        tracker.add("test1", "testDescription", created.getTime());
        tracker.replace("2", "new item", "new desc");
        assertThat(tracker.findById("1"), is(expected));
    }

    @Test
    public void whenAddThreeItemAndDeleteSecondThenTrackerHaveTwoItemFirstAndSecond()
            throws Exception {
        Item item1 = new Item(
                "1", "test1", "testDescription1", created.getTime()
        );
        tracker.add("test1", "testDescription1", created.getTime());
        tracker.add("test2", "testDescription2", created.getTime());
        Item item3 = new Item(
                "3", "test3", "testDescription3", created.getTime()
        );
        tracker.add("test3", "testDescription3", created.getTime());
        tracker.delete("2");
        List<Item> result = tracker.findAll();
        List<Item> expected = Arrays.asList(item1, item3);
        assertThat(result, is(expected));
    }

    @Test
    public void whennAddThreeItemThenTrackerFindThreeItem() throws Exception {
        Item item1 = new Item(
                "1", "test1", "testDescription1", created.getTime()
        );
        tracker.add("test1", "testDescription1", created.getTime());
        Item item2 = new Item(
                "2", "test2", "testDescription2", created.getTime()
        );
        tracker.add("test2", "testDescription2", created.getTime());
        Item item3 = new Item(
                "3", "test3", "testDescription3", created.getTime()
        );
        tracker.add("test3", "testDescription3", created.getTime());
        List<Item> result = tracker.findAll();
        List<Item> expected = Arrays.asList(item1, item2, item3);
        assertThat(result, is(expected));
    }

    @Test
    public void whenEmptyTrackerThenFindAllResultEmptyList() throws Exception {
        List<Item> result = tracker.findAll();
        int expected = 0;
        assertThat(result.size(), is(expected));
    }

    @Test
    public void whenAddFourItemAndTwoOfThemHaveSameNameThenFindByNameReturnTwoItems() throws Exception {
        tracker.add("test1", "testDescription1", created.getTime());
        Item item2 = new Item(
                "2", "test2", "testDescription2", created.getTime()
        );
        tracker.add("test2", "testDescription2", created.getTime());
        tracker.add("test3", "testDescription3", created.getTime());
        Item item4 = new Item(
                "4", "test2", "testDescription3", created.getTime()
        );
        tracker.add("test2", "testDescription3", created.getTime());
        List<Item> result = tracker.findByName("test2");
        List<Item> expected = Arrays.asList(item2, item4);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndFindByNameNotEqualsItemThenResultEmptyList() throws Exception {
        tracker.add("test1", "testDescription1");
        List<Item> result = tracker.findByName("test");
        int expected = 0;
        assertThat(result.size(), is(expected));
    }

    @Test
    public void whenAddItemAndFindByIdNotEqualsItemThenResultNull() throws Exception {
        tracker.add("test1", "testDescription1");
        Item result = tracker.findById("0");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenAddItemAndFindByIdThisItemReturnThisItem() throws Exception {
        Item expected = new Item(
                "1", "test1", "testDescription1", created.getTime()
        );
        tracker.add("test1", "testDescription1", created.getTime());
        Item result = tracker.findById("1");
        assertThat(result, is(expected));
    }
}
