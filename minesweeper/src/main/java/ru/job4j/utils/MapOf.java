package ru.job4j.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * MapOf.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.03.2019
 */
public class MapOf<K, V> {
    private final Collection<Map.Entry<K, V>> entries;

    @SafeVarargs
    public MapOf(final Map.Entry<K, V> ...entries) {
        this.entries = Arrays.asList(entries);
    }

    /**
     * Map of entries.
     * @return hash map with entries.
     */
    public final Map<K, V> map() {
        final Map<K, V> result = new HashMap<>(this.entries.size());
        this.entries
                .forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        return result;
    }
}
