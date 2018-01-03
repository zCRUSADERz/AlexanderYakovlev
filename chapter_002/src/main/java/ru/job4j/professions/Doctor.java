package ru.job4j.professions;

/**
 * Doctor.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Doctor extends Profession {

    /**
     * Constructor.
     * @param name - person name.
     * @param diploma - diploma.
     */
    public Doctor(String name, Diploma diploma) {
        super(name, diploma);
    }

    /**
     * Healing.
     * @param human - human.
     */
    public void heal(Human human) {
        if (!human.isHealthy()) {
            human.setHealthy(true);
            System.out.println(
                    "Доктор " + getName() + " вылечил пациента " + human.getName()
            );
        } else {
            System.out.println("Пациент " + human.getName() + " здоров.");
        }
    }
}
