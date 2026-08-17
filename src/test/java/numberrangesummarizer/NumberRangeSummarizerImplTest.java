package numberrangesummarizer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
