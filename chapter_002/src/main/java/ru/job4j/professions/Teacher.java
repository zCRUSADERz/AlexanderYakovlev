package ru.job4j.professions;

/**
 * Teacher.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2018
 * @version 1.0
 */
public class Teacher extends Profession {
    private int lesson = 0;

    /**
     * Constructor.
     * @param name - name person.
     * @param diploma - diploma.
     */
    public Teacher(String name, Diploma diploma) {
        super(name, diploma);
    }

    /**
     * Teach.
     * @param student - student who learn lesson.
     * @return - knowledge.
     */
    public Knowledge teach(Student student) {
        System.out.println("Учитель читает лекцию № " + lesson);
        student.learn();
        System.out.println("Студент " + student.getName() + " прослушал лекцию.");
        return new Knowledge(lesson++);
    }
}
