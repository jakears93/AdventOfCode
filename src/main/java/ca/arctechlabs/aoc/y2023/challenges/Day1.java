package ca.arctechlabs.aoc.y2023.challenges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day1 {
    //https://adventofcode.com/2023/day/1

    private final Map<String, Integer> writtenNumbers;

    public Day1(){
        this.writtenNumbers = new HashMap<>();
        this.writtenNumbers.put("one", 1);
        this.writtenNumbers.put("1", 1);
        this.writtenNumbers.put("two", 2);
        this.writtenNumbers.put("2", 2);
        this.writtenNumbers.put("three", 3);
        this.writtenNumbers.put("3", 3);
        this.writtenNumbers.put("four", 4);
        this.writtenNumbers.put("4", 4);
        this.writtenNumbers.put("five", 5);
        this.writtenNumbers.put("5", 5);
        this.writtenNumbers.put("six", 6);
        this.writtenNumbers.put("6", 6);
        this.writtenNumbers.put("seven", 7);
        this.writtenNumbers.put("7", 7);
        this.writtenNumbers.put("eight", 8);
        this.writtenNumbers.put("8", 8);
        this.writtenNumbers.put("nine", 9);
        this.writtenNumbers.put("9", 9);
        this.writtenNumbers.put("zero", 0);
        this.writtenNumbers.put("0", 0);
    }

    public Integer sumOfCalibrationNumbers(List<String> input, boolean enableWrittenNumbers){
        return input
                .stream()
                .map(line -> extractSum(line, enableWrittenNumbers))
                .mapToInt(Integer::intValue)
                .sum();
    }

    public String parseNumber(char[] chars, int index, boolean enableWrittenNumbers){
        String partialLine = String.copyValueOf(chars).substring(index);
        if(partialLine.startsWith("1")) return "1";
        else if(partialLine.startsWith("2")) return "2";
        else if(partialLine.startsWith("3")) return "3";
        else if(partialLine.startsWith("4")) return "4";
        else if(partialLine.startsWith("5")) return "5";
        else if(partialLine.startsWith("6")) return "6";
        else if(partialLine.startsWith("7")) return "7";
        else if(partialLine.startsWith("8")) return "8";
        else if(partialLine.startsWith("9")) return "9";
        else if(partialLine.startsWith("0")) return "0";

        if(enableWrittenNumbers){
            if(partialLine.startsWith("one")) return "one";
            else if(partialLine.startsWith("two")) return "two";
            else if(partialLine.startsWith("three")) return "three";
            else if(partialLine.startsWith("four")) return "four";
            else if(partialLine.startsWith("five")) return "five";
            else if(partialLine.startsWith("six")) return "six";
            else if(partialLine.startsWith("seven")) return "seven";
            else if(partialLine.startsWith("eight")) return "eight";
            else if(partialLine.startsWith("nine")) return "nine";
            else if(partialLine.startsWith("zero")) return "zero";
        }

        return null;
    }

    public Integer extractSum(String line, boolean enableWrittenNumbers){
        Integer first = null;
        Integer last = null;

        char[] chars = line.toCharArray();

        for(int i=0; i< chars.length; i++){
            String numberString = parseNumber(chars, i, enableWrittenNumbers);
            if(Objects.nonNull(numberString)){
                if (Objects.isNull(first)) first = this.writtenNumbers.get(numberString);
                else last = this.writtenNumbers.get(numberString);
            }
        }

        if(Objects.isNull(first)){
            return 0;
        }
        else if(Objects.isNull(last)){
            last = first;
        }

        return (10*first+last);
    }

}
