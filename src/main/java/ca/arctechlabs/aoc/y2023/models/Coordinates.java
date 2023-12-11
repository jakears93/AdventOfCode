package ca.arctechlabs.aoc.y2023.models;

public class Coordinates{
    private long x;
    private long y;

    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                "}\n";
    }
}
