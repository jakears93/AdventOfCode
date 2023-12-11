package ca.arctechlabs.aoc.y2023.models;

public enum PipeStatus {
    PATH('X'),
    INSIDE('I'),
    OUTSIDE('O'),
    UNCHECKED('?');

    private char value;
    PipeStatus(char value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
