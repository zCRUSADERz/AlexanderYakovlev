package ru.job4j.heroes;

public interface HeroFactory {

    Hero hero(String squadName, String raceName);
}
