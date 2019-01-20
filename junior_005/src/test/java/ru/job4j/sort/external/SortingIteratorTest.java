package ru.job4j.sort.external;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Sorting iterator, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.01.2019
 */
public class SortingIteratorTest {
    private final Comparator<String> lineComparator
            = Comparator.comparing(String::length);

    @Test
    public void whenNoOneIteratorHasNextElementThenHasNextReturnFalse() {
        final SortingIterator<String> iterator = new SortingIterator<>(
                Collections.emptyIterator(),
                Collections.emptyIterator(),
                this.lineComparator
        );
        assertFalse(iterator.hasNext());
    }

    @Test
    public void whenOneHasElementThenHasNextReturnTrue() {
        assertTrue(
                new SortingIterator<>(
                        Collections.singletonList("1").iterator(),
                        Collections.emptyIterator(),
                        this.lineComparator
                ).hasNext()
        );
        assertTrue(
                new SortingIterator<>(
                        Collections.emptyIterator(),
                        Collections.singletonList("25").iterator(),
                        this.lineComparator
                ).hasNext()
        );
    }

    @Test
    public void whenIteratorsHasSomeElementsThenHashNextReturnTrue() {
        assertTrue(
                new SortingIterator<>(
                        Collections.singletonList("74").iterator(),
                        Collections.singletonList("20 r").iterator(),
                        this.lineComparator
                ).hasNext()
        );
        assertTrue(
                new SortingIterator<>(
                        Arrays.asList("3 gry", "54 gee5", "get45").iterator(),
                        Arrays.asList("gq", "ret 345").iterator(),
                        this.lineComparator
                ).hasNext()
        );
    }

    @Test
    public void whenHasNextSeveralTimesThenAllReturnTrue() {
        final SortingIterator<String> iterator = new SortingIterator<>(
                Collections.singletonList("gert").iterator(),
                Collections.singletonList("k36 fg").iterator(),
                this.lineComparator
        );
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
    }

    @Test (expected = NoSuchElementException.class)
    public void whenNextOnEmptyIteratorsThenThrowNoSuchElementException() {
        new SortingIterator<>(
                Collections.emptyIterator(),
                Collections.emptyIterator(),
                this.lineComparator
        ).next();
    }

    @Test
    public void whenGetAllElementsThenHasNextReturnFalse() {
        final SortingIterator<String> iterator = new SortingIterator<>(
                Collections.singletonList("ner34").iterator(),
                Arrays.asList("gt", "jryt3", "34tg").iterator(),
                this.lineComparator
        );
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void whenGetAllElementsThenElementsSorted() {
        final List<String> leftLines = Arrays.asList(
                "3-fry",
                "5-gqtvq",
                "6-.ertyj"
        );
        final List<String> rightLines = Arrays.asList(
                "1-l",
                "4-k45g",
                "8-0934hqbu",
                "10-pbyema591k"
        );
        final SortingIterator<String> iterator = new SortingIterator<>(
                leftLines.iterator(),
                rightLines.iterator(),
                this.lineComparator
        );
        final List<String> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);
        final List<String> expected = Stream
                .concat(leftLines.stream(), rightLines.stream())
                .sorted(this.lineComparator)
                .collect(Collectors.toList());
        assertThat(result, is(expected));
    }
}