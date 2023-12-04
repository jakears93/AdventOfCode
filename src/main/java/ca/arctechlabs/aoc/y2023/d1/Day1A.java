package ca.arctechlabs.aoc.y2023.d1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day1A {
    //https://adventofcode.com/2023/day/1

    public static void main(String[] args){
        System.out.println("Test Results: " + processFile("src/main/resources/2023/Day1/A/test.txt"));
        System.out.println("Puzzle Results: " + processFile("src/main/resources/2023/Day1/A/input.txt"));
    }

    public static Integer processFile(String fileName){
        File input = new File(fileName);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            while(reader.ready()){
                lines.add(reader.readLine());
            }
        } catch (IOException e) { e.printStackTrace(); }

        return lines
                .stream()
                .map(Day1A::extractSum)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static Integer extractSum(String line){
        Integer first = null;
        Integer last = null;

        char[] chars = line.toCharArray();

        for(int i=0; i< chars.length; i++){
            String character = String.valueOf(chars[i]);
            if(character.matches("[1-9]")){
                if(Objects.isNull(first)) first = Integer.valueOf(character);
                else last = Integer.valueOf(character);
            }
        }

        if(Objects.isNull(first)){
            return 0;
        }
        else if(Objects.isNull(last)){
            last = first;
        }

        return 10*first+last;
    }
}
