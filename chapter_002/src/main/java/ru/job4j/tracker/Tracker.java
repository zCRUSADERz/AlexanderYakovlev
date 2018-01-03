package ru.job4j.tracker;

/**
 * Tracker item.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 * @version 1.0
 */
public class Tracker {
    private Item[] items = new Item[100];
    private int position = 0;
    private int nextId = 1;

    /**
     * Add item in tracker.
     * @param item - item.
     * @return - item.
     */
    public Item add(Item item) {
        item.setId(generateId());
        items[position++] = item;
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
            item.setId(items[index].getId());
            items[index] = item;
        }
    }

    /**
     * Delete item by id.
     * @param id - id item.
     */
    public void delete(String id) {
        int index = findIndexById(id);
        if (index != -1) {
            System.arraycopy(items, index + 1, items, index, position - 1 - index);
            position--;
        }
    }

    /**
     * find all items.
     * @return - items in tracker.
     */
    public Item[] findAll() {
        Item[] result;
        if (position != 0) {
            result = new Item[position];
            System.arraycopy(items, 0, result, 0, position);
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Find items by name.
     * @param key - name.
     * @return - items.
     */
    public Item[] findByName(String key) {
        Item[] tmp = new Item[position];
        int size = 0;
        for (int i = 0; i < position; i++) {
            if (items[i].getName().equals(key)) {
                tmp[size++] = items[i];
            }
        }
        Item[] result;
        if (size != 0) {
            result = new Item[size];
            System.arraycopy(tmp, 0, result, 0, size);
        } else {
            result = null;
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
        int index = findIndexById(id);
        if (index != -1) {
            result = items[index];
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
        for (int i = 0; i < position; i++) {
            if (items[i].getId().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }
}
