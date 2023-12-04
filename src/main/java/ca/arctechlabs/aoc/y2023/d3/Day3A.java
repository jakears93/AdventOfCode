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

        List<List<String>> translatedMap = new ArrayList<>();

        for(String line: lines){
            translatedMap.add(translateLine(line));
        }

        return parsePartNumbers(translatedMap).stream()
                .mapToInt(value -> value)
                .sum();
    }

    private static List<String> populateAdjacent(List<List<String>> map, int mapIndex, int lineIndex){
        List<String> adjacent = new ArrayList<>();

        return adjacent;
    }
    private static Set<Integer> parsePartNumbers(List<List<String>> map){
        Set<Integer> partNumbers = new HashSet<>();

        for(int i=0; i<map.size(); i++){
            List<String> line = map.get(i);
            for(int j=0; j<line.size(); j++){
                String entry = line.get(j);
                try{
                    int num = Integer.parseInt(entry);
                    List<String> adjacent = populateAdjacent(map, i, j);
                    for(String value : adjacent){
                        if(value.matches("[~!@#$%^&*+-]")){
                            partNumbers.add(num);
                            break;
                        }
                    }
                } catch (NumberFormatException ignored){}
            }
        }

        return partNumbers;
    }
    private static List<String> translateLine(String line){
        List<String> lineValues = new ArrayList<>();
        char[] chars = line.toCharArray();
        for(int i=0; i< chars.length; i++){
            String entry = "";
            String character = String.valueOf(chars[i]);
            if(character.matches("[1-9]")){
                entry = peekPartNumber(line.substring(i).toCharArray());
                i += entry.length() -1;
            }
            else if(!character.equals(".")){
                entry = character;
                i += entry.length() -1;
            }
            lineValues.add(entry);
        }

        return lineValues;
    }

    private static String peekPartNumber(char[] partialLine){
        String entry = String.valueOf(partialLine[0]);
        for(int i=1; i< partialLine.length; i++){
            String character = String.valueOf(partialLine[i]);
            if(character.matches("[1-9]")){
                entry = entry + character;
            }
            else{
                break;
            }
        }
        return entry;
    }

}
