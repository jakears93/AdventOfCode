package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Coordinates;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public BigInteger countTrenchArea(List<String> input){
        List<Instruction> instructions = parseInstructions(input);
        List<Coordinates> coordinates = convertInstructionsToVertexCoords(instructions);
        BigInteger perimeter = BigInteger.valueOf(countPerimeter(instructions));
        return calculateAreaFromCoords(coordinates).add(perimeter.divide(BigInteger.TWO).add(BigInteger.ONE));
    }

    public BigInteger countTrenchAreaColourSwap(List<String> input){
        List<Instruction> instructions = parseColoursAsInstructions(input);
        List<Coordinates> coordinates = convertInstructionsToVertexCoords(instructions);
        BigInteger perimeter = BigInteger.valueOf(countPerimeter(instructions));
        return calculateAreaFromCoords(coordinates).add(perimeter.divide(BigInteger.TWO).add(BigInteger.ONE));
    }
    private long countPerimeter(List<Instruction> instructions){
        return instructions.stream().mapToLong(Instruction::getSteps).sum();
    }
    private BigInteger calculateAreaFromCoords(List<Coordinates> coordinates){
        BigInteger pSum = new BigInteger(String.valueOf(0L));
        BigInteger nSum = new BigInteger(String.valueOf(0L));
        BigInteger x;
        BigInteger y;
        BigInteger product;
        for(int i=0; i<coordinates.size()-1; i++){
            x = BigInteger.valueOf(coordinates.get(i).getX());
            y = BigInteger.valueOf(coordinates.get(i+1).getY());
            product = x.multiply(y);
            pSum = pSum.add(product);
        }
        x = BigInteger.valueOf(coordinates.get(coordinates.size()-1).getX());
        y = BigInteger.valueOf(coordinates.get(0).getY());
        product = x.multiply(y);
        pSum = pSum.add(product);

        for(int i=0; i<coordinates.size()-1; i++){
            x = BigInteger.valueOf(coordinates.get(i+1).getX());
            y = BigInteger.valueOf(coordinates.get(i).getY());
            product = x.multiply(y);
            nSum = nSum.add(product);
        }
        x = BigInteger.valueOf(coordinates.get(0).getX());
        y = BigInteger.valueOf(coordinates.get(coordinates.size()-1).getY());
        product = x.multiply(y);
        nSum = nSum.add(product);

        BigInteger abs = pSum.subtract(nSum).abs();
        return abs.divide(BigInteger.valueOf(2));
    }
    private List<Coordinates> convertInstructionsToVertexCoords(List<Instruction> instructions){
        List<Coordinates> coordinates = new ArrayList<>();
        Coordinates current = new Coordinates(0,0);
        Coordinates nextCoords;
        Coordinates delta;
        for(Instruction instruction : instructions){
            delta = getDelta(instruction.getDirection());
            int steps = instruction.getSteps();
            nextCoords = new Coordinates(current.getX() + (steps*delta.getX()), current.getY() + (steps*delta.getY()));
            coordinates.add(nextCoords);
            current = new Coordinates(nextCoords.getX(), nextCoords.getY());
        }
        return coordinates;
    }
    private Coordinates getDelta(Direction direction){
        Coordinates delta;
        switch (direction){
            case U -> delta = new Coordinates(0, -1);
            case D -> delta = new Coordinates(0, 1);
            case L -> delta = new Coordinates(-1, 0);
            case R -> delta = new Coordinates(1, 0);
            default -> delta = new Coordinates(0,0);
        }
        return delta;
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
        if(num==0) return Direction.R;
        else if(num==1) return Direction.D;
        else if(num==2) return Direction.L;
        else return Direction.U;
    }
    private List<Instruction> parseInstructions(List<String> input){
        List<Instruction> instructions = new ArrayList<>();
        for(String row: input){
            Instruction instruction = new Instruction();
            String[] components = row.split(" ");
            instruction.setDirection(Direction.valueOf(components[0]));
            instruction.setSteps(Integer.parseInt(components[1]));
            instruction.setColour(components[2].substring(1, components[2].length()-1));
            instructions.add(instruction);
        }
        return instructions;
    }

    enum Direction{
        U,
        D,
        L,
        R;
    }

    static class Instruction{
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
