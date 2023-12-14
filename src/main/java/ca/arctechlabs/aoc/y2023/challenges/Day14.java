package ca.arctechlabs.aoc.y2023.challenges;

import java.util.*;
import java.util.stream.Collectors;

//https://adventofcode.com/2023/day/14
public class Day14 {

    public enum Rock{
        ROUND('O'),
        SQUARE('#'),
        EMPTY('.');

        private final char value;

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

    private int findOffset(List<List<Rock>> platform){
        int offset = -1;
        int index = 0;
        List<List<Rock>> cycledPlatform = platform.stream().map(ArrayList::new).collect(Collectors.toList());
        Set<List<List<Rock>>> cycleSet = new HashSet<>();
        while(offset == -1){
            cycledPlatform = newPlatformFromCycle(cycledPlatform);
            if(cycleSet.contains(cycledPlatform)){
                offset = index;
            }
            else{
                cycleSet.add(cycledPlatform);
            }
            index++;
        }
        return offset;
    }

    private int findCycleLength(List<List<Rock>> platform, int offset){
        int cycleLength = -1;
        int index = 0;
        List<List<Rock>> cycledPlatform = platform.stream().map(ArrayList::new).collect(Collectors.toList());
        Set<List<List<Rock>>> cycleSet = new HashSet<>();
        while(cycleLength == -1){
            cycledPlatform = newPlatformFromCycle(cycledPlatform);
            if(cycleSet.contains(cycledPlatform)){
                cycleLength = index-offset-1;
            }
            else if(index > offset){
                cycleSet.add(cycledPlatform);
            }
            index++;
        }
        return cycleLength;
    }

    public long sumOfNorthLoadWithFullCycles(List<String> input, int cycleCount){
        List<List<Rock>> platform = parseToPlatform(input);

        int offset = findOffset(platform);
        int cycleLength = findCycleLength(platform, offset);
        offset = offset - cycleLength;

        int lowestCycle = ((cycleCount - offset) % cycleLength) + offset;

        for(int i=0; i<lowestCycle; i++){
            cyclePlatform(platform);
        }
        return calculateNorthLoad(platform);
    }

    private List<List<Rock>> newPlatformFromCycle(List<List<Rock>> platform){
        List<List<Rock>> cycledPlatform = platform.stream().map(ArrayList::new).collect(Collectors.toList());
        rollNorth(cycledPlatform);
        rollWest(cycledPlatform);
        rollSouth(cycledPlatform);
        rollEast(cycledPlatform);
        return cycledPlatform;
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
                else if(r == 0 && roundRockCount > 0) {
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
                else if(r == columnSize-1 && roundRockCount > 0) {
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
                } else if (c == row.size() - 1 && roundRockCount > 0) {
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
                } else if (c == 0 && roundRockCount > 0) {
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
}