package ca.arctechlabs.aoc.y2023.d1;

import ca.arctechlabs.aoc.utilities.FileLoader;

import java.util.*;

public class Day1B {
    //https://adventofcode.com/2023/day/1#part2

    private static Map<String, Integer> writtenNumbers;

    public static void setup(){
        writtenNumbers = new HashMap<>();
        writtenNumbers.put("one", 1);
        writtenNumbers.put("1", 1);
        writtenNumbers.put("two", 2);
        writtenNumbers.put("2", 2);
        writtenNumbers.put("three", 3);
        writtenNumbers.put("3", 3);
        writtenNumbers.put("four", 4);
        writtenNumbers.put("4", 4);
        writtenNumbers.put("five", 5);
        writtenNumbers.put("5", 5);
        writtenNumbers.put("six", 6);
        writtenNumbers.put("6", 6);
        writtenNumbers.put("seven", 7);
        writtenNumbers.put("7", 7);
        writtenNumbers.put("eight", 8);
        writtenNumbers.put("8", 8);
        writtenNumbers.put("nine", 9);
        writtenNumbers.put("9", 9);
        writtenNumbers.put("zero", 0);
        writtenNumbers.put("0", 0);
    }

    public static void main(String[] args){
        setup();
        System.out.println("Test Results: " + processFile("src/main/resources/2023/Day1/B/test.txt", true));
        System.out.println("Puzzle Results: " + processFile("src/main/resources/2023/Day1/B/input.txt", true));
    }

    public static Integer processFile(String fileName, boolean enableWritten){
        List<String> lines = FileLoader.loadLines(fileName);

        return lines
                .stream()
                .map(line -> extractSum(line, enableWritten))
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static String extractWrittenNumber(char[] chars, int index, boolean enableWritten){
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

        if(enableWritten){
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

    public static Integer extractSum(String line, boolean enableWritten){
        Integer first = null;
        Integer last = null;

        char[] chars = line.toCharArray();

        for(int i=0; i< chars.length; i++){
            String writtenNum = extractWrittenNumber(chars, i, enableWritten);
            if(Objects.nonNull(writtenNum)){
                if (Objects.isNull(first)) first = writtenNumbers.get(writtenNum);
                else last = writtenNumbers.get(writtenNum);
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
