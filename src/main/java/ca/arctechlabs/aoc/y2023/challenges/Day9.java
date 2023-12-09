package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day9 {
    //https://adventofcode.com/2023/day/9

    public long sumOfExtrapolations(List<String> input, boolean isReversed){
        return input.stream()
                .map(line -> parseHistory(line, isReversed))
                .map(this::extrapolateNextValue)
                .mapToLong(Long::longValue)
                .sum();
    }
    private long extrapolateNextValue(List<Long> values){
        boolean allZero = values.stream().allMatch(value -> value.equals(0L));
        if(allZero){
            return 0L;
        }

        List<Long> differences = new ArrayList<>();
        for(int i=1; i<values.size(); i++){
            differences.add(values.get(i)-values.get(i-1));
        }
        return extrapolateNextValue(differences) + values.get(values.size()-1);
    }
    private List<Long> parseHistory(String line, boolean isReversed){
        List<Long> historys = new ArrayList<>(Arrays.stream(line.split(" "))
                .map(Long::parseLong)
                .toList());
        if(isReversed){
            Collections.reverse(historys);
        }
        return historys;
    }
}
