package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.*;

import java.util.*;

//https://adventofcode.com/2023/day/10
public class Day10 {

    private PipeMaze pipeMaze;
    private PipeDirection initialHeading;

    public long lengthOfLoop(){
        List<Direction> path = createPath();
        return path.size();
    }

    public long areaInsideLoop(){
        List<Direction> path = createPath();
        Direction insideDirection;
        if(path.stream().filter(Direction.LEFT::equals).count() > path.stream().filter(Direction.RIGHT::equals).count()){
            insideDirection = Direction.LEFT;
        }
        else{
            insideDirection = Direction.RIGHT;
        }

        setPipeStatusForPath(path);
        setPipeStatusForInside(path, insideDirection);
        printInsides();
        return countInsides();
    }

    private void printInsides(){
        StringBuilder sb = new StringBuilder();
        Location[][] maze = this.pipeMaze.getMaze();
        for(int y=0; y<maze.length; y++){
            Location[] row = maze[y];
            for(int x=0; x<row.length; x++){
                if(row[x].getStatus().equals(PipeStatus.INSIDE)) sb.append("#");
                else if(row[x].getStatus().equals(PipeStatus.PATH)) sb.append(row[x].getPipe().getValue());
                else if(row[x].getStatus().equals(PipeStatus.UNCHECKED)) sb.append("O");
                else if(row[x].getStatus().equals(PipeStatus.OUTSIDE)) sb.append(" ");
                else sb.append(row[x].getPipe().getValue());
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    private void setPipeStatusForInside(List<Direction> path, Direction insideDirection){
        //follow path, set location on most direction inside if not a pipe. for any adjacent locations that arent the pipe, set inside. continue until no locations change status
        int x = this.pipeMaze.getStartX();
        int y = this.pipeMaze.getStartY();
        PipeDirection heading = this.initialHeading;

        for(Direction direction : path){
            Location location = this.pipeMaze.getMaze()[y][x];
            heading = calculateNextHeading(heading, direction);
            //get location on left and set to inside, if already set, skip to next location in path;
            Coordinates deltaCoords = getAdjacentCoordinates(location, insideDirection, heading);
            try{
                //get all adjacent locations to the inside pipe that are unchecked and set to inside
                floodFill(new Coordinates(x+deltaCoords.getX(), y+deltaCoords.getY()));
            } catch(Exception ignored){}


            //navigate to next location in path
            x += heading.getxMovement();
            y += heading.getyMovement();
        }
    }

    private void floodFill(Coordinates coordinates) {
        Location inside = this.pipeMaze.getMaze()[coordinates.getY()][coordinates.getX()];
        if(inside.getStatus().equals(PipeStatus.INSIDE) || inside.getStatus().equals(PipeStatus.PATH)){
            return;
        }
        inside.setStatus(PipeStatus.INSIDE);
        //get all adjacent locations to the inside pipe that are unchecked and set to inside
        List<Coordinates> adjacent = getAdjacent(coordinates);
        for(Coordinates next : adjacent){
            floodFill(next);
        }
    }

    private List<Coordinates> getAdjacent(Coordinates origin){
        int maxX = this.pipeMaze.getMaze()[0].length;
        int maxY = this.pipeMaze.getMaze().length;
        List<Coordinates> adjacent = new ArrayList<>();
        for(int x=-1; x<=1; x++){
            if(origin.getX()+x<0 || origin.getX()+x >= maxX) continue;
            for(int y=-1; y<=1; y++){
                if(origin.getY()+y<0 || origin.getY()+y >= maxY || (x==0 && y==0)) continue;
                Coordinates coordinates = new Coordinates(origin.getX()+x, origin.getY()+y);
                if(coordinates.getX() == 42 && coordinates.getY()==13){
                    System.out.println();
                }
                adjacent.add(coordinates);
            }
        }
        return adjacent;
    }

    private boolean validateCoordinatesRelativeToPath(int newX, int newY, Location[][] maze) {
        return (maze[0].length > newX) && (maze.length > newY);
    }

    private Coordinates getAdjacentCoordinates(Location location, Direction insideDirection, PipeDirection heading) {
        Coordinates coordinates = new Coordinates();
        if(Direction.RIGHT.equals(insideDirection)){
            switch (heading){
                case NORTH ->{
                    coordinates.setX(1);
                    coordinates.setY(0);
                }
                case SOUTH ->{
                    coordinates.setX(-1);
                    coordinates.setY(0);
                }
                case EAST -> {
                    coordinates.setX(0);
                    coordinates.setY(1);
                }
                case WEST -> {
                    coordinates.setX(0);
                    coordinates.setY(-1);
                }
                default -> {
                    coordinates.setX(0);
                    coordinates.setY(0);
                }
            }
        }
        else{
            switch (heading){
                case NORTH ->{
                    coordinates.setX(-1);
                    coordinates.setY(0);
                }
                case SOUTH ->{
                    coordinates.setX(1);
                    coordinates.setY(0);
                }
                case EAST -> {
                    coordinates.setX(0);
                    coordinates.setY(-1);
                }
                case WEST -> {
                    coordinates.setX(0);
                    coordinates.setY(1);
                }
                default -> {
                    coordinates.setX(0);
                    coordinates.setY(0);
                }
            }
        }
        return coordinates;
    }

    private long countInsides(){
        Location[][] maze = this.pipeMaze.getMaze();
        long count = 0;
        for (Location[] locations : maze) {
            for (Location location : locations) {
                if (PipeStatus.INSIDE.equals(location.getStatus())) count++;
            }
        }
        return count;
    }

    public void setPipeStatusForPath(List<Direction> path){
        int x = this.pipeMaze.getStartX();
        int y = this.pipeMaze.getStartY();
        PipeDirection heading = this.initialHeading;

        for(Direction direction : path){
            Location location = this.pipeMaze.getMaze()[y][x];
            heading = calculateNextHeading(heading, direction);
            location.setStatus(PipeStatus.PATH);
            x += heading.getxMovement();
            y += heading.getyMovement();
        }
    }
    private PipeDirection calculateNextHeading(PipeDirection heading, Direction direction){
        PipeDirection nextHeading = heading;
        if(PipeDirection.NORTH.equals(heading)){
            if(Direction.RIGHT.equals(direction)){
                nextHeading = PipeDirection.EAST;
            }
            else if(Direction.LEFT.equals(direction)){
                nextHeading = PipeDirection.WEST;
            }
        }
        else if(PipeDirection.SOUTH.equals(heading)){
            if(Direction.RIGHT.equals(direction)){
                nextHeading = PipeDirection.WEST;
            }
            else if(Direction.LEFT.equals(direction)){
                nextHeading = PipeDirection.EAST;
            }
        }
        else if(PipeDirection.EAST.equals(heading)){
            if(Direction.RIGHT.equals(direction)){
                nextHeading = PipeDirection.SOUTH;
            }
            else if(Direction.LEFT.equals(direction)){
                nextHeading = PipeDirection.NORTH;
            }
        }
        else if(PipeDirection.WEST.equals(heading)){
            if(Direction.RIGHT.equals(direction)){
                nextHeading = PipeDirection.NORTH;
            }
            else if(Direction.LEFT.equals(direction)){
                nextHeading = PipeDirection.SOUTH;
            }
        }

        return nextHeading;
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
