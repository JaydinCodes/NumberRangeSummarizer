package numberrangesummarizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NumberRangeSummarizerImplTest {

    @Test
    void canCreateSummarizer(){
        NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

        assertNotNull(summarizer);
    }
}
