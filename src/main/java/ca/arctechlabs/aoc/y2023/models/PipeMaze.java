package ca.arctechlabs.aoc.y2023.models;

public class PipeMaze {
    private final Location[][] maze;
    private final int mazeHeight;
    private final int mazeWidth;
    private Integer startX;
    private Integer startY;

    public PipeMaze(int mazeHeight, int mazeWidth) {
        this.mazeHeight = mazeHeight;
        this.mazeWidth = mazeWidth;
        this.maze = new Location[mazeHeight][mazeWidth];
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

    public int getMazeHeight() {
        return mazeHeight;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public Location[][] getMaze() {
        return maze;
    }

    public void addRowToMaze(Location[] row, int y) {
        this.maze[y] = row;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for(int y=0; y<this.getMaze().length; y++){
            for(int x=0; x<this.maze[y].length; x++){
                sb.append(this.maze[y][x].getStatus()).append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
