package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.models.Coordinates;

import java.util.*;

//https://adventofcode.com/2023/day/11
public class Day11 {
    public long sumOfShortestPaths(List<String> input, long expansionMultiplier){
        MapData mapData = parseMapData(input);
        Map<Integer, Coordinates> galaxyMap = extractGalaxyCoordinates(mapData, expansionMultiplier);
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

    private Map<Integer, Coordinates> extractGalaxyCoordinates(MapData mapData, long expansionMultiplier){
        List<List<Integer>> rawMap = mapData.getMap();

        Map<Integer, Coordinates> galaxyMap = new HashMap<>();
        for(int y=0; y<rawMap.size(); y++){
            List<Integer> row = rawMap.get(y);
            for(int x=0; x<row.size(); x++){
                Integer galaxy = row.get(x);
                if(Objects.nonNull(galaxy)){
                    galaxyMap.put(galaxy, calculateCoordinates(x, y, mapData, expansionMultiplier));
                }
            }
        }
        return galaxyMap;
    }

    private Coordinates calculateCoordinates(int x, int y, MapData mapData, long expansionMultiplier){
        Coordinates coordinates = new Coordinates();
        long numOfExpandedColumns = mapData.getExpandedColumn().stream().filter(index -> index<x).count();
        long numOfExpandedRows = mapData.getExpandedRows().stream().filter(index -> index<y).count();
        coordinates.setX((int) (x+(numOfExpandedColumns*expansionMultiplier)-numOfExpandedColumns));
        coordinates.setY((int) (y+(numOfExpandedRows*expansionMultiplier)-numOfExpandedRows));
        return coordinates;
    }

    private MapData parseMapData(List<String> input){
        List<List<Integer>> rawMap = new ArrayList<>();
        List<Integer> expandedRows = new ArrayList<>();
        int galaxyCount = 0;
        for(int l=0; l<input.size(); l++){
            String line = input.get(l);
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
                expandedRows.add(l);
            }
            rawMap.add(row);
        }


        boolean allNull;
        int rowSize = rawMap.get(0).size();

        List<Integer> expandedColumns = new ArrayList<>();
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
                expandedColumns.add(rowIndex);
            }
        }
        return new MapData(rawMap, expandedColumns, expandedRows);
    }

    private static class MapData{
        private final List<Integer> expandedColumn;
        private final List<Integer> expandedRows;
        private final List<List<Integer>> map;

        public MapData(List<List<Integer>> map, List<Integer> expandedColumn, List<Integer> expandedRows) {
            this.expandedColumn = expandedColumn;
            this.expandedRows = expandedRows;
            this.map = map;
        }

        public List<Integer> getExpandedColumn() {
            return expandedColumn;
        }

        public List<Integer> getExpandedRows() {
            return expandedRows;
        }

        public List<List<Integer>> getMap() {
            return map;
        }
    }
}
