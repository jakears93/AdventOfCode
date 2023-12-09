package ca.arctechlabs.aoc.y2023.models;

public class AttributeMapping {
    long destinationStart;
    long sourceStart;
    long range;

    public AttributeMapping(long destinationStart, long sourceStart, long range) {
        this.destinationStart = destinationStart;
        this.sourceStart = sourceStart;
        this.range = range;
    }

    public long getSourceStart() {
        return sourceStart;
    }

    public void setSourceStart(long sourceStart) {
        this.sourceStart = sourceStart;
    }

    public long getDestinationStart() {
        return destinationStart;
    }

    public void setDestinationStart(long destinationStart) {
        this.destinationStart = destinationStart;
    }

    public long getRange() {
        return range;
    }

    public void setRange(long range) {
        this.range = range;
    }
}
