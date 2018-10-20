package ru.job4j.heroes.health;

import ru.job4j.heroes.Hero;

public interface HeroHealth {
    void takeDamage(Hero heroOwner, int damage);
}
