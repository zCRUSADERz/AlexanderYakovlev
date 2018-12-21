package ru.job4j;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Тестирование параллельного поиска текста в файловой системе.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.04.2018
 */
public class ParallelSearchTest {

    @Test
    public void testAll() throws InterruptedException {
        File file = new File(getClass().getClassLoader().getResource("parallelSearch").getFile());
        String root = file.getAbsolutePath();
        ParallelSearch search = new ParallelSearch(root, "123", Arrays.asList("txt", "java"));
        search.init();
        List<String> result = search.result();
        assertThat(
                result,
                containsInAnyOrder(
                        String.format("%s%stext.txt", root, File.separator),
                        String.format("%s%2$sfirst%2$stext2.txt", root, File.separator)
                )
        );
    }
}