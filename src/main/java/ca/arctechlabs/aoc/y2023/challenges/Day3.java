package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Gear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Day3 {
    //https://adventofcode.com/2023/day/3

    public Integer sumOfPartNumbers(List<String> input){
        List<char[]> map = new ArrayList<>();

        for(String line: input){
            map.add(line.toCharArray());
        }
        return parsePartNumbers(map).stream()
                .mapToInt(value -> value)
                .sum();
    }
    public Integer sumOfGearRatios(List<String> input){
        List<char[]> map = new ArrayList<>();

        for(String line: input){
            map.add(line.toCharArray());
        }
        return parseGears(map).stream()
                .mapToInt(Gear::getRatio)
                .sum();
    }

    private List<String> populateAdjacent(List<char[]> map, int mapIndex, int lineIndex){
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

    private Gear checkIfGear(List<char[]> map, int mapIndex, int lineIndex){
        Gear gear = null;
        List<String> adjacentParts = new ArrayList<>();

        for(int i=mapIndex-1; i>=0 && i<map.size() && i<=mapIndex+1; i++){
            char[] line = map.get(i);
            String nextPart = "";
            for(int j=0; j<line.length; j++){
                if(String.valueOf(line[j]).matches("[0-9]")){
                    nextPart = nextPart + line[j];
                }
                else{
                    if(!nextPart.isEmpty()){
                        for(int k=j-1; k>j-nextPart.length()-1; k--){
                            if(k == lineIndex || k == lineIndex-1 || k == lineIndex+1){
                                adjacentParts.add(nextPart);
                                break;
                            }
                        }
                    }
                    nextPart = "";
                }
                if(j == line.length -1 && !nextPart.isEmpty()){
                    for(int k=j; k>j-nextPart.length(); k--){
                        if(k == lineIndex || k == lineIndex-1 || k == lineIndex+1){
                            adjacentParts.add(nextPart);
                            break;
                        }
                    }
                }
            }
        }

        if(adjacentParts.size() == 2){
            gear = new Gear();
            gear.setG1(Integer.parseInt(adjacentParts.get(0)));
            gear.setG2(Integer.parseInt(adjacentParts.get(1)));
        }
        return gear;
    }

    private Collection<Gear> parseGears(List<char[]> map){
        List<Gear> gears = new ArrayList<>();
        for(int i=0; i<map.size(); i++){
            char[] line = map.get(i);
            for(int j=0; j<line.length; j++){
                if(line[j] != '*'){
                    continue;
                }
                Gear gear = checkIfGear(map, i, j);

                if(Objects.nonNull(gear)){
                    gears.add(gear);
                }
            }
        }
        return gears;
    }
    private Collection<Integer> parsePartNumbers(List<char[]> map){
        List<Integer> partNumbers = new ArrayList<>();
        for(int i=0; i<map.size(); i++){
            char[] line = map.get(i);
            for(int j=0; j<line.length; j++){
                try{
                    int num = Integer.parseInt(String.valueOf(line[j]));
                    List<String> adjacent = populateAdjacent(map, i, j);
                    for(String value : adjacent){
                        if(value.matches("[~!@#$%^&*+/=-]")){
                            String entry = peekPartNumber(line, j);
                            partNumbers.add(Integer.parseInt(entry));
                            j = advanceIndex(line, j) -1;
                            break;
                        }
                    }
                } catch (NumberFormatException ignored){}
            }
        }
        return partNumbers;
    }

    private int advanceIndex(char[] line, int start) {
        int newIndex = start;
        while(newIndex<line.length && String.valueOf(line[newIndex]).matches("[0-9]")){
            newIndex++;
        }
        return newIndex;
    }
    private String peekPartNumber(char[] line, int startIndex){
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
