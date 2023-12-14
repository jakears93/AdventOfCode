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
        List<List<Rock>> platform = parseToPlatform(input);
        rollNorth(platform);
        return calculateNorthLoad(platform);
    }

    public long sumOfNorthLoadWithFullCycles(List<String> input, int cycleCount){
        List<List<Rock>> platform = parseToPlatform(input);
        for(int i=0; i<cycleCount; i++){
            cyclePlatform(platform);
        }
        return calculateNorthLoad(platform);
    }

    private void cyclePlatform(List<List<Rock>> platform){
        rollNorth(platform);
        rollWest(platform);
        rollSouth(platform);
        rollEast(platform);
    }

    private void rollNorth(List<List<Rock>> platform){
        int columnSize = platform.get(0).size();
        for(int c=0; c<columnSize; c++){
            Rock current;
            int roundRockCount = 0;
            int lastSquareIndex = platform.size();
            for(int r=platform.size()-1; r>=0; r--){
                current = platform.get(r).get(c);
                if(Rock.ROUND.equals(current)){
                    roundRockCount++;
                }
                if(Rock.SQUARE.equals(current)) {
                    for(int s = r+1; s<lastSquareIndex; s++, roundRockCount--){
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        platform.get(s).set(c, rock);
                    }
                    lastSquareIndex = r;
                    roundRockCount = 0;
                }
                else if(r == 0) {
                    for(int s = r; s<lastSquareIndex; s++, roundRockCount--){
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        platform.get(s).set(c, rock);
                    }
                }
            }
        }
    }

    private void rollSouth(List<List<Rock>> platform){
        int columnSize = platform.get(0).size();
        for(int c=0; c<columnSize; c++){
            Rock current;
            int roundRockCount = 0;
            int lastSquareIndex = -1;
            for(int r=0; r<platform.size(); r++){
                current = platform.get(r).get(c);
                if(Rock.ROUND.equals(current)){
                    roundRockCount++;
                }
                if(Rock.SQUARE.equals(current)) {
                    for(int s = r-1; s>lastSquareIndex; s--, roundRockCount--){
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        platform.get(s).set(c, rock);
                    }
                    lastSquareIndex = r;
                    roundRockCount = 0;
                }
                else if(r == columnSize-1) {
                    for(int s = r; s>lastSquareIndex; s--, roundRockCount--){
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        platform.get(s).set(c, rock);
                    }
                }
            }
        }
    }

    private void rollEast(List<List<Rock>> platform){
        for (List<Rock> row : platform) {
            Rock current;
            int roundRockCount = 0;
            int lastSquareIndex = -1;

            for (int c = 0; c < row.size(); c++) {
                current = row.get(c);
                if (Rock.ROUND.equals(current)) {
                    roundRockCount++;
                }
                if (Rock.SQUARE.equals(current)) {
                    for (int s = c - 1; s > lastSquareIndex; s--, roundRockCount--) {
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        row.set(s, rock);
                    }
                    lastSquareIndex = c;
                    roundRockCount = 0;
                } else if (c == row.size() - 1) {
                    for (int s = c; s > lastSquareIndex; s--, roundRockCount--) {
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        row.set(s, rock);
                    }
                }
            }
        }
    }

    private void rollWest(List<List<Rock>> platform){
        for (List<Rock> row : platform) {
            Rock current;
            int roundRockCount = 0;
            int lastSquareIndex = row.size();

            for (int c = row.size()-1; c>=0; c--) {
                current = row.get(c);
                if (Rock.ROUND.equals(current)) {
                    roundRockCount++;
                }
                if (Rock.SQUARE.equals(current)) {
                    for (int s = c+1; s < lastSquareIndex; s++, roundRockCount--) {
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        row.set(s, rock);
                    }
                    lastSquareIndex = c;
                    roundRockCount = 0;
                } else if (c == 0) {
                    for (int s = c; s < lastSquareIndex; s++, roundRockCount--) {
                        Rock rock = roundRockCount > 0 ? Rock.ROUND : Rock.EMPTY;
                        row.set(s, rock);
                    }
                }
            }
        }
    }

    private long calculateNorthLoad(List<List<Rock>> platform){
        long load = 0;
        for(int r=0; r<platform.size(); r++){
            List<Rock> row = platform.get(r);
            long rowMultiplier = platform.size() - r;
            load += row.stream().filter(Rock.ROUND::equals).count() * rowMultiplier;
        }
        return load;
    }

    private long weighColumn(List<Rock> upsideDownColumn){
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

    private List<List<Rock>> parseToPlatform(List<String> input){
        List<List<Rock>> platform = new ArrayList<>(input.size());
        for(String line : input){
            List<Rock> row = new ArrayList<>();
            for(char c : line.toCharArray()){
                row.add(Rock.fromValue(c));
            }
            platform.add(row);
        }
        return platform;
    }

    private void printPlatform(List<List<Rock>> platform){
        int columnSize = platform.get(0).size();
        for(int r=0; r< platform.size(); r++){
            for(int c=0; c<columnSize; c++){
//                if(platform.get(r).get(c).equals(Rock.SQUARE)){
//                    System.out.print(platform.get(r).get(c).value);
//                }
//                else{
//                    System.out.print(".");
//                }
                System.out.print(platform.get(r).get(c).value);

            }
            System.out.println();

        }
        System.out.println();
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