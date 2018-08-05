package ru.job4j;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
        List<String> expected = Arrays.asList(
                root + File.separator + "first" + File.separator + "text2.txt",
                root + File.separator + "text.txt"
        );
        assertThat(result, is(expected));
    }
}