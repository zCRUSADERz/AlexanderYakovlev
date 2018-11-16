package ru.job4j.pool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void testThreadPool() throws InterruptedException {
        List<Integer> result = new ArrayList<>(10);
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            pool.add(new SimpleWork(result));
        }
        Thread.sleep(100);
        assertThat(result.size(), is(10));
    }

}