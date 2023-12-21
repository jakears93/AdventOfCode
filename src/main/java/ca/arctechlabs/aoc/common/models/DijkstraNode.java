package ca.arctechlabs.aoc.common.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DijkstraNode<T> {
    private final T value;
    private int shortestDistanceFromStart;
    private DijkstraNode<T> previousVertex;
    private Map<DijkstraNode<T>, Integer> distanceToNeighbours;

    public DijkstraNode(T value) {
        this.value = value;
        this.previousVertex = null;
        this.shortestDistanceFromStart = Integer.MAX_VALUE;
        this.distanceToNeighbours = new HashMap<>();
    }

    public Map<DijkstraNode<T>, Integer> getDistanceToNeighbours() {
        return distanceToNeighbours;
    }

    public void setDistanceToNeighbours(Map<DijkstraNode<T>, Integer> distanceToNeighbours) {
        this.distanceToNeighbours = distanceToNeighbours;
    }

    public void addNeighbour(DijkstraNode<T> neighbour, Integer distance){
        this.distanceToNeighbours.put(neighbour, distance);
    }

    public T getValue() {
        return value;
    }

    public int getShortestDistanceFromStart() {
        return shortestDistanceFromStart;
    }

    public void setShortestDistanceFromStart(int shortestDistanceFromStart) {
        this.shortestDistanceFromStart = shortestDistanceFromStart;
    }

    public DijkstraNode<T> getPreviousVertex() {
        return previousVertex;
    }

    public void setPreviousVertex(DijkstraNode<T> previousVertex) {
        this.previousVertex = previousVertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DijkstraNode<?> that = (DijkstraNode<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "DijkstraNode{" +
                "value=" + value +
                ", shortestDistanceFromStart=" + shortestDistanceFromStart +
                ", previousVertex=" + previousVertex +
                "}";
    }
}
