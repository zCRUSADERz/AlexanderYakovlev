package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.models.Item;

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

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("test name"));
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
}