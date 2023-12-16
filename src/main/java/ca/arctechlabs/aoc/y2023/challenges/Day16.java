package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class Day16 {

    private List<String> input;
    private List<List<Tile>> tileMap;

    public void resetTileMap() {
        this.tileMap = parseTileMap(this.input);
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    public long getMaxNumberOfIlluminatedTiles(){
        resetTileMap();
        int maxX = this.tileMap.get(0).size();
        int maxY = this.tileMap.size();
        long maxSum = 0;
        for(int x=0; x<maxX; x++){
            resetTileMap();
            long sum = getNumberOfIlluminatedTiles(new Coordinates(x, 0), Direction.SOUTH);
            if(sum > maxSum) maxSum = sum;
            resetTileMap();
            sum = getNumberOfIlluminatedTiles(new Coordinates(x, maxY-1), Direction.NORTH);
            if(sum > maxSum) maxSum = sum;
        }
        for(int y=0; y<maxY; y++){
            resetTileMap();
            long sum = getNumberOfIlluminatedTiles(new Coordinates(0, y), Direction.EAST);
            if(sum > maxSum) maxSum = sum;
            resetTileMap();
            sum = getNumberOfIlluminatedTiles(new Coordinates(maxX-1, y), Direction.WEST);
            if(sum > maxSum) maxSum = sum;
        }
        return maxSum;
    }

    public long getNumberOfIlluminatedTiles(){
        return getNumberOfIlluminatedTiles(new Coordinates(0,0), Direction.EAST);
    }

    private long getNumberOfIlluminatedTiles(Coordinates origin, Direction direction){
        resetTileMap();

        Tile firstTile = getNextTile(origin);
        if(firstTile == null) return 0L;
        firstTile.setIlluminated(true);
        List<Instruction> initialInstructions = getNextInstructions(this.tileMap.get((int)origin.getY()).get((int)origin.getX()), direction);
        for(Instruction instruction : initialInstructions){
            followBeam(new Coordinates(origin.getX(), origin.getY()), instruction);
        }

        long sum = 0;
        for(List<Tile> row : this.tileMap){
            sum += row.stream().filter(Tile::isIlluminated).count();
        }
        return sum;
    }

    private void printTileMap(boolean showIlluminated){
        StringBuilder sb = new StringBuilder();
        for(List<Tile> row : this.tileMap){
            for(Tile tile : row){
                if(showIlluminated){
                    if(tile.isIlluminated()){
                        sb.append("#");
                    }
                    else{
                        sb.append(".");
                    }
                }
                else{
                    sb.append(tile.getType().value);
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    private void followBeam(Coordinates origin, Instruction instruction){
        Coordinates currentCoords = new Coordinates(origin.getX() + instruction.getCoords().getX(), origin.getY() + instruction.getCoords().getY());
        Tile currentTile = getNextTile(currentCoords);
        if(currentTile == null) return;
        currentTile.setIlluminated(true);
        List<Instruction> instructions = getNextInstructions(currentTile, instruction.direction);
        for(Instruction nextInstruction : instructions){
            followBeam(currentCoords, nextInstruction);
        }
    }

    private Tile getNextTile(Coordinates currentCoords){
        int maxY = this.tileMap.size()-1;
        if(currentCoords.getY() <0 || currentCoords.getY() >maxY) return null;
        int maxX = this.tileMap.get((int)currentCoords.getY()).size()-1;
        if(currentCoords.getX() <0 || currentCoords.getX() >maxX) return null;

        return this.tileMap.get((int)currentCoords.getY()).get((int)currentCoords.getX());
    }

    public List<Instruction> getNextInstructions(Tile tile, Direction direction){
        List<Instruction> nextInstructions = new ArrayList<>();
        //If Tile has already been visited from this direction, return empty list
        if(!tile.addVisitedDirection(direction)) return nextInstructions;

        switch (direction){
            case NORTH -> {
                switch (tile.getType()){
                    case EMPTY, SPLITTER_V -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,-1), direction));
                    }
                    case MIRROR_RIGHT -> {
                        nextInstructions.add(new Instruction(new Coordinates(1,0), Direction.EAST));
                    }
                    case MIRROR_LEFT -> {
                        nextInstructions.add(new Instruction(new Coordinates(-1,0), Direction.WEST));
                    }
                    case SPLITTER_H -> {
                        nextInstructions.add(new Instruction(new Coordinates(-1,0), Direction.WEST));
                        nextInstructions.add(new Instruction(new Coordinates(1,0), Direction.EAST));
                    }
                }
            }
            case SOUTH -> {
                switch (tile.getType()){
                    case EMPTY, SPLITTER_V -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,1), direction));
                    }
                    case MIRROR_RIGHT -> {
                        nextInstructions.add(new Instruction(new Coordinates(-1,0), Direction.WEST));
                    }
                    case MIRROR_LEFT -> {
                        nextInstructions.add(new Instruction(new Coordinates(1,0), Direction.EAST));
                    }
                    case SPLITTER_H -> {
                        nextInstructions.add(new Instruction(new Coordinates(-1,0), Direction.WEST));
                        nextInstructions.add(new Instruction(new Coordinates(1,0), Direction.EAST));
                    }
                }
            }
            case EAST -> {
                switch (tile.getType()){
                    case EMPTY, SPLITTER_H -> {
                        nextInstructions.add(new Instruction(new Coordinates(1,0), direction));
                    }
                    case MIRROR_RIGHT -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,-1), Direction.NORTH));
                    }
                    case MIRROR_LEFT -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,1), Direction.SOUTH));
                    }
                    case SPLITTER_V -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,-1), Direction.NORTH));
                        nextInstructions.add(new Instruction(new Coordinates(0,1), Direction.SOUTH));
                    }
                }
            }
            case WEST -> {
                switch (tile.getType()){
                    case EMPTY, SPLITTER_H -> {
                        nextInstructions.add(new Instruction(new Coordinates(-1,0), direction));
                    }
                    case MIRROR_RIGHT -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,1), Direction.SOUTH));
                    }
                    case MIRROR_LEFT -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,-1), Direction.NORTH));
                    }
                    case SPLITTER_V -> {
                        nextInstructions.add(new Instruction(new Coordinates(0,-1), Direction.NORTH));
                        nextInstructions.add(new Instruction(new Coordinates(0,1), Direction.SOUTH));
                    }
                }
            }
        }
        return nextInstructions;
    }
    private List<List<Tile>> parseTileMap(List<String> input){
        List<List<Tile>> tileMap = new ArrayList<>();
        for(String line: input){
            List<Tile> row = new ArrayList<>();
            for(String value : line.split("")){
                row.add(new Tile(TileType.fromValue(value)));
            }
            tileMap.add(row);
        }
        return tileMap;
    }


    public enum Direction{
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    public enum TileType{
        EMPTY("."),
        MIRROR_RIGHT("/"),
        MIRROR_LEFT("\\"),
        SPLITTER_H("-"),
        SPLITTER_V("|");

        private String value;

        TileType(String value) {
            this.value = value;
        }

        public static TileType fromValue(String value){
            for(TileType tileType : TileType.values()){
                if(value.equals(tileType.value)) return tileType;
            }
            throw new IllegalArgumentException("Unable to process TileType: "+value);
        }
    }

    public class Tile{
        private final TileType type;
        private boolean isIlluminated;
        private final List<Direction> visitedDirections;

        public Tile(TileType type) {
            this.type = type;
            this.isIlluminated = false;
            this.visitedDirections = new ArrayList<>();
        }

        public boolean isIlluminated() {
            return isIlluminated;
        }

        public void setIlluminated(boolean illuminated) {
            isIlluminated = illuminated;
        }

        public boolean addVisitedDirection(Direction direction){
            if(this.visitedDirections.contains(direction)) return false;
            this.visitedDirections.add(direction);
            return true;
        }

        public TileType getType() {
            return type;
        }

        @Override
        public String toString(){
            return type.value;
        }

        public String toIlluminatedString(){
            if(this.isIlluminated){
                return "#";
            }
            return type.value;
        }

    }

    public class Instruction{
        private Coordinates coords;
        private Direction direction;

        public Instruction(Coordinates coords, Direction direction) {
            this.coords = coords;
            this.direction = direction;
        }

        public Coordinates getCoords() {
            return coords;
        }

        public Direction getDirection() {
            return direction;
        }
    }

}