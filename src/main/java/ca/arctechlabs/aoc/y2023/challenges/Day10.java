package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Direction;
import ca.arctechlabs.aoc.y2023.models.Pipe;
import ca.arctechlabs.aoc.y2023.models.PipeDirection;
import ca.arctechlabs.aoc.y2023.models.PipeMaze;

import java.util.*;

public class Day10 {

    private PipeMaze pipeMaze;
    //idea: count amount of left/right turns. greater value means inside is on that direction of the path.
//follow path, set location on most direction inside if not a pipe. for any adjacent locations that arent the pipe, set inside. continue until no locations change status
//count total insides
    public long lengthOfLoop(){
        List<Direction> path = createPath();
        return path.size();
    }

    public long areaInsideLoop(){
        List<Direction> path = createPath();
        long leftTurns = path.stream().filter(Direction.LEFT::equals).count();
        long rightTurns = path.stream().filter(Direction.RIGHT::equals).count();
        System.out.println("rightTurns = " + rightTurns);
        System.out.println("leftTurns = " + leftTurns);
        return 0L;
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

    private long followPipe(int initialX, int initialY, PipeDirection heading){
        Pipe nextPipe;
        long loopLength = 0L;
        int x = initialX;
        int y = initialY;
        while(true){
            nextPipe = getNextPipe(x, y, heading);
            if(Pipe.START.equals(nextPipe)){
                loopLength++;
                break;
            }
            if(Pipe.INVALID.equals(nextPipe) || Objects.isNull(nextPipe)){
                loopLength = -1L;
                break;
            }
            x += heading.getxMovement();
            y += heading.getyMovement();
            PipeDirection complement = getComplement(heading);
            if(nextPipe.getEntry().equals(complement)){
                heading = nextPipe.getExit();
            }
            else{
                heading = nextPipe.getEntry();
            }
            loopLength++;

        }
        return loopLength;
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
        Pipe[][] maze = this.pipeMaze.getMaze();
        //Go North
        if(y-1 >= 0  && PipeDirection.NORTH.equals(heading)){
            Pipe northPipe = maze[y-1][x];
            if(Pipe.NS.equals(northPipe) || Pipe.SW.equals(northPipe) || Pipe.SE.equals(northPipe) || Pipe.START.equals(northPipe)){
                nextPipe = northPipe;
            }
        }
        //Go South
        else if(this.pipeMaze.getMazeHeight() > y+1 && PipeDirection.SOUTH.equals(heading)){
            Pipe southPipe = maze[y+1][x];
            if(Pipe.NS.equals(southPipe) || Pipe.NW.equals(southPipe) || Pipe.NE.equals(southPipe) || Pipe.START.equals(southPipe)){
                nextPipe = southPipe;
            }
        }
        //Go East
        else if(this.pipeMaze.getMazeWidth() > x+1 && PipeDirection.EAST.equals(heading)){
            Pipe eastPipe = maze[y][x+1];
            if(Pipe.EW.equals(eastPipe) || Pipe.NW.equals(eastPipe) || Pipe.SW.equals(eastPipe) || Pipe.START.equals(eastPipe)){
                nextPipe = eastPipe;
            }
        }
        //Go West
        else if(x-1 >= 0 && PipeDirection.WEST.equals(heading)){
            Pipe westPipe = maze[y][x-1];
            if(Pipe.EW.equals(westPipe) || Pipe.NE.equals(westPipe) || Pipe.SE.equals(westPipe) || Pipe.START.equals(westPipe)){
                nextPipe = westPipe;
            }
        }

        return nextPipe;
    }
    public void populateMaze(List<String> input){
        PipeMaze pipeMaze = new PipeMaze(input.size(), input.get(0).length());
        
        for(int y=0; y<input.size(); y++){
            Pipe[] mappedRow = mapRow(input.get(y));
            if(Objects.isNull(pipeMaze.getStartX())){
                for(int x=0; x< mappedRow.length; x++){
                    if(Pipe.START.equals(mappedRow[x])){
                        pipeMaze.setStartCoordinates(x, y);
                    }
                }
            }
            pipeMaze.addRowToMaze(mappedRow, y);
        }
        
        this.pipeMaze = pipeMaze;
    }
    private Pipe[] mapRow(String rawRow){
        char[] row = rawRow.toCharArray();
        Pipe[] mappedRow = new Pipe[row.length];
        for(int i=0; i<row.length; i++){
            mappedRow[i] = Pipe.fromValue(row[i]);
        }
        return mappedRow;
    }
}
