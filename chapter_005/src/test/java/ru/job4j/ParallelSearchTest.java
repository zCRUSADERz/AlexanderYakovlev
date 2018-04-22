package ru.job4j;

import org.junit.Ignore;

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

    @Ignore
    public void testAll() throws InterruptedException {
        ParallelSearch search = new ParallelSearch("D:\\Q", "123", Arrays.asList("txt", "java"));
        search.init();
        List<String> result = search.result();
        List<String> expected = Arrays.asList("D:\\Q\\B\\text2.txt", "D:\\Q\\text.txt");
        assertThat(result, is(expected));
    }
}