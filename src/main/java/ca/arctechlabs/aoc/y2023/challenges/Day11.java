package ca.arctechlabs.aoc.y2023.challenges;

import java.util.*;

public class Day11 {
    public long sumOfShortestPaths(List<String> input, int expansionMultiplier){
        List<List<Integer>> rawGalaxyMap = applyCosmicExpansion(input, expansionMultiplier);
        Map<Integer, Coordinates> galaxyMap = extractGalaxyCoordinates(rawGalaxyMap);

        long sumOfPaths = 0L;
        for(int i=0; i<galaxyMap.size(); i++){
            Coordinates g1 = galaxyMap.get(i);
            for(int j=i+1; j<galaxyMap.size(); j++){
                Coordinates g2 = galaxyMap.get(j);
                long xDiff = Math.abs(g2.getX() - g1.getX());
                long yDiff = Math.abs(g2.getY() - g1.getY());
                sumOfPaths += (xDiff+yDiff);
            }
        }
        return sumOfPaths;
    }

    private Map<Integer, Coordinates> extractGalaxyCoordinates(List<List<Integer>> rawMap){
        Map<Integer, Coordinates> galaxyMap = new HashMap<>();
        for(int y=0; y<rawMap.size(); y++){
            List<Integer> row = rawMap.get(y);
            for(int x=0; x<row.size(); x++){
                Integer galaxy = row.get(x);
                if(Objects.nonNull(galaxy)){
                    galaxyMap.put(galaxy, new Coordinates(x, y));
                }
            }
        }
        return galaxyMap;
    }

    private List<List<Integer>> applyCosmicExpansion(List<String> input, int multiplier){
        List<List<Integer>> rawMap = new ArrayList<>();
        int galaxyCount = 0;
        for(String line : input){
            List<Integer> row = new ArrayList<>();
            int startingGalaxyCount = galaxyCount;
            for(char c : line.toCharArray()){
                if(c == '#'){
                    row.add(galaxyCount++);
                }
                else{
                    row.add(null);
                }
            }
            //Expand extra row if empty
            if(startingGalaxyCount == galaxyCount){
                for(int m=0; m<multiplier-1; m++){
                    rawMap.add(row);
                }
            }
            rawMap.add(row);
        }

        boolean allNull;
        int rowSize = rawMap.get(0).size();
        for(int rowIndex=0; rowIndex<rowSize; rowIndex++){
            allNull = true;
            for(int columnIndex=0; columnIndex<rawMap.size(); columnIndex++){
                List<Integer> row = rawMap.get(columnIndex);
                if(!Objects.isNull(row.get(rowIndex))){
                    allNull = false;
                    break;
                }
            }
            //Expand Column if every same row index in null;
            if(allNull){
                for(int columnIndex=0; columnIndex<rawMap.size(); columnIndex++){
                    List<Integer> row = rawMap.get(columnIndex);
                    for(int m=0; m<multiplier-1; m++){
                        row.add(rowIndex, null);
                    }
                }
                rowIndex += (multiplier-1);
                rowSize += (multiplier-1);
            }
        }
        return rawMap;
    }
    private class Coordinates{
        private final int x;
        private final int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=" + y +
                    "}\n";
        }
    }
}
