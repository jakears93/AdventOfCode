package ca.arctechlabs.aoc.y2023.models;

public enum PipeDirection {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
    INVALID(0, 0);

    private final int xMovement;
    private final int yMovement;

    PipeDirection(int xMovement, int yMovement) {
        this.xMovement = xMovement;
        this.yMovement = yMovement;
    }

    public int getxMovement() {
        return xMovement;
    }

    public int getyMovement() {
        return yMovement;
    }
}
