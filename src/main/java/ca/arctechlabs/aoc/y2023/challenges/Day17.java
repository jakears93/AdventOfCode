package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.models.Coordinates;
import ca.arctechlabs.aoc.common.models.DijkstraNode;
import ca.arctechlabs.aoc.common.utilities.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day17 {
    public long lowestHeatLoss(List<String> input) {
        List<DijkstraNode<Coordinates>> nodes = parseInputToNodes(input);
        DijkstraNode<Coordinates> start = nodes.get(0);
        DijkstraNode<Coordinates> end = nodes.get(nodes.size()-1);

        populateNeighbours(nodes, input);
        List<DijkstraNode<Coordinates>> shortestPathsFromStart = Utils.dijkstraDistancesFromStart(start, nodes);
        outputPath(shortestPathsFromStart, end);
        return shortestPathsFromStart.stream()
                .filter(node -> node.equals(end))
                .findFirst()
                .map(DijkstraNode::getShortestDistanceFromStart)
                .orElseThrow();
    }

    private void outputPath(List<DijkstraNode<Coordinates>> shortestPathsFromStart, DijkstraNode<Coordinates> end) {
        DijkstraNode<Coordinates> current = end;
        while(current != null){
            System.out.print("coords = " + current.getValue());
            current = current.getPreviousVertex();
        }
    }

    private void populateNeighbours(List<DijkstraNode<Coordinates>> nodes, List<String> input){
        for(DijkstraNode<Coordinates> node : nodes){
            List<Coordinates> possibleNeighbours = getPossibleNeighbours(node, input.get(0).length(), input.size());
            for(Coordinates possibleNeighbour : possibleNeighbours){
                DijkstraNode<Coordinates> neighbour = nodes.stream().filter(n -> n.getValue().equals(possibleNeighbour)).findFirst().orElse(null);
                if(Objects.nonNull(neighbour)){
                    int heatLoss = Integer.parseInt(String.valueOf(input.get(possibleNeighbour.getY()).charAt(possibleNeighbour.getX())));
                    node.addNeighbour(neighbour, heatLoss);
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
                nodes.add(new DijkstraNode<>(new Coordinates(x,y)));
            }
        }
        return nodes;
    }
}
