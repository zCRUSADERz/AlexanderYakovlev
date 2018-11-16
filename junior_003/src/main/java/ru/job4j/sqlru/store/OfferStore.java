package ru.job4j.sqlru.store;

import ru.job4j.sqlru.utils.SimpleDate;

/**
 * Offer store interface.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public interface OfferStore extends AutoCloseable {

    /**
     * Find by offer id, and return true if found.
     * @param id offer id.
     * @return true if found.
     * @throws DBException DB error.
     */
    boolean findById(int id) throws DBException;

    /**
     * Save offer to store.
     * @param id offer id.
     * @param title offer title.
     * @param text offer description.
     * @param created offer created date.
     * @param url url to offer.
     * @throws DBException if throw SQLException.
     */
    void save(int id, String title, String text,
              SimpleDate created, String url) throws DBException;
}
