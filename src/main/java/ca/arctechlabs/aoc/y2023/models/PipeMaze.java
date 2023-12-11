package ca.arctechlabs.aoc.y2023.models;

public class PipeMaze {
    private final Pipe[][] maze;
    private final int mazeHeight;
    private final int mazeWidth;
    private Integer startX;
    private Integer startY;

    public PipeMaze(int mazeHeight, int mazeWidth) {
        this.mazeHeight = mazeHeight;
        this.mazeWidth = mazeWidth;
        this.maze = new Pipe[mazeHeight][mazeWidth];
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

    public Pipe[][] getMaze() {
        return maze;
    }

    public void addRowToMaze(Pipe[] row, int y) {
        this.maze[y] = row;
    }
}
