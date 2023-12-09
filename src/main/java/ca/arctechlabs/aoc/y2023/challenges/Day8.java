package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.Utils;
import ca.arctechlabs.aoc.y2023.models.Node;
import ca.arctechlabs.aoc.y2023.models.NodeDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day8 {
    //https://adventofcode.com/2023/day/8

    public long stepsToTraverseMap(List<String> input, Predicate<String> startRule, Predicate<String> endRule){
        List<NodeDirection> directions = parseDirections(input.get(0));
        Map<String, Node> nodes = new HashMap<>();
        input.stream()
                .skip(2)
                .forEach(line -> {
                    Node node = parseNode(line);
                    nodes.put(node.getName(), node);
                });
        return countTotalSteps(directions, nodes, startRule, endRule);
    }

    private long countTotalSteps(List<NodeDirection> directions, Map<String, Node> nodes, Predicate<String> startRule, Predicate<String> endRule){
        List<Long> nodePathLengths = new ArrayList<>();

        List<Node> currentNodes = nodes.keySet().stream()
                .filter(startRule)
                .map(nodes::get)
                .toList();

        for(Node node : currentNodes){
            Long pathLength = countStepsPerNode(directions, nodes, node, endRule);
            nodePathLengths.add(pathLength);
        }

        return Utils.calculateLCM(nodePathLengths);
    }

    private long countStepsPerNode(List<NodeDirection> directions, Map<String, Node> nodes, Node startingNode, Predicate<String> endRule){
        long totalSteps = 0;
        boolean foundEnd = false;
        Node currentNode = startingNode;
        while (!foundEnd){
            for(NodeDirection direction : directions) {
                totalSteps++;
                if (NodeDirection.LEFT.equals(direction)) {
                    currentNode = nodes.get(currentNode.getLeft());
                } else if (NodeDirection.RIGHT.equals(direction)) {
                    currentNode = nodes.get(currentNode.getRight());
                }
                if (endRule.test(currentNode.getName())) {
                    foundEnd = true;
                    break;
                }
            }
        }
        return totalSteps;
    }

    private Node parseNode(String line){
        Node node = new Node();
        String[] components = line.split(" = ");
        node.setName(components[0].trim());
        String[] nodeValues = components[1].replace("(", "")
                .replace(")", "")
                .split(", ");
        node.setLeft(nodeValues[0].trim());
        node.setRight(nodeValues[1].trim());
        return node;
    }

    private List<NodeDirection> parseDirections(String line){
        List<NodeDirection> directions = new ArrayList<>();
        for(char c : line.toCharArray()){
            directions.add(NodeDirection.fromValue(c));
        }
        return directions;
    }
}
