package ca.arctechlabs.aoc.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.Predicate;

@Data
@RequiredArgsConstructor
public class GraphNode<T> {
    protected final T value;

    protected Integer distance;
    public Predicate<GraphNode<T>> isAPossibleNeighbour;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode<?> graphNode = (GraphNode<?>) o;
        return Objects.equals(value, graphNode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
