package ca.arctechlabs.aoc.common.models;

public enum Turn {
    STRAIGHT('S'),
    LEFT('L'),
    RIGHT('R');

    private final char value;

    Turn(char value) {
        this.value = value;
    }

    public static Turn fromValue(char value){
        for (Turn b : values()) {
            if (b.value == value) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected Turn Value '" + value + "'");
    }

    public char getValue() {
        return value;
    }
}
