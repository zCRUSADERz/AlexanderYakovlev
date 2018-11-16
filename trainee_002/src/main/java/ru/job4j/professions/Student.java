package ru.job4j.professions;

/**
 * Student.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Student {
    private String name;
    private int studiedLectures = 0;

    /**
     * Constructor.
     * @param name - name.
     */
    public Student(String name) {
        this.name = name;
    }

    /**
     * Getter.
     * @return name;
     */
    public String getName() {
        return name;
    }
    /**
     * learn lesson.
     */
    public void learn() {
        studiedLectures++;
    }
}
