package ru.job4j.professions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Doctor test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 16.01.2018
 * @version 1.0
 */
public class DoctorTest {

    @Test
    public void whenDoctorDontHealHuman() {
        Doctor doctor = new Doctor("Doctor", new Diploma("NSU", "medical"));
        Human pacient = new Human("Bob");
        pacient.setHealthy(true);
        doctor.heal(pacient);
        boolean result = pacient.isHealthy();
        assertThat(result, is(true));
    }

    @Test
    public void whenDoctorHealHuman() {
        Doctor doctor = new Doctor("Doctor", new Diploma("NSU", "medical"));
        Human pacient = new Human("Bob");
        pacient.setHealthy(false);
        doctor.heal(pacient);
        boolean result = pacient.isHealthy();
        assertThat(result, is(true));
    }

}