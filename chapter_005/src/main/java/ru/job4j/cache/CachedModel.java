package ru.job4j.cache;

/**
 * Кэшированная модель.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.04.2018
 */
public interface CachedModel {
    Model origin();
    String key();
    long version();
    boolean checkVersion(CachedModel cachedModel);
    CachedModel updateVersion(CachedModel cachedModel);
}
