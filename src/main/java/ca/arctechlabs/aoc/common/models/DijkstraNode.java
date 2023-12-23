package ca.arctechlabs.aoc.common.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class DijkstraNode<T> extends GraphNode<T> {
    @Setter
    private DijkstraNode<T> previousVertex;
    private final Map<DijkstraNode<T>, Integer> distanceToNeighbours;

    public DijkstraNode(T value) {
        super(value);
        this.previousVertex = null;
        this.distance = Integer.MAX_VALUE;
        this.distanceToNeighbours = new HashMap<>();
    }

    public void addNeighbour(DijkstraNode<T> neighbour, Integer distance){
        this.distanceToNeighbours.put(neighbour, distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DijkstraNode<?> that = (DijkstraNode<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "DijkstraNode{" +
                "value=" + value +
                ", shortestDistanceFromStart=" + distance +
                ", previousVertex=" + previousVertex +
                "}";
    }
}
