package ca.arctechlabs.aoc.y2023.models;

public enum NodeDirection {
    LEFT('L'),
    RIGHT('R');

    private char value;

    NodeDirection(char value) {
        this.value = value;
    }

    public static NodeDirection fromValue(char value){
        NodeDirection[] var1 = values();
        int var2 = var1.length;

        for (NodeDirection b : var1) {
            if (b.value == value) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected Node Direction Value '" + value + "'");
    }

    public char getValue() {
        return value;
    }
}
