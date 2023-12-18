package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Coordinates;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Day18 {
    public int countTrenchArea(List<String> input){
        List<Instruction> instructions = parseInstructions(input);
        int maxX = getMaxX(instructions);
        int maxY = getMaxY(instructions);
        List<List<Terrain>> terrain = generateEmptyTerrainMap(maxX+2, maxY+2);
        setPerimeter(terrain, instructions);
        Turn insideDirection = getInsideDirection(instructions);
        setInsides(terrain, instructions, insideDirection);
        return countTrenches(terrain);
    }

    private void setInsides(List<List<Terrain>> terrain, List<Instruction> instructions, Turn insideDirection) {
        Coordinates current = new Coordinates();
        current.setY(terrain.size()/2);
        current.setX(terrain.get(0).size()/2);

        Queue<Coordinates> insideQueue = new ArrayDeque<>();
        for(Instruction instruction : instructions){
            Coordinates delta = getDelta(instruction.getDirection());
            for(int i=1; i<=instruction.getSteps(); i++){
                current = new Coordinates((current.getX() + (delta.getX())), (current.getY()+(delta.getY())));
                Coordinates insideLocation = getInsideLocation(current, insideDirection, instruction.getDirection());
                fill(insideLocation, terrain, insideQueue);
            }
        }
        while(insideQueue.peek() != null){
            Coordinates coordinates = insideQueue.poll();
            fill(coordinates, terrain, insideQueue);
        }
    }

    private void fill(Coordinates insideLocation, List<List<Terrain>> terrain, Queue<Coordinates> queue) {
        Terrain inside = terrain.get(insideLocation.getY()).get(insideLocation.getX());

        if(inside.equals(Terrain.TRENCH_PERIMETER) || inside.equals(Terrain.TRENCH)){
            return;
        }
        terrain.get(insideLocation.getY()).set(insideLocation.getX(), Terrain.TRENCH);

        List<Coordinates> adjacent = getAdjacent(insideLocation, terrain);
        queue.addAll(adjacent);
    }

    private List<Coordinates> getAdjacent(Coordinates origin, List<List<Terrain>> terrain){
        int maxX = terrain.get(0).size();
        int maxY = terrain.size();
        List<Coordinates> adjacent = new ArrayList<>();
        for(int x=-1; x<=1; x++){
            if((origin.getX()+x)<0 || (origin.getX()+x) >= maxX) continue;
            for(int y=-1; y<=1; y++){
                if((origin.getY()+y)<0 || (origin.getY()+y) >= maxY || (x==0 && y==0)) continue;
                Coordinates coordinates = new Coordinates(origin.getX()+x, origin.getY()+y);
                Terrain inside = terrain.get(coordinates.getY()).get(coordinates.getX());
                if(inside.equals(Terrain.GROUND)){
                    adjacent.add(coordinates);
                }
            }
        }
        return adjacent;
    }

    private Coordinates getInsideLocation(Coordinates current, Turn insideDirection, Direction direction) {
        int deltaX = 0;
        int deltaY = 0;
        if(direction.equals(Direction.U) && insideDirection.equals(Turn.LEFT)) deltaX=-1;
        else if(direction.equals(Direction.U) && insideDirection.equals(Turn.RIGHT)) deltaX=1;

        else if(direction.equals(Direction.D) && insideDirection.equals(Turn.LEFT)) deltaX=1;
        else if(direction.equals(Direction.D) && insideDirection.equals(Turn.RIGHT)) deltaX=-1;

        else if(direction.equals(Direction.R) && insideDirection.equals(Turn.LEFT)) deltaY=-1;
        else if(direction.equals(Direction.R) && insideDirection.equals(Turn.RIGHT)) deltaY=1;

        else if(direction.equals(Direction.L) && insideDirection.equals(Turn.LEFT)) deltaY=1;
        else if(direction.equals(Direction.L) && insideDirection.equals(Turn.RIGHT)) deltaY=-1;

        return new Coordinates(current.getX()+deltaX, current.getY()+deltaY);
    }

    private Turn getInsideDirection(List<Instruction> instructions) {
        int left = 0;
        int right = 0;

        for(int i=0; i<instructions.size()-1; i++){
            Direction current = instructions.get(i).getDirection();
            Direction next = instructions.get(i+1).getDirection();
            if(current.equals(Direction.U) && next.equals(Direction.R)) right++;
            else if(current.equals(Direction.U) && next.equals(Direction.L)) left++;

            else if(current.equals(Direction.D) && next.equals(Direction.R)) left++;
            else if(current.equals(Direction.D) && next.equals(Direction.L)) right++;

            else if(current.equals(Direction.R) && next.equals(Direction.U)) left++;
            else if(current.equals(Direction.R) && next.equals(Direction.D)) right++;

            else if(current.equals(Direction.L) && next.equals(Direction.U)) right++;
            else if(current.equals(Direction.L) && next.equals(Direction.D)) left++;
        }

        if(right>left) return Turn.RIGHT;
        else return Turn.LEFT;
    }

    private void printTerrain(List<List<Terrain>> terrain) {
        StringBuilder sb = new StringBuilder();
        for(List<Terrain> row : terrain){
            StringBuilder rb = new StringBuilder();
            for(Terrain t : row){
                if(t.equals(Terrain.TRENCH)) rb.append("#");
                else if(t.equals(Terrain.TRENCH_PERIMETER)) rb.append("P");
                else rb.append(".");
            }
            if(rb.toString().contains("#") || rb.toString().contains("P")){
                sb.append(rb);
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }

    private void setPerimeter(List<List<Terrain>> terrain, List<Instruction> instructions) {
        Coordinates origin = new Coordinates();
        origin.setY(terrain.size()/2);
        origin.setX(terrain.get(0).size()/2);
        setLocation(origin, terrain);

        for(Instruction instruction : instructions){
            origin = fillLine( origin, instruction, terrain);
        }
    }

    private void setLocation(Coordinates coordinates, List<List<Terrain>> terrain){
        terrain.get(coordinates.getY()).set(coordinates.getX(), Terrain.TRENCH_PERIMETER);
    }

    private Coordinates getDelta(Direction direction){
        Coordinates delta;
        switch (direction){
            case U -> delta = new Coordinates(0, -1);
            case D -> delta = new Coordinates(0, 1);
            case L -> delta = new Coordinates(-1, 0);
            case R -> delta = new Coordinates(1, 0);
            default -> delta = new Coordinates(0,0);
        }
        return delta;
    }
    private Coordinates fillLine(Coordinates origin, Instruction instruction, List<List<Terrain>> terrain){
        Coordinates nextCoords = new Coordinates(origin.getX(), origin.getY());
        Coordinates delta = getDelta(instruction.getDirection());
        for(int i=1; i<=instruction.getSteps(); i++){
            nextCoords = new Coordinates(origin.getX() + (i*delta.getX()), origin.getY()+(i*delta.getY()));
            setLocation(nextCoords, terrain);
        }
        return nextCoords;
    }

    private int countTrenches(List<List<Terrain>> terrain) {
        int sum = 0;
        for(int y=0; y<terrain.size(); y++){
            List<Terrain> row = terrain.get(y);
            for(int x=0; x<row.size(); x++){
                if(row.get(x).equals(Terrain.TRENCH) || row.get(x).equals(Terrain.TRENCH_PERIMETER)) sum++;
            }
        }
        return sum;
    }

    private List<List<Terrain>> generateEmptyTerrainMap(int maxX, int maxY) {
        List<List<Terrain>> map = new ArrayList<>(maxY);
        for(int y=0; y<maxY; y++){
            List<Terrain> row = new ArrayList<>(maxX);
            for(int x=0; x<maxX; x++){
                row.add(Terrain.GROUND);
            }
            map.add(row);
        }
        return map;
    }

    public int getMaxX(List<Instruction> instructions){
        return instructions.stream()
                .filter(instruction -> instruction.getDirection().equals(Direction.R) || instruction.getDirection().equals(Direction.L))
                .mapToInt(Instruction::getSteps)
                .sum();
    }

    public int getMaxY(List<Instruction> instructions){
        return instructions.stream()
                .filter(instruction -> instruction.getDirection().equals(Direction.U) || instruction.getDirection().equals(Direction.D))
                .mapToInt(Instruction::getSteps)
                .sum();
    }

    private List<Instruction> parseInstructions(List<String> input){
        List<Instruction> instructions = new ArrayList<>();
        for(String row: input){
            Instruction instruction = new Instruction();
            String[] components = row.split(" ");
            instruction.setDirection(Direction.valueOf(components[0]));
            instruction.setSteps(Integer.parseInt(components[1]));
            instruction.setColour(components[2].substring(1, components[2].length()-1));
            instructions.add(instruction);
        }
        return instructions;
    }

    enum Direction{
        U,
        D,
        L,
        R;
    }

    enum Terrain{
        TRENCH,
        TRENCH_PERIMETER,
        GROUND;
    }

    enum Turn{
        RIGHT,
        LEFT;
    }

    class Instruction{
        private Direction direction;
        private int steps;
        private String colour;

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "direction=" + direction +
                    ", steps=" + steps +
                    ", colour='" + colour + '\'' +
                    "}\n";
        }
    }
}
