package ca.arctechlabs.aoc.y2023.models;

public class Location {
    private final Pipe pipe;
    private PipeStatus status;

    public Location(Pipe pipe) {
        this.pipe = pipe;
        this.status = PipeStatus.UNCHECKED;
    }

    public Pipe getPipe() {
        return pipe;
    }

    public PipeStatus getStatus() {
        return status;
    }

    public void setStatus(PipeStatus status) {
        this.status = status;
    }
}
