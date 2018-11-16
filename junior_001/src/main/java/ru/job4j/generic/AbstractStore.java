package ru.job4j.generic;

import ru.job4j.generic.models.Base;

/**
 * Abstract store.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    private final SimpleArray<T> store;

    /**
     * Default constructor.
     * @param store - store.
     */
    AbstractStore(SimpleArray<T> store) {
        this.store = store;
    }

    /**
     * Add model in store.
     * @param model - model.
     */
    @Override
    public void add(T model) {
        store.add(model);
    }

    /**
     * Replace model with id an other model.
     * @param id - id model.
     * @param model - model.
     * @return - true, if replaced.
     */
    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        int index = findIndexById(id);
        if (index != -1) {
            store.set(index, model);
            result = true;
        }
        return result;
    }

    /**
     * Delete model with id.
     * @param id - id model to be deleted.
     * @return - true, if deleted
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        int index = findIndexById(id);
        if (index != -1) {
            store.delete(index);
            result = true;
        }
        return result;
    }

    /**
     * Find model in store by Id.
     * @param id - model id.
     * @return - model.
     */
    @Override
    public T findById(String id) {
        T result = null;
        for (T t : store) {
            if (t.getId().equals(id)) {
                result = t;
                break;
            }
        }
        return result;
    }

    /**
     * Find for the model index by its id.
     * @param id - model id.
     * @return - index, or -1, if model not found.
     */
    private int findIndexById(String id) {
        int result = -1;
        int index = 0;
        for (T t : store) {
            if (t.getId().equals(id)) {
                result = index;
                break;
            }
            index++;
        }
        return result;
    }
}
