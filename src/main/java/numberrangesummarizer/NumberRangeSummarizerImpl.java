package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class NumberRangeSummarizerImpl implements NumberRangeSummarizer{
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.isEmpty()) {
            return Collections.emptyList();
        }
        return Stream.of(input.split(",", -1))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        List<Integer> sorted = input.stream().distinct().sorted().collect(Collectors.toList());

        if (sorted.isEmpty()){
            return "";
        }

        List<String> parts = new ArrayList<>();
        int rangeStart = sorted.get(0);
        int prev = rangeStart;

        for (int i = 1; i <= sorted.size(); i++){
            Integer current = i < sorted.size() ? sorted.get(i) : null;

            if (current == null || current != prev + 1){
                parts.add(rangeStart == prev ? String.valueOf(rangeStart) : rangeStart + "-" + prev );

                if (current != null){
                    rangeStart = current;
                }
            }

            if (current != null){
                prev = current;
            }

        }

        return String.join(", ", parts);


    }
}
