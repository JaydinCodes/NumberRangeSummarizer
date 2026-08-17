package numberrangesummarizer;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NumberRangeSummarizerImplTest {

    private final NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();
    @Test
    void shouldCollectCommaDelimitedNumbers(){
        Collection<Integer> result = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");

        assertEquals(
                Arrays.asList(1,3,6,7,8,12,13,14,15,21,22,23,24,31), result
        );
    }

    @Test
    void shouldSummarizeSequentialNumbersIntoRanges(){
        Collection<Integer> input = Arrays.asList(1,3,6,7,8,12,13,14,15,21,22,23,24,31);

        String result = summarizer.summarizeCollection(input);

        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
    }

    @Test
    void shouldRejectNonNumericValues(){
        assertThrows(IllegalArgumentException.class, () -> {
            summarizer.collect("1, hello, 3, world");
        });
    }

    @Test
    void shouldRejectMissingValues(){
        assertThrows(IllegalArgumentException.class, () -> {
           summarizer.collect("1, ,3");
        });
    }

    @Test
    void shouldAcceptWhitespaceAroundNumbers(){
        Collection<Integer> result = summarizer.collect("1,2, 3");

        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    void shouldRejectEmptyValuesBetweenCommas(){
        assertThrows(IllegalArgumentException.class, () -> {
           summarizer.collect("1,,3");
        });
    }

    @Test
    void shouldReturnEmptyCollectionForEmptyInput(){
        Collection<Integer> result = summarizer.collect("");

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldRejectTrailingMissingValue(){
        assertThrows(IllegalArgumentException.class, () -> {
            summarizer.collect("1,2,");
        });
    }

    @Test
    void shouldRejectLeadingMissingValue(){
        assertThrows(IllegalArgumentException.class, () -> {
            summarizer.collect(",2,3");
        });
    }

    @Test
    void shouldSummarizeTwoSequentialNumbersAsRange() {
        assertEquals("1-2", summarizer.summarizeCollection(Arrays.asList(1, 2)));
    }

    @Test
    void shouldSortUnorderedInputBeforeSummarizing() {
        // [5, 3, 4] -> "3-5"
        assertEquals("3-5", summarizer.summarizeCollection(Arrays.asList(5, 4, 3)));
    }

    @Test
    void shouldIgnoreDuplicateValues() {
        // [1, 1, 2, 3] -> "1-3"
        assertEquals("1-3", summarizer.summarizeCollection(Arrays.asList(1,1,2,3)));
    }

    @Test
    void shouldReturnEmptyStringForEmptyCollection() {
        // [] -> ""
        assertEquals("", summarizer.summarizeCollection(Arrays.asList()));
    }

    @Test
    void shouldKeepNonSequentialNumbersSeparate() {
        // [1, 3, 5] -> "1, 3, 5"
        assertEquals("1, 3, 5", summarizer.summarizeCollection(Arrays.asList(1, 3, 5)));
    }

    @Test
    void shouldFormatNegativeSequentialRange() {
        assertEquals("-3--1, 2", summarizer.summarizeCollection(Arrays.asList(-3, -2, -1, 2)));
    }

    @Test
    void shouldSummarizeSingleNumber() {
        assertEquals("5", summarizer.summarizeCollection(Arrays.asList(5)));
    }

    @Test
    void shouldHandleBoundaryValuesWithoutOverflow() {
        assertEquals("2147483646-2147483647",
                summarizer.summarizeCollection(Arrays.asList(Integer.MAX_VALUE - 1, Integer.MAX_VALUE)));
    }

    @Test
    void shouldThrowOnNullCollection() {
        assertThrows(NullPointerException.class, () -> summarizer.summarizeCollection(null));
    }



}
