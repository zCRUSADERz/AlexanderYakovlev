package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование кэша моделей.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.04.2018
 */
public class SimpleCachedModelTest {

    @Test (expected = IllegalArgumentException.class)
    public void whenUpdateVersionWithDifferentModelVersionsThenThrowException() {
        Model origin = () -> "1";
        CachedModel model = new SimpleCachedModel(origin, 1);
        CachedModel newModel = new SimpleCachedModel(origin, 2);
        newModel.updateVersion(model);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenUpdateVersionWithDifferentOriginModelThenThrowException() {
        CachedModel model = new SimpleCachedModel(() -> "2");
        CachedModel newModel = new SimpleCachedModel(() -> "3");
        newModel.updateVersion(model);
    }

    @Test
    public void whenUpdateVersionThenNewCachedModelWithincrementedVersion() {
        Model origin = () -> "4";
        CachedModel model = new SimpleCachedModel(origin, 3);
        CachedModel newModel = new SimpleCachedModel(origin, 3);
        CachedModel result = newModel.updateVersion(model);
        assertThat(result.origin(), is(origin));
        long resultVersion = result.version();
        long expectedVersion = newModel.version() + 1;
        assertThat(resultVersion, is(expectedVersion));
    }
}