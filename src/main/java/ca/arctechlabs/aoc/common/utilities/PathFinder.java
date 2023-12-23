package ca.arctechlabs.aoc.common.utilities;

import ca.arctechlabs.aoc.common.models.GraphNode;

import java.util.*;

public class PathFinder {
    public static <T> Integer bfs(GraphNode<T> start, GraphNode<T> goal, List<GraphNode<T>> allNodes){
        //Set distance to all nodes to max Int
        allNodes.forEach(n -> n.setDistance(Integer.MAX_VALUE));

        //Set startNode to 0 distance
        start.setDistance(0);

        //Set goalNode distance to null
        goal.setDistance(null);

        //Create new master queue
        Queue<GraphNode<T>> masterQueue = new ArrayDeque<>();

        //Created Visited List
        Set<GraphNode<T>> visited = new HashSet<>();
        //Add start to the master Queue
        masterQueue.add(start);

        while(!masterQueue.isEmpty() && Objects.isNull(goal.getDistance())){
            //Get current node
            GraphNode<T> current = masterQueue.poll();
            //Get all neighbours of current node
            List<GraphNode<T>> currentNeighbours = allNodes.stream()
                    .filter(graphNode -> !visited.contains(graphNode))
                    .filter(current.isAPossibleNeighbour)
                    .toList();

            //Set all neighbours distance to current node distance + 1;
            currentNeighbours.forEach(n -> n.setDistance(current.getDistance()+1));
            //Add all neighbours to master queue
            masterQueue.addAll(currentNeighbours);
            visited.add(current);
        }

        return goal.getDistance();
    }
}
