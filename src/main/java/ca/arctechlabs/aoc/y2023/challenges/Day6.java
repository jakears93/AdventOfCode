package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    //https://adventofcode.com/2023/day/6
    public long getMargin(List<String> input, int acceleration){
        return parseRaces(input)
                .stream()
                .mapToLong(race -> computeWaysToBeat(race, acceleration))
                .reduce(1, (a, b) -> a*b);
    }

    public long getMarginAsSingleRace(List<String> input, int acceleration){
        return computeWaysToBeat(parseRacesAsSingle(input), acceleration);
    }

    private long computeWaysToBeat(Race race, int acceleration){
        long waysToWin = 0;
        for(int accelTime=0; accelTime<=race.getTimeInMs(); accelTime++){
            long distanceCovered = accelTime*acceleration*(race.getTimeInMs()-accelTime);
            if(distanceCovered > race.getDistanceInMm()){
                waysToWin++;
            }
        }
        return waysToWin;
    }

    private List<Race> parseRaces(List<String> input){
        List<Race> races = new ArrayList<>();
        
        List<String> times = Arrays.stream(input.get(0).split(" ")).filter(split -> !split.isEmpty()).toList();
        List<String> distances = Arrays.stream(input.get(1).split(" ")).filter(split -> !split.isEmpty()).toList();

        for(int i=1; i<times.size(); i++){
            races.add(new Race(Long.parseLong(times.get(i)), Long.parseLong(distances.get(i))));
        }
        
        return races;
    }

    private Race parseRacesAsSingle(List<String> input){
        String timeString = Arrays.stream(input.get(0).split(" "))
                .filter(split -> !split.isEmpty())
                .skip(1)
                .reduce("", (a,b) -> a+b);

        String distanceString = Arrays.stream(input.get(1).split(" "))
                .filter(split -> !split.isEmpty())
                .skip(1)
                .reduce("", (a,b) -> a+b);

        return new Race(Long.parseLong(timeString), Long.parseLong(distanceString));
    }

    private static class Race {
        private final long timeInMs;
        private final long distanceInMm;
        public Race(long timeInMs, long distanceInMm) {
            this.timeInMs = timeInMs;
            this.distanceInMm = distanceInMm;
        }

        public long getTimeInMs() {
            return timeInMs;
        }

        public long getDistanceInMm() {
            return distanceInMm;
        }

        @Override
        public String toString() {
            return "Race{" +
                    "timeInMs=" + timeInMs +
                    ", distanceInMm=" + distanceInMm +
                    '}';
        }
    }
}
