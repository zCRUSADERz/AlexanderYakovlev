package ru.job4j.professions;

import org.hamcrest.core.IsNot;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Teacher test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 16.01.2018
 * @version 1.0
 */
public class TeacherTest {

    @Test
    public void whenTeacherTeachStudent() {
        Teacher teacher = new Teacher("Teacher", new Diploma("NSU", "educator"));
        Student student = new Student("Student");
        Knowledge result = teacher.teach(student);
        assertThat(result, is(notNullValue()));
    }
}