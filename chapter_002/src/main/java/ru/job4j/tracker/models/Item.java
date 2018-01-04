package ru.job4j.tracker.models;

import java.util.Date;

/**
 * Item.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 * @version 1.0
 */
public class Item {
    private String id;
    private String name;
    private String desc;
    private long created;
    private String[] comments;

    /**
     * Default constructor.
     */
    public Item() {
        name = "";
        desc = "";
        created = new Date().getTime();
    }

    /**
     * Constructor item.
     * @param name - name.
     * @param desc - description.
     * @param created - date of creation.
     */
    public Item(String name, String desc, long created) {
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc != null ? desc : "";
    }

    public long getCreated() {
        return created;
    }
}
