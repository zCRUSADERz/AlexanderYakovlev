package ru.job4j.xml.heroes.types;

import org.w3c.dom.Node;
import ru.job4j.heroes.HeroType;

/**
 * XMLHeroType.
 * Тип героя(Например: маг, лучникб воин и др.).
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface XMLHeroType extends HeroType {

    /**
     * Находит узел содержащий данные героя этого типа.
     * @param heroes узел указывающий на всех героев.
     * @return узел содержащий данные героя этого типа.
     */
    Node findHeroNode(Node heroes);
}
