package ca.arctechlabs.aoc.y2023.models;

public class Location<T> {
    private int x;
    private int y;
    private final T node;

    public Location(T node) {
        this.node = node;
    }

    public Location(int x, int y, T node) {
        this.x = x;
        this.y = y;
        this.node = node;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public T getNode() {
        return node;
    }
}
