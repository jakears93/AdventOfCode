package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.Utils;
import ca.arctechlabs.aoc.common.models.Coordinates;
import ca.arctechlabs.aoc.common.models.Direction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public BigInteger countTrenchArea(List<String> input){
        List<Instruction> instructions = parseInstructions(input);
        List<Coordinates> coordinates = convertInstructionsToVertexCoords(instructions);
        BigInteger perimeter = BigInteger.valueOf(countPerimeter(instructions));
        return Utils.calculateAreaFromVertices(coordinates).add(perimeter.divide(BigInteger.TWO).add(BigInteger.ONE));
    }

    public BigInteger countTrenchAreaColourSwap(List<String> input){
        List<Instruction> instructions = parseColoursAsInstructions(input);
        List<Coordinates> coordinates = convertInstructionsToVertexCoords(instructions);
        BigInteger perimeter = BigInteger.valueOf(countPerimeter(instructions));
        return Utils.calculateAreaFromVertices(coordinates).add(perimeter.divide(BigInteger.TWO).add(BigInteger.ONE));
    }
    private long countPerimeter(List<Instruction> instructions){
        return instructions.stream().mapToLong(Instruction::getSteps).sum();
    }
    private List<Coordinates> convertInstructionsToVertexCoords(List<Instruction> instructions){
        List<Coordinates> coordinates = new ArrayList<>();
        Coordinates current = new Coordinates(0,0);
        Coordinates nextCoords;
        Coordinates delta;
        for(Instruction instruction : instructions){
            delta = new Coordinates(instruction.getDirection().getDeltaX(), instruction.getDirection().getDeltaY());
            int steps = instruction.getSteps();
            nextCoords = new Coordinates(current.getX() + (steps*delta.getX()), current.getY() + (steps*delta.getY()));
            coordinates.add(nextCoords);
            current = new Coordinates(nextCoords.getX(), nextCoords.getY());
        }
        return coordinates;
    }
    private List<Instruction> parseColoursAsInstructions(List<String> input){
        List<Instruction> instructions = new ArrayList<>();
        for(String row: input){
            Instruction instruction = new Instruction();
            String[] components = row.split(" ");
            instruction.setColour(components[2].substring(2, components[2].length()-1));
            instruction.setDirection(intToDirection(Integer.parseInt(instruction.getColour().substring(instruction.getColour().length()-1))));
            instruction.setSteps(Integer.parseInt(instruction.getColour().substring(0,instruction.getColour().length()-1), 16));
            instructions.add(instruction);
        }
        return instructions;
    }
    private Direction intToDirection(int num){
        if(num==0) return Direction.EAST;
        else if(num==1) return Direction.SOUTH;
        else if(num==2) return Direction.WEST;
        else return Direction.NORTH;
    }

    private Direction stringToDirection(String s){
        return switch (s) {
            case "R" -> Direction.EAST;
            case "D" -> Direction.SOUTH;
            case "L" -> Direction.WEST;
            default -> Direction.NORTH;
        };
    }
    private List<Instruction> parseInstructions(List<String> input){
        List<Instruction> instructions = new ArrayList<>();
        for(String row: input){
            Instruction instruction = new Instruction();
            String[] components = row.split(" ");
            instruction.setDirection(stringToDirection(components[0]));
            instruction.setSteps(Integer.parseInt(components[1]));
            instruction.setColour(components[2].substring(1, components[2].length()-1));
            instructions.add(instruction);
        }
        return instructions;
    }

    private static class Instruction{
        private Direction direction;
        private int steps;
        private String colour;

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "direction=" + direction +
                    ", steps=" + steps +
                    ", colour='" + colour + '\'' +
                    "}\n";
        }
    }
}
