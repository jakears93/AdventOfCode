package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.models.Coordinates;
import ca.arctechlabs.aoc.common.models.DijkstraNode;
import ca.arctechlabs.aoc.common.utilities.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day21 {
    public int numberOfReachableGardenPlots(List<String> input, int steps){
        List<DijkstraNode<Coordinates>> gardenPlots = parseInputToNodes(input);
        populateNeighbours(gardenPlots, input.get(0).length(), input.size());
        Coordinates start = findStart(input);

        List<DijkstraNode<Coordinates>> shortestPathsFromStart = Utils.dijkstraDistancesFromStart(start, gardenPlots);
        return shortestPathsFromStart.stream()
                .filter(node -> node.getShortestDistanceFromStart() <= steps)
                .filter(node -> node.getShortestDistanceFromStart() % 2 == 0)
                .mapToInt(e -> 1)
                .sum();
    }

    private Coordinates findStart(List<String> input) {
        for(int y=0; y<input.size(); y++){
            char[] row = input.get(y).toCharArray();
            for(int x=0; x<row.length; x++){
                if(row[x] == 'S'){
                    return new Coordinates(x,y);
                }
            }
        }
        throw new RuntimeException("Unable to find start coordinates");
    }

    private void populateNeighbours(List<DijkstraNode<Coordinates>> nodes, int maxX, int maxY){
        for(DijkstraNode<Coordinates> node : nodes){
            List<Coordinates> possibleNeighbours = getPossibleNeighbours(node, maxX, maxY);
            for(Coordinates possibleNeighbour : possibleNeighbours){
                DijkstraNode<Coordinates> neighbour = nodes.stream().filter(n -> n.getValue().equals(possibleNeighbour)).findFirst().orElse(null);
                if(Objects.nonNull(neighbour)){
                    node.addNeighbour(neighbour, 1);
                }
            }
        }
    }

    private List<Coordinates> getPossibleNeighbours(DijkstraNode<Coordinates> node, int maxX, int maxY) {
        List<Coordinates> possibleNeighbours = new ArrayList<>();
        int originX = node.getValue().getX();
        int originY = node.getValue().getY();
        for(int deltaX=-1; deltaX<=1; deltaX+=2){
            if(originX+deltaX >= 0 && originX+deltaX < maxX){
                possibleNeighbours.add(new Coordinates(originX+deltaX, originY));
            }
        }
        for(int deltaY=-1; deltaY<=1; deltaY+=2){
            if(originY+deltaY >= 0 && originY+deltaY < maxY){
                possibleNeighbours.add(new Coordinates(originX, originY+deltaY));
            }
        }
        return possibleNeighbours;
    }

    private List<DijkstraNode<Coordinates>> parseInputToNodes(List<String> input) {
        List<DijkstraNode<Coordinates>> nodes = new ArrayList<>();
        for(int y=0; y<input.size(); y++){
            char[] row = input.get(y).toCharArray();
            for(int x=0; x<row.length; x++){
                if(row[x] == '.' || row[x] == 'S'){
                    nodes.add(new DijkstraNode<>(new Coordinates(x,y)));
                }
            }
        }
        return nodes;
    }
}
