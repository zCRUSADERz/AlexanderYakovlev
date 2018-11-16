package ru.job4j.cache;

/**
 * Кэшированная модель.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.04.2018
 */
public class SimpleCachedModel implements CachedModel {
    private final Model originModel;
    private final long version;

    /**
     * @param originModel основная модель.
     */
    public SimpleCachedModel(Model originModel) {
        this(originModel, 0);
    }

    /**
     * @param originModel основная модель.
     * @param version версия кэшированной модели.
     */
    public SimpleCachedModel(Model originModel, long version) {
        this.originModel = originModel;
        this.version = version;
    }

    @Override
    public Model origin() {
        return originModel;
    }

    @Override
    public String key() {
        return originModel.key();
    }

    @Override
    public long version() {
        return version;
    }

    /**
     * Свряет текущую версию с версией друггой кэшированной модели.
     * @param cachedModel другая кэшированная модель для сверки версий.
     * @return true, если версии равны.
     */
    @Override
    public boolean checkVersion(CachedModel cachedModel) {
        checkOriginModel(cachedModel);
        return cachedModel.version() == version;
    }

    /**
     * Возвращает новую кэшированную модель на основе текущей с
     * инкрементированной версией.
     * @param cachedModel - другая кэшированная модель для сверки.
     * @return - новая кэшированная модель на основе текущей.
     */
    @Override
    public CachedModel updateVersion(CachedModel cachedModel) {
        checkOriginModel(cachedModel);
        if (!checkVersion(cachedModel)) {
            throw new IllegalArgumentException("Models have different versions.");
        }
        return new SimpleCachedModel(originModel, version + 1);
    }

    private void checkOriginModel(CachedModel cachedModel) {
        if (this.originModel != cachedModel.origin()) {
            throw new IllegalArgumentException("Cached models have different origin models.");
        }
    }
}
