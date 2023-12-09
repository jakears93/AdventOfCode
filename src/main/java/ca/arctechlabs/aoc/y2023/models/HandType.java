package ca.arctechlabs.aoc.y2023.models;

public enum HandType {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1),
    INVALID(0);

    private int value;

    HandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
