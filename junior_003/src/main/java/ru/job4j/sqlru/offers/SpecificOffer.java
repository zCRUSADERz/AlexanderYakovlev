package ru.job4j.sqlru.offers;

import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.store.OfferStore;

/**
 * Вакансия с определенной специализацией.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public interface SpecificOffer {

    /**
     * Сохранить вакансию в хранилище.
     * @param store хранилище вакансий.
     * @throws DBException if store throw DBException.
     */
    void saveToStore(OfferStore store) throws DBException;
}
