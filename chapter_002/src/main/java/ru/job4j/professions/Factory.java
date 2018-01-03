package ru.job4j.professions;

/**
 * Factory.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Factory {
    private String name;
    private String productName;

    /**
     * Constructor
     * @param name - factory name.
     * @param productName - product name.
     */
    public Factory(String name, String productName) {
        this.name = name;
        this.productName = productName;
    }

    /**
     * Getter.
     * @return - name.
     */
    public String getName() {
        return name;
    }
    /**
     * Production
     * @return - product.
     */
    public Product production() {
        return new Product(productName);
    }
}
