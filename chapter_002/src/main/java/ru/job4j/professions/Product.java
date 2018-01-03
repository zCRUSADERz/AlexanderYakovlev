package ru.job4j.professions;

/**
 * Product.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Product {
    private String name;

    /**
     * Product
     * @param name - product name.
     */
    public Product(String name) {
        this.name = name;
    }

    /**
     * Getter.
     * @return - name.
     */
    public String getName() {
        return name;
    }
}
