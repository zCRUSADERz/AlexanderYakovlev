package ru.job4j.xml.xslt.jdbc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запись.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
@XmlRootElement
public class Entry {
    @XmlElement(name = "field")
    private int field;

    public Entry() {
    }

    public Entry(final int field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return String.valueOf(field);
    }
}
