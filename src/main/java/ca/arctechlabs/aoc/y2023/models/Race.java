package ca.arctechlabs.aoc.y2023.models;

public class Race {
    private long timeInMs;
    private long distanceInMm;

    public Race() {
    }

    public Race(long timeInMs, long distanceInMm) {
        this.timeInMs = timeInMs;
        this.distanceInMm = distanceInMm;
    }

    public long getTimeInMs() {
        return timeInMs;
    }

    public void setTimeInMs(long timeInMs) {
        this.timeInMs = timeInMs;
    }

    public long getDistanceInMm() {
        return distanceInMm;
    }

    public void setDistanceInMm(long distanceInMm) {
        this.distanceInMm = distanceInMm;
    }

    @Override
    public String toString() {
        return "Race{" +
                "timeInMs=" + timeInMs +
                ", distanceInMm=" + distanceInMm +
                '}';
    }
}
