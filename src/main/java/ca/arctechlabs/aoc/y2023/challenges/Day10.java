package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Pipe;
import ca.arctechlabs.aoc.y2023.models.PipeDirection;
import ca.arctechlabs.aoc.y2023.models.PipeMaze;

import java.util.*;

public class Day10 {

    private PipeMaze pipeMaze;

    public long lengthOfLoop(){
        long loopLength = -1L;
        for(PipeDirection heading: PipeDirection.values()){
            loopLength = followPipe(this.pipeMaze.getStartX(), this.pipeMaze.getStartY(), heading, 0);
            if(loopLength>0){
                break;
            }
        }
        return loopLength;
    }
    private long followPipe(int x, int y, PipeDirection heading, long stepsTaken){
        Pipe nextPipe = getNextPipe(x, y, heading);

        if(Pipe.START.equals(nextPipe)){
            return stepsTaken+1;
        }
        if(Pipe.INVALID.equals(nextPipe) || Objects.isNull(nextPipe)){
            return -1L;
        }

        int nextX = x + heading.getxMovement();
        int nextY = y + heading.getyMovement();
        PipeDirection complement = getComplement(heading);
        PipeDirection nextHeading;
        if(nextPipe.getEntry().equals(complement)){
            nextHeading = nextPipe.getExit();
        }
        else{
            nextHeading = nextPipe.getEntry();
        }
        return followPipe(nextX, nextY, nextHeading, stepsTaken+1);
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
