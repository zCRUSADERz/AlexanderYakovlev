package ru.job4j.cache;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleCacheTest {
    private ConcurrentMap<String, CachedModel> cache;
    private SimpleCache simpleCache;

    @Before
    public void initCache() {
        cache = new ConcurrentHashMap<>();
        simpleCache = new SimpleCache(cache);
    }

    @Test
    public void whenAddNonExistentModelThenReturnTrue() {
        CachedModel model = new SimpleCachedModel(() -> "1");
        boolean result = simpleCache.add(model);
        assertThat(result, is(true));
        result = cache.containsValue(model);
        assertThat(result, is(true));
    }

    @Test
    public void whenAddExistModelThenReturnTrue() {
        CachedModel model = new SimpleCachedModel(() -> "1");
        simpleCache.add(model);
        boolean result = simpleCache.add(model);
        assertThat(result, is(false));
        int resultSize = cache.size();
        assertThat(resultSize, is(1));
    }

    @Test
    public void whenUpdateNonExistentModelThenNothing() throws OptimisticException {
        CachedModel model = new SimpleCachedModel(() -> "1");
        simpleCache.add(model);
        simpleCache.update(new SimpleCachedModel(() -> "2"));
        assertThat(cache.containsValue(model), is(true));
        assertThat(cache.size(), is(1));
    }

    @Test
    public void whenUpdateNotModifiedModelThenUpdated() throws OptimisticException {
        Model origin = () -> "1";
        CachedModel model = new SimpleCachedModel(origin);
        CachedModel newModel = new SimpleCachedModel(origin);
        simpleCache.add(model);
        simpleCache.update(newModel);
        assertThat(cache.containsValue(model), is(false));
        assertThat(cache.containsValue(newModel), is(false));
        assertThat(cache.size(), is(1));
        long resultVersion = cache.get(model.key()).version();
        assertThat(resultVersion, is(1L));
    }

    @Test (expected = OptimisticException.class)
    public void whenUpdateModifiedModelThenThrowOptimisticException() {
        Model origin = () -> "1";
        CachedModel model = new SimpleCachedModel(origin);
        CachedModel newModel = new SimpleCachedModel(origin, 2);
        simpleCache.add(model);
        simpleCache.update(newModel);
    }

    @Test
    public void whenDeleteNonExistentModelThenReturnFalse() {
        boolean result = simpleCache.delete(new SimpleCachedModel(() -> "1"));
        assertThat(result, is(false));
    }

    @Test
    public void whenDeleteExistModelThenReturnTrue() {
        CachedModel model = new SimpleCachedModel(() -> "1");
        simpleCache.add(model);
        boolean result = simpleCache.delete(model);
        assertThat(result, is(true));
    }
}