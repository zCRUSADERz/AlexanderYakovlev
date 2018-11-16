package ru.job4j.professions;

/**
 * Engineer.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Engineer extends Profession {

    /**
     * Constructor.
     * @param name - engineer name.
     * @param diploma - diploma
     */
    public Engineer(String name, Diploma diploma) {
        super(name, diploma);
    }

    /**
     * Manage production.
     * @param factory - factory.
     * @return - product.
     */
    public Product manageProduction(Factory factory) {
        System.out.println("Инженер приступает к руководству на фабрике " + factory.getName());
        Product product = factory.production();
        System.out.println("Товар " + product.getName() + " был произведен.");
        System.out.println("Инженер выполнил свою работу.");
        return product;
    }
}
