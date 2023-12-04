package ca.arctechlabs.aoc.y2023.d3;

import java.io.*;
import java.util.*;

public class Day3A {
    //https://adventofcode.com/2023/day/3


    public static void main(String[] args){
        System.out.println("Test Results: " + processFile("src/main/resources/2023/Day3/A/test.txt"));
        System.out.println("Puzzle Results: " + processFile("src/main/resources/2023/Day3/A/input.txt"));
    }

    public static Integer processFile(String fileName){
        File input = new File(fileName);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            while(reader.ready()){
                lines.add(reader.readLine());
            }
        } catch (IOException e) { e.printStackTrace(); }

        List<char[]> map = new ArrayList<>();

        for(String line: lines){
            map.add(line.toCharArray());
        }

        return parsePartNumbers(map).stream()
                .mapToInt(value -> value)
                .sum();
    }

    private static List<String> populateAdjacent(List<char[]> map, int mapIndex, int lineIndex){
        List<String> adjacent = new ArrayList<>();
        //j+1, j-1, i+1, i-1, i+1/j+1, i+1/j-1, i-1/j+1, i-1/j-1,
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                try{
                    char[] line = map.get(mapIndex + i);
                    String value = String.valueOf(line[lineIndex+j]);
                    adjacent.add(value);
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        return adjacent;
    }
    private static Collection<Integer> parsePartNumbers(List<char[]> map){
        List<Integer> partNumbers = new ArrayList<>();
        for(int i=0; i<map.size(); i++){
            char[] line = map.get(i);
            for(int j=0; j<line.length; j++){
                String entry = String.valueOf(line[j]);
                try{
                    int num = Integer.parseInt(entry);
                    List<String> adjacent = populateAdjacent(map, i, j);
                    for(String value : adjacent){
                        if(value.matches("[~!@#$%^&*+/=-]")){
                            entry = peekPartNumber(line, j);
                            partNumbers.add(Integer.parseInt(entry));
                            j += entry.length() -1;
                            break;
                        }
                    }
                } catch (NumberFormatException ignored){}
            }
        }
        System.out.println("partNumbers.size() = " + partNumbers.size());
        System.out.println(partNumbers);
        return partNumbers;
    }

    private static String peekPartNumber(char[] line, int startIndex){
        String entry = String.valueOf(line[startIndex]);
        //prepend
        for(int i=startIndex-1; i>=0; i--){
            String character = String.valueOf(line[i]);
            if(character.matches("[0-9]")){
                entry = character + entry;
            }
            else{
                break;
            }
        }
        //append
        for(int i=startIndex+1; i<line.length; i++){
            String character = String.valueOf(line[i]);
            if(character.matches("[0-9]")){
                entry = entry + character;
            }
            else{
                break;
            }
        }
        return entry;
    }

}
