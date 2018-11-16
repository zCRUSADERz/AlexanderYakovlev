package ru.job4j.professions;

/**
 * Human.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Human {
    private String name;
    boolean healthy = true;

    /**
     * Constructor
     * @param name - human name.
     */
    public Human(String name) {
        this.name = name;
    }

    /**
     * Getter
     * @return - name.
     */
    public String getName() {
        return name;
    }

    /**
     * Is the person healty?.
     * @return - true if healthy.
     */
    public boolean isHealthy() {
        return healthy;
    }

    /**
     * Setter.
     * @param healthy - true if healthy.
     */
    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
