package ru.job4j.professions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Engineer test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 16.01.2018
 * @version 1.0
 */
public class EngineerTest {

    @Test
    public void whenEngeneerManageProduction() {
        Engineer engineer = new Engineer("Engineer", new Diploma("NSTU", "technical"));
        Factory factory = new Factory("SPK", "sausage");
        String result = engineer.manageProduction(factory).getName();
        String expected = new Product("sausage").getName();
        assertThat(result, is(expected));
    }
}