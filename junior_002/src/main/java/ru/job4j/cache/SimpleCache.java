package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Потокобезопасный кэш моделей.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.04.2018
 */
@ThreadSafe
public class SimpleCache {
    private final ConcurrentMap<String, CachedModel> cache;

    public SimpleCache() {
        this(new ConcurrentHashMap<>());
    }

    public SimpleCache(ConcurrentMap<String, CachedModel> cache) {
        this.cache = cache;
    }

    /**
     * Добавить модель в кэш.
     * @param cachedModel кэшированная модель.
     * @return true, если модель добавлена.
     */
    public boolean add(CachedModel cachedModel) {
        boolean result = false;
        CachedModel putResult = cache.putIfAbsent(cachedModel.key(), cachedModel);
        if (putResult == null) {
            result = true;
        }
        return result;
    }

    /**
     * Обновить модель в кэше.
     * @param cachedModel обновленная модель.
     * @throws OptimisticException если, модель из кэша была ранее изменена,
     * то будет выброшенна ошибка.
     */
    public void update(CachedModel cachedModel) throws OptimisticException {
        cache.computeIfPresent(cachedModel.key(), (key, oldCachedModel) -> {
            if (!cachedModel.checkVersion(oldCachedModel)) {
                throw new OptimisticException(
                        "The cached model has already been updated"
                );
            }
            return cachedModel.updateVersion(oldCachedModel);
        });
    }

    /**
     * Удалить модель из кэша.
     * @param cachedModel удаляемая модель.
     * @return true, если удалена.
     */
    public boolean delete(CachedModel cachedModel) {
        boolean result = false;
        CachedModel removeResult = cache.remove(cachedModel.key());
        if (removeResult != null) {
            result = true;
        }
        return result;
    }
}
