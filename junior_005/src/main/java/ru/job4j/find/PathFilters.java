package ru.job4j.find;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Path filters.
 * Хранилище различных фильтров для поиска файла.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.02.2019
 */
public final class PathFilters {
    private final Map<String, Function<String, Predicate<Path>>> pathFilters;

    /**
     * Дополнительный конструктор содержащий по умолчанию несколько фильтров:
     * Ключ: "m" соответствует фильтру,
     *  который использует "glob" паттерн для поиска файла.
     * Ключ: "f" соответствует фильтру,
     *  который проверяет на полное совпадение имя файла.
     * Ключ: "r" соответствует фильтру,
     *  который использует регулярное выражение для поиска.
     */
    public PathFilters() {
        this(Map.ofEntries(
                Map.entry(
                        "m",
                        (Function<String, Predicate<Path>>) searchString -> new Predicate<>() {
                            private final PathMatcher matcher = FileSystems
                                    .getDefault()
                                    .getPathMatcher("glob:" + searchString);

                            @Override
                            public boolean test(final Path path) {
                                System.out.println(path);
                                return this.matcher.matches(path);
                            }
                        }
                ),
                Map.entry(
                        "f",
                        (Function<String, Predicate<Path>>) searchString ->
                                (Predicate<Path>) path -> path.endsWith(searchString)
                ),
                Map.entry(
                        "r",
                        (Function<String, Predicate<Path>>) searchString -> new Predicate<>() {
                            private final PathMatcher matcher = FileSystems
                                    .getDefault()
                                    .getPathMatcher("regex:" + searchString);

                            @Override
                            public boolean test(final Path path) {
                                return this.matcher.matches(path);
                            }
                        }
                )
        ));
    }

    public PathFilters(
            final Map<String, Function<String, Predicate<Path>>> pathFilters) {
        this.pathFilters = pathFilters;
    }

    /**
     * Возвращает фильтр для поиска файла.
     * @param key ключ для выбора определенного фильтра.
     * @param searchString строка, необходимая фильтру для поиска
     *                     определенного файла.
     * @return фильтр для поиска файла.
     */
    public final Predicate<Path> pathFilter(final String key,
                                            final String searchString) {
        final Optional<Function<String, Predicate<Path>>> optFilterBuilder
                = Optional.ofNullable(this.pathFilters.get(key));
        if (optFilterBuilder.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                    "Filter for key: %s missing.", key
            ));
        }
        return optFilterBuilder.get().apply(searchString);
    }
}
