package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.List;

//https://adventofcode.com/2023/day/14
public class Day14 {

    public enum Rock{
        ROUND('O'),
        SQUARE('#'),
        EMPTY('.');

        private char value;

        Rock(char value) {
            this.value = value;
        }

        public static Rock fromValue(char c){
            for(Rock rock : Rock.values()){
                if(c == rock.value){
                    return rock;
                }
            }
            throw new IllegalArgumentException("No rock found for value: "+c);
        }
    }

    public long sumOfNorthLoad(List<String> input){
        List<List<Rock>> upsideDownColumns = parseToUpsideDownColumns(input);
        return upsideDownColumns.stream().mapToLong(this::weighColumn).sum();
    }

    public long weighColumn(List<Rock> upsideDownColumn){
        long weight = 0;
        for(int i=0; i<upsideDownColumn.size();){
            Rock current;
            int roundRockCount = 0;
            int totalRockCount = 0;
            int index = i;
            do{
                current = upsideDownColumn.get(index);
                if(Rock.ROUND.equals(current)) roundRockCount++;
                totalRockCount++;
                index++;
            } while(!Rock.SQUARE.equals(current) && index < upsideDownColumn.size());
            if(Rock.SQUARE.equals(current)) index--;

            for(int j=0; j<roundRockCount; j++){
                int value = index -j;
                weight = weight + value;
            }
            i+=totalRockCount;
        }
        return weight;
    }

    private List<List<Rock>> parseToUpsideDownColumns(List<String> input){
        int numOfColumns = input.get(0).length();
        List<List<Rock>> upsideDownColumns = new ArrayList<>(numOfColumns);
        for(int i=0; i<numOfColumns; i++){
            upsideDownColumns.add(new ArrayList<>(input.size()));
        }

        for(int i=input.size()-1; i>=0; i--){
            for(int j=0; j<numOfColumns; j++){
                Rock rock = Rock.fromValue(input.get(i).charAt(j));
                upsideDownColumns.get(j).add(rock);
            }
        }

        return upsideDownColumns;
    }
}