package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.Utils;
import ca.arctechlabs.aoc.common.models.Coordinates;
import ca.arctechlabs.aoc.common.models.Direction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//https://adventofcode.com/2023/day/10
public class Day10 {

    public long perimeterOfLoop(List<String> input){
        PipeMaze pipeMaze = generateMaze(input);
        List<Coordinates> path = createPath(pipeMaze);
        return path.size();
    }

    public BigInteger areaInsideLoop(List<String> input){
        PipeMaze pipeMaze = generateMaze(input);
        List<Coordinates> path = createPath(pipeMaze);
        List<Coordinates> vertices = calculateVertices(path, pipeMaze);
        BigInteger area = Utils.calculateAreaFromVertices(vertices);
        return area.add(BigInteger.ONE).subtract(BigInteger.valueOf(path.size()).divide(BigInteger.TWO));
    }

    private List<Coordinates> calculateVertices(List<Coordinates> path, PipeMaze pipeMaze) {
        List<Coordinates> verticies = new ArrayList<>();
        //check if start is vertex
        boolean startIsVertex = true;
        Coordinates c1 = path.get(1);
        Coordinates c2 = path.get(path.size()-1);
        if(c1.getX() - c2.getX() == 0 || c1.getY() - c2.getY() == 0){
            startIsVertex = false;
        }
        List<Pipe> vertexPipes = List.of(Pipe.SW, Pipe.SE, Pipe.NE, Pipe.NW);
        Pipe pipe;
        for(Coordinates c : path){
            pipe = pipeMaze.getMaze().get(c.getY()).get(c.getX());
            if(vertexPipes.contains(pipe)) verticies.add(c);
        }

        if(startIsVertex){
            verticies.add(path.get(0));
        }
        return verticies;
    }
    private List<Coordinates> createPath(PipeMaze pipeMaze){
        List<Coordinates> coordinates = new ArrayList<>();
        int x;
        int y;
        Direction heading;

        for(Direction direction: Direction.values()){
            x = pipeMaze.getStartX();
            y = pipeMaze.getStartY();
            coordinates.add(new Coordinates(x, y));
            heading = direction;
            Direction nextHeading;
            Pipe nextPipe = getNextPipe(x, y, heading, pipeMaze);

            while(true){
                //End Path is nextPipe is the origin
                if(Pipe.START.equals(nextPipe)){
                    break;
                }
                //Reset Path and start at next direction if invalid pipe or null
                if(Pipe.INVALID.equals(nextPipe) || Objects.isNull(nextPipe)){
                    coordinates.clear();
                    break;
                }
                x += heading.getDeltaX();
                y += heading.getDeltaY();
                Direction complement = getComplement(heading);

                if(nextPipe.getEntry().equals(complement)){
                    nextHeading = nextPipe.getExit();
                }
                else{
                    nextHeading = nextPipe.getEntry();
                }

                coordinates.add(new Coordinates(x, y));
                heading = nextHeading;
                nextPipe = getNextPipe(x, y, heading, pipeMaze);
            }
            if(Pipe.START.equals(nextPipe)){
                break;
            }
        }
        return coordinates;
    }

    private Direction getComplement(Direction heading){
        if(Direction.NORTH.equals(heading)){
            return Direction.SOUTH;
        }
        if(Direction.SOUTH.equals(heading)){
            return Direction.NORTH;
        }
        if(Direction.WEST.equals(heading)){
            return Direction.EAST;
        }
        else{
            return Direction.WEST;
        }
    }
    private Pipe getNextPipe(int x, int y, Direction heading, PipeMaze pipeMaze){
        Pipe nextPipe = null;
        List<List<Pipe>> maze = pipeMaze.getMaze();
        int maxHeight = maze.size();
        int maxWidth = maze.get(0).size();
        //Go North
        if(y-1 >= 0  && Direction.NORTH.equals(heading)){
            Pipe northPipe = maze.get(y-1).get(x);
            if(Pipe.NS.equals(northPipe) || Pipe.SW.equals(northPipe) || Pipe.SE.equals(northPipe) || Pipe.START.equals(northPipe)){
                nextPipe = northPipe;
            }
        }
        //Go South
        else if(maxHeight > y+1 && Direction.SOUTH.equals(heading)){
            Pipe southPipe = maze.get(y+1).get(x);
            if(Pipe.NS.equals(southPipe) || Pipe.NW.equals(southPipe) || Pipe.NE.equals(southPipe) || Pipe.START.equals(southPipe)){
                nextPipe = southPipe;
            }
        }
        //Go East
        else if(maxWidth > x+1 && Direction.EAST.equals(heading)){
            Pipe eastPipe = maze.get(y).get(x+1);
            if(Pipe.EW.equals(eastPipe) || Pipe.NW.equals(eastPipe) || Pipe.SW.equals(eastPipe) || Pipe.START.equals(eastPipe)){
                nextPipe = eastPipe;
            }
        }
        //Go West
        else if(x-1 >= 0 && Direction.WEST.equals(heading)){
            Pipe westPipe = maze.get(y).get(x-1);
            if(Pipe.EW.equals(westPipe) || Pipe.NE.equals(westPipe) || Pipe.SE.equals(westPipe) || Pipe.START.equals(westPipe)){
                nextPipe = westPipe;
            }
        }

        return nextPipe;
    }
    private PipeMaze generateMaze(List<String> input){
        PipeMaze pipeMaze = new PipeMaze();
        List<List<Pipe>> maze = pipeMaze.getMaze();
        
        for(int y=0; y<input.size(); y++){
            String line = input.get(y);
            List<Pipe> row = new ArrayList<>();
            for(int x=0; x< line.length(); x++){
                row.add(Pipe.fromValue(line.charAt(x)));
                if(Pipe.START.equals(row.get(x))){
                    pipeMaze.setStartCoordinates(x, y);
                }
            }
            maze.add(row);
        }
        return pipeMaze;
    }

    private enum Pipe {
        NS('|', Direction.NORTH, Direction.SOUTH),
        EW('-', Direction.EAST, Direction.WEST),
        NE('L', Direction.NORTH, Direction.EAST),
        NW('J', Direction.NORTH, Direction.WEST),
        SW('7', Direction.SOUTH, Direction.WEST),
        SE('F', Direction.SOUTH, Direction.EAST),
        INVALID('.', Direction.INVALID, Direction.INVALID),
        START('S', Direction.INVALID, Direction.INVALID);


        private final char value;
        private final Direction entry;
        private final Direction exit;

        Pipe(char value, Direction entry, Direction exit) {
            this.value = value;
            this.entry = entry;
            this.exit = exit;
        }

        public static Pipe fromValue(char value){
            for (Pipe b : values()) {
                if (b.value == value) {
                    return b;
                }
            }
            return INVALID;
        }
        public Direction getEntry() {
            return entry;
        }

        public Direction getExit() {
            return exit;
        }
    }
    private static class PipeMaze {
        private final List<List<Pipe>> maze;
        private Integer startX;
        private Integer startY;

        public PipeMaze() {
            this.maze = new ArrayList<>();
        }

        public Integer getStartX() {
            return startX;
        }

        public Integer getStartY() {
            return startY;
        }

        public void setStartCoordinates(int startX, int startY) {
            this.startX = startX;
            this.startY = startY;
        }

        public List<List<Pipe>> getMaze() {
            return maze;
        }

    }
}
