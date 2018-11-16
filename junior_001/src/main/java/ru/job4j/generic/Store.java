package ru.job4j.generic;

import ru.job4j.generic.models.Base;

/**
 * Store.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
public interface Store<T extends Base> {

    /**
     * Add model in store.
     * @param model - model.
     */
    void add(T model);

    /**
     * Replace model with id an other model.
     * @param id - id model.
     * @param model - model.
     * @return - true, if replaced.
     */
    boolean replace(String id, T model);

    /**
     * Delete model with id.
     * @param id - id model to be deleted.
     * @return - true, if deleted
     */
    boolean delete(String id);

    /**
     * Find model in store by Id.
     * @param id - model id.
     * @return - model.
     */
    T findById(String id);
}
