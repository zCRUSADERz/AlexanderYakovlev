package ru.job4j;

import java.io.File;
import java.util.*;

/**
 * Search, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.01.2019
 */
public class Search {

    /**
     * Найти все файлы с указанными расшрениями в файловом каталоге.
     * @param parent директория в которой будем искать файлы.
     * @param exts расширения файлов.
     * @return файлы с указанными расширениями.
     */
    public List<File> files(String parent, List<String> exts) {
        final List<File> result = new ArrayList<>();
        final File root = new File(parent);
        if (!root.exists() || !root.isDirectory()) {
            throw new IllegalArgumentException(String.format(
                    "Path: %s, is not a directory.", parent
            ));
        }
        final Queue<File> queue = new LinkedList<>(Collections.singleton(root));
        while (!queue.isEmpty()) {
            final File dir = queue.poll();
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isDirectory()) {
                    queue.offer(file);
                    continue;
                }
                if (exts.stream().anyMatch(ext -> file.getName().endsWith(ext))) {
                    result.add(file);
                }
            }
        }
        return result;
    }
}
