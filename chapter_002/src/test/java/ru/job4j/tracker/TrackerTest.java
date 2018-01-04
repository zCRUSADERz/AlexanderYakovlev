package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.models.Item;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tracker test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 * @version 1.0
 */
public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll()[0], is(item));
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItemWithIdOne() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        String resultId = tracker.findAll()[0].getId();
        String expected = "1";
        assertThat(resultId, is(expected));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2", 1234L);
        next.setId(previous.getId());
        tracker.replace(previous.getId(), next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenAddThreeItemAndDeleteSecondThenTrackerHaveTwoItemFirstAndSecond() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription1", 123L);
        tracker.add(item1);
        Item item2 = new Item("test2", "testDescription2", 124L);
        tracker.add(item2);
        Item item3 = new Item("test3", "testDescription3", 125L);
        tracker.add(item3);
        tracker.delete("2");
        Item[] result = tracker.findAll();
        Item[] expected = {item1, item3};
        assertThat(result, is(expected));
    }

    @Test
    public void whennAddThreeItemThenTrackerFindThreeItem() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription1", 123L);
        tracker.add(item1);
        Item item2 = new Item("test2", "testDescription2", 124L);
        tracker.add(item2);
        Item item3 = new Item("test3", "testDescription3", 125L);
        tracker.add(item3);
        Item[] result = tracker.findAll();
        Item[] expected = {item1, item2, item3};
        assertThat(result, is(expected));
    }

    @Test
    public void whenEmptyTrackerthenFindAllResultNull() {
        Tracker tracker = new Tracker();
        Item[] result = tracker.findAll();
        Item[] expected = null;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddFourItemAndTwoOfThemHaveSameNameThenFindByNameReturnTwoItems() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription1", 123L);
        tracker.add(item1);
        Item item2 = new Item("test2", "testDescription2", 124L);
        tracker.add(item2);
        Item item3 = new Item("test3", "testDescription3", 125L);
        tracker.add(item3);
        Item item4 = new Item("test2", "testDescription4", 126L);
        tracker.add(item4);
        Item[] result = tracker.findByName(item2.getName());
        Item[] expected = {item2, item4};
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndFindByNameNotEqualsItemThenResultNull() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription1", 123L);
        tracker.add(item1);
        Item[] result = tracker.findByName("test");
        Item[] expected = null;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndFindByIdNotEqualsItemThenResultNull() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription1", 123L);
        tracker.add(item1);
        Item result = tracker.findById("0");
        Item expected = null;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddItemAndFindByIdThisItemReturnThisItem() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription1", 123L);
        tracker.add(item1);
        Item result = tracker.findById(item1.getId());
        assertThat(result, is(item1));
    }
}