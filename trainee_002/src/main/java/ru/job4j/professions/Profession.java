package ru.job4j.professions;

/**
 * Profession.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Profession {
    private String name;
    private Diploma diploma;

    /**
     * Constructor.
     * @param name - name person.
     * @param diploma - diploma.
     */
    Profession(String name, Diploma diploma) {
        this.name = name;
        this.diploma = diploma;
    }

    /**
     * Getter.
     * @return - name
     */
    public String getName() {
        return name;
    }
}
