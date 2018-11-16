package ru.job4j.tracker.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Item.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 */
public class Item {
    private final String id;
    private final String name;
    private final String desc;
    private final long created;

    public Item(String id, String name, String desc, long created) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    /**
     * Item to string.
     */
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return String.format(
                "Заявка: %s, Id: %s%n"
                        + "Описание: %s%n"
                        + "Дата создания заявки: %s%n"
                        + "-------------------------",
                name, id, desc, format.format(new Date(created))
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return created == item.created
                && Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(desc, item.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, created);
    }
}
