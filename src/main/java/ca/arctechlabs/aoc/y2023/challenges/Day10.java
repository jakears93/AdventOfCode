package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.Utils;
import ca.arctechlabs.aoc.y2023.models.*;

import java.math.BigInteger;
import java.util.*;

//https://adventofcode.com/2023/day/10
public class Day10 {

    private PipeMaze pipeMaze;
    private PipeDirection initialHeading;

    public long lengthOfLoop(){
        List<Direction> path = createPath();
        return path.size();
    }

    public BigInteger areaInsideLoop(){
        List<Direction> path = createPath();
        List<Coordinates> vertices = createVerticesFromPath(path);
        BigInteger area = Utils.calculateAreaFromVerticies(vertices);
        return area.add(BigInteger.ONE).subtract(BigInteger.valueOf(path.size()).divide(BigInteger.TWO));
    }

    private List<Coordinates> createVerticesFromPath(List<Direction> path) {
        List<Coordinates> verticies = new ArrayList<>();
        Coordinates current = new Coordinates(0,0);
        Coordinates nextCoords;
        Coordinates delta;
        PipeDirection heading = PipeDirection.EAST;
        Direction lastDirection;
        for(int i=1; i<path.size(); i++){
            lastDirection = path.get(i);
            int steps = 1;
            while(lastDirection.equals(Direction.STRAIGHT) && i<path.size()-1){
                lastDirection = path.get(++i);
                steps++;
            }
            if(i==path.size()-1) steps++;
            delta = getDelta(heading);
            heading = nextHeading(heading, lastDirection);
            nextCoords = new Coordinates(current.getX() + (steps*delta.getX()), current.getY() + (steps*delta.getY()));
            verticies.add(nextCoords);
            current = nextCoords;
        }
        return verticies;
    }

    private PipeDirection nextHeading(PipeDirection heading, Direction lastDirection) {
        if(heading.equals(PipeDirection.NORTH) && lastDirection.equals(Direction.LEFT)) return PipeDirection.WEST;
        else if(heading.equals(PipeDirection.NORTH) && lastDirection.equals(Direction.RIGHT)) return PipeDirection.EAST;

        else if(heading.equals(PipeDirection.SOUTH) && lastDirection.equals(Direction.LEFT)) return PipeDirection.EAST;
        else if(heading.equals(PipeDirection.SOUTH) && lastDirection.equals(Direction.RIGHT)) return PipeDirection.WEST;

        else if(heading.equals(PipeDirection.EAST) && lastDirection.equals(Direction.LEFT)) return PipeDirection.NORTH;
        else if(heading.equals(PipeDirection.EAST) && lastDirection.equals(Direction.RIGHT)) return PipeDirection.SOUTH;

        else if(heading.equals(PipeDirection.WEST) && lastDirection.equals(Direction.LEFT)) return PipeDirection.SOUTH;
        else if(heading.equals(PipeDirection.WEST) && lastDirection.equals(Direction.RIGHT)) return PipeDirection.NORTH;
        else return PipeDirection.INVALID;
    }

    private Coordinates getDelta(PipeDirection heading) {
        if(heading.equals(PipeDirection.NORTH)) return new Coordinates(0,-1);
        else if(heading.equals(PipeDirection.SOUTH)) return new Coordinates(0,1);
        else if(heading.equals(PipeDirection.EAST)) return new Coordinates(1,0);
        else if(heading.equals(PipeDirection.WEST)) return new Coordinates(-1,0);
        else return new Coordinates(0,0);
    }

    private List<Direction> createPath(){
        List<Direction> directions = new ArrayList<>();
        int x;
        int y;
        PipeDirection heading;

        for(PipeDirection direction: PipeDirection.values()){
            x = this.pipeMaze.getStartX();
            y = this.pipeMaze.getStartY();
            directions.add(Direction.STRAIGHT);
            heading = direction;
            PipeDirection nextHeading;
            Pipe nextPipe = getNextPipe(x, y, heading);

            while(true){
                //End Path is nextPipe is the origin
                if(Pipe.START.equals(nextPipe)){
                    break;
                }
                //Reset Path and start at next direction if invalid pipe or null
                if(Pipe.INVALID.equals(nextPipe) || Objects.isNull(nextPipe)){
                    directions.clear();
                    break;
                }
                x += heading.getxMovement();
                y += heading.getyMovement();
                PipeDirection complement = getComplement(heading);

                if(nextPipe.getEntry().equals(complement)){
                    nextHeading = nextPipe.getExit();
                }
                else{
                    nextHeading = nextPipe.getEntry();
                }

                directions.add(calculateDirection(heading, nextHeading));
                heading = nextHeading;
                nextPipe = getNextPipe(x, y, heading);
            }
            if(Pipe.START.equals(nextPipe)){
                this.initialHeading = direction;
                break;
            }
        }
        return directions;
    }
    private Direction calculateDirection(PipeDirection heading, PipeDirection nextHeading){
        //if heading north, exit east: right, exit west: left, else straight
        //if heading south, exit east: left, exit west: right, else straight
        //if heading east, exit north: left, exit south: right, else straight
        //if heading west, exit north: right, exit south: left, else straight
        Direction direction = Direction.STRAIGHT;
        if(PipeDirection.NORTH.equals(heading)){
            if(PipeDirection.EAST.equals(nextHeading)){
                direction = Direction.RIGHT;
            }
            else if(PipeDirection.WEST.equals(nextHeading)){
                direction = Direction.LEFT;
            }
        }
        else if(PipeDirection.SOUTH.equals(heading)){
            if(PipeDirection.EAST.equals(nextHeading)){
                direction = Direction.LEFT;
            }
            else if(PipeDirection.WEST.equals(nextHeading)){
                direction = Direction.RIGHT;
            }
        }
        else if(PipeDirection.EAST.equals(heading)){
            if(PipeDirection.NORTH.equals(nextHeading)){
                direction = Direction.LEFT;
            }
            else if(PipeDirection.SOUTH.equals(nextHeading)){
                direction = Direction.RIGHT;
            }
        }
        else if(PipeDirection.WEST.equals(heading)){
            if(PipeDirection.NORTH.equals(nextHeading)){
                direction = Direction.RIGHT;
            }
            else if(PipeDirection.SOUTH.equals(nextHeading)){
                direction = Direction.LEFT;
            }
        }

        return direction;
    }
    private PipeDirection getComplement(PipeDirection heading){
        if(PipeDirection.NORTH.equals(heading)){
            return PipeDirection.SOUTH;
        }
        if(PipeDirection.SOUTH.equals(heading)){
            return PipeDirection.NORTH;
        }
        if(PipeDirection.WEST.equals(heading)){
            return PipeDirection.EAST;
        }
        else{
            return PipeDirection.WEST;
        }
    }
    private Pipe getNextPipe(int x, int y, PipeDirection heading){
        Pipe nextPipe = null;
        Location[][] maze = this.pipeMaze.getMaze();
        //Go North
        if(y-1 >= 0  && PipeDirection.NORTH.equals(heading)){
            Pipe northPipe = maze[y-1][x].getPipe();
            if(Pipe.NS.equals(northPipe) || Pipe.SW.equals(northPipe) || Pipe.SE.equals(northPipe) || Pipe.START.equals(northPipe)){
                nextPipe = northPipe;
            }
        }
        //Go South
        else if(this.pipeMaze.getMazeHeight() > y+1 && PipeDirection.SOUTH.equals(heading)){
            Pipe southPipe = maze[y+1][x].getPipe();
            if(Pipe.NS.equals(southPipe) || Pipe.NW.equals(southPipe) || Pipe.NE.equals(southPipe) || Pipe.START.equals(southPipe)){
                nextPipe = southPipe;
            }
        }
        //Go East
        else if(this.pipeMaze.getMazeWidth() > x+1 && PipeDirection.EAST.equals(heading)){
            Pipe eastPipe = maze[y][x+1].getPipe();
            if(Pipe.EW.equals(eastPipe) || Pipe.NW.equals(eastPipe) || Pipe.SW.equals(eastPipe) || Pipe.START.equals(eastPipe)){
                nextPipe = eastPipe;
            }
        }
        //Go West
        else if(x-1 >= 0 && PipeDirection.WEST.equals(heading)){
            Pipe westPipe = maze[y][x-1].getPipe();
            if(Pipe.EW.equals(westPipe) || Pipe.NE.equals(westPipe) || Pipe.SE.equals(westPipe) || Pipe.START.equals(westPipe)){
                nextPipe = westPipe;
            }
        }

        return nextPipe;
    }
    public void populateMaze(List<String> input){
        PipeMaze pipeMaze = new PipeMaze(input.size(), input.get(0).length());
        
        for(int y=0; y<input.size(); y++){
            Location[] mappedRow = mapRow(input.get(y));
            if(Objects.isNull(pipeMaze.getStartX())){
                for(int x=0; x< mappedRow.length; x++){
                    if(Pipe.START.equals(mappedRow[x].getPipe())){
                        pipeMaze.setStartCoordinates(x, y);
                    }
                }
            }
            pipeMaze.addRowToMaze(mappedRow, y);
        }
        
        this.pipeMaze = pipeMaze;
    }
    private Location[] mapRow(String rawRow){
        char[] row = rawRow.toCharArray();
        Location[] mappedRow = new Location[row.length];
        for(int i=0; i<row.length; i++){
            mappedRow[i] = new Location(Pipe.fromValue(row[i]));
        }
        return mappedRow;
    }
}
