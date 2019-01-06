package ru.job4j.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinePositionTest {

    @Test
    public void whenFirstLineHasLessCharactersThenFirstLess() {
        final SortedFileLines.LinePosition first = new SortedFileLines.LinePosition(0, 1, 7);
        final SortedFileLines.LinePosition second = new SortedFileLines.LinePosition(0, 1, 14);
        assertTrue(first.compareTo(second) < 0);
    }

    @Test
    public void whenSecondLineHasLessCharactersThenFirstLess() {
        final SortedFileLines.LinePosition first = new SortedFileLines.LinePosition(7, 6, 17);
        final SortedFileLines.LinePosition second = new SortedFileLines.LinePosition(8, 8, 4);
        assertTrue(first.compareTo(second) > 0);
    }

    @Test
    public void whenLinesHasSamePositionAndChars() {
        final SortedFileLines.LinePosition first = new SortedFileLines.LinePosition(9, 9, 11);
        final SortedFileLines.LinePosition second = new SortedFileLines.LinePosition(9, 10, 11);
        assertEquals(0, first.compareTo(second));
    }

    @Test
    public void whenLinesHasSameNumOfCharactersAndNotSamePosition() {
        final SortedFileLines.LinePosition first = new SortedFileLines.LinePosition(7, 11, 12);
        final SortedFileLines.LinePosition second = new SortedFileLines.LinePosition(8, 4, 12);
        assertTrue(first.compareTo(second) > 0);
    }
}