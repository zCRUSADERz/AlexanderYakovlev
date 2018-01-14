package ru.job4j.tracker;

import ru.job4j.tracker.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Tracker item.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 * @version 2.0
 */
public class Tracker {
    private final ArrayList<Item> items = new ArrayList<>();
    private int nextId = 1;

    /**
     * Add item in tracker.
     * @param item - item.
     * @return - item.
     */
    public Item add(Item item) {
        item.setId(generateId());
        items.add(item);
        return item;
    }

    /**
     * Replace Item in tracker by id.
     * @param id - id item which replace.
     * @param item - new item.
     */
    public void replace(String id, Item item) {
        int index = findIndexById(id);
        if (index != -1) {
            item.setId(items.get(index).getId());
            items.set(index, item);
        }
    }

    /**
     * Delete item by id.
     * @param id - id item.
     */
    public void delete(String id) {
        int index = findIndexById(id);
        if (index != -1) {
            items.remove(index);
        }
    }

    /**
     * find all items.
     * @return - items in tracker.
     */
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    /**
     * Find items by name.
     * @param key - name.
     * @return - items.
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Find item by id.
     * @param id - id item.
     * @return - item or null if this id is not in tracker items.
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }

    /**
     * Generate unique Id.
     * @return - unique Id.
     */
    private String generateId() {
        return String.valueOf(nextId++);
    }

    /**
     * Find Index by id.
     * @param id - id
     * @return - index or -1 if this id is not in tracker items.
     */
    private int findIndexById(String id) {
        int result = -1;
        ListIterator<Item> iterator = items.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                result = iterator.previousIndex();
                break;
            }
        }
        return result;
    }
}
