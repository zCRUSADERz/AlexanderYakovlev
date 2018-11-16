package ru.job4j.xml.xslt.jdbc;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Список записей.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
@XmlRootElement
public class Entries {
    @XmlElement(name = "entry")
    private List<Entry> listEntries;

    public Entries() {
    }

    public Entries(final List<Entry> listEntries) {
        this.listEntries = listEntries;
    }

    @Override
    public String toString() {
        return listEntries.toString();
    }
}
