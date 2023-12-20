package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://adventofcode.com/2023/day/12
//Heavily relied on explanation from https://advent-of-code.xavd.id/writeups/2023/day/12/
public class Day12 {

    private final Map<String, Long> cache = new HashMap<>();
    public long totalArrangements(List<String> input) {
        return totalArrangements(input, 0);
    }

    public long totalArrangements(List<String> input, int foldLevel) {
        List<SpringData> springDataList = unFold(parseInput(input), foldLevel);
        List<Long> arrangements = new ArrayList<>();
        for(SpringData data : springDataList){
            arrangements.add(numberOfValidSolutions(data.getSequence(), data.damagedGroups));
        }

        return arrangements.stream().mapToLong(Long::longValue).sum();
    }

    private long numberOfValidSolutions(List<Spring> sequence, List<Long> shape){
        String hash = String.valueOf(sequence.hashCode())+ shape.hashCode();
        if(this.cache.containsKey(hash)){
            return this.cache.get(hash);
        }

        if(sequence.isEmpty()){
            if(shape.isEmpty()){
                this.cache.put(hash, 1L);
                return 1;
            }
            this.cache.put(hash, 0L);
            return 0;
        }
        if(shape.isEmpty()){
            if(sequence.contains(Spring.DAMAGED)){
                this.cache.put(hash, 0L);
                return 0;
            }
            this.cache.put(hash, 1L);
            return 1;
        }

        Spring start = sequence.get(0);
        List<Spring> remainder = sequence.subList(1, sequence.size());

        if(Spring.OPERATIONAL.equals(start)){
            long value = numberOfValidSolutions(remainder, shape);
            hash = String.valueOf(remainder.hashCode())+ shape.hashCode();
            this.cache.put(hash, value);
            return value;
        }
        else if(Spring.DAMAGED.equals(start)){
            long group = shape.get(0);
            if(sequence.size() >= group
                && !sequence.subList(0, (int) group).contains(Spring.OPERATIONAL)
                && (sequence.size() == group || !sequence.get((int) group).equals(Spring.DAMAGED)))
            {
                List<Spring> sublist;
                if(sequence.size() > group){
                    sublist = sequence.subList((int) (group+1), sequence.size());
                }
                else{
                    sublist = new ArrayList<>();
                }
                long value = numberOfValidSolutions(sublist, shape.subList(1,shape.size()));
                this.cache.put(hash, value);
                return value;
            }
            return 0;
        }
        else if(Spring.UNKNOWN.equals(start)){
            List<Spring> list1 = new ArrayList<>(remainder);
            list1.add(0, Spring.DAMAGED);

            List<Spring> list2 = new ArrayList<>(remainder);
            list2.add(0, Spring.OPERATIONAL);
            return numberOfValidSolutions(list1, shape) + numberOfValidSolutions(list2, shape);
        }

        throw new IllegalArgumentException("Invalid Spring Type: "+start.value);
    }

    private List<SpringData> parseInput(List<String> input){
        List<SpringData> data = new ArrayList<>();
        for(String line : input){
            List<Spring> sequence = new ArrayList<>();
            List<Long> damagedGroups = new ArrayList<>();

            String[] splitData = line.split(" ");
            for(char c : splitData[0].toCharArray()){
                sequence.add(Spring.fromValue(c));
            }

            for(String i : splitData[1].split(",")){
                damagedGroups.add(Long.parseLong(i));
            }
            
            data.add(new SpringData(damagedGroups, sequence));
        }
        return data;
    }

    private List<SpringData> unFold(List<SpringData> base, int foldLevel){
        if(foldLevel<=1){
            return base;
        }

        List<SpringData> result = new ArrayList<>();
        for(SpringData data : base){
            List<Spring> springs = new ArrayList<>(data.getSequence().size()*foldLevel+foldLevel);
            List<Long> damagedGroups = new ArrayList<>(data.getDamagedGroups().size()*foldLevel);
            for(int i=0; i<foldLevel; i++){
                springs.addAll(data.getSequence());
                springs.add(Spring.UNKNOWN);
                damagedGroups.addAll(data.getDamagedGroups());
            }
            springs.remove(springs.size()-1);
            result.add(new SpringData(damagedGroups, springs));
        }
        return result;
    }

    private static class SpringData{
        private final List<Long> damagedGroups;
        private final List<Spring> sequence;

        public SpringData(List<Long> damagedGroups, List<Spring> sequence) {
            this.damagedGroups = damagedGroups;
            this.sequence = sequence;
        }
        public List<Long> getDamagedGroups() {
            return damagedGroups;
        }
        public List<Spring> getSequence() {
            return sequence;
        }
    }

    private enum Spring{
        OPERATIONAL('.'),
        DAMAGED('#'),
        UNKNOWN('?');

        private final char value;
        Spring(char value) {
            this.value = value;
        }

        public static Spring fromValue(char c){
            for(Spring spring: values()){
                if(c == spring.value){
                    return spring;
                }
            }
            throw new IllegalArgumentException("No spring available for value: "+c);
        }
    }
}
