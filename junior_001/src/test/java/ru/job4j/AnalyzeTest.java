package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalyzeTest {
    private final Analyze analyze = new Analyze();
    @Test
    public void whenPreviousAndCurrentListEmpty() {
        final Analyze.Info result = this.analyze.diff(
                Collections.emptyList(),
                Collections.emptyList()
        );
        assertThat(result, is(new Analyze.Info()));
    }

    @Test
    public void whenAllDeleted() {
        final Analyze.Info result = this.analyze.diff(
                Arrays.asList(
                        new Analyze.User(1, "user1"),
                        new Analyze.User(2, "user2"),
                        new Analyze.User(3, "user3")
                ),
                Collections.emptyList()
        );
        assertThat(result, is(new Analyze.Info(0, 0, 3)));
    }

    @Test
    public void whenAllAdded() {
        final Analyze.Info result = this.analyze.diff(
                Collections.emptyList(),
                Arrays.asList(
                        new Analyze.User(4, "user4"),
                        new Analyze.User(5, "user5"),
                        new Analyze.User(6, "user6")
                )
        );
        assertThat(result, is(new Analyze.Info(3, 0, 0)));
    }

    @Test
    public void allOptions() {
        final Analyze.Info result = this.analyze.diff(
                Arrays.asList(
                        new Analyze.User(7, "user7"),
                        new Analyze.User(8, "user8"),
                        new Analyze.User(9, "user9"),
                        new Analyze.User(10, "user10"),
                        new Analyze.User(11, "user11")
                ),
                Arrays.asList(
                        new Analyze.User(7, "user7"),
                        new Analyze.User(9, "user99"),
                        new Analyze.User(10, "user101"),
                        new Analyze.User(12, "user12"),
                        new Analyze.User(13, "user13")
                )
        );
        assertThat(result, is(new Analyze.Info(2, 2, 2)));
    }
}