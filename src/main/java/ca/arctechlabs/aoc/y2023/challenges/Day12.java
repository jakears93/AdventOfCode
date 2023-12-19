package ca.arctechlabs.aoc.y2023.challenges;


import java.util.ArrayList;
import java.util.List;

//https://adventofcode.com/2023/day/12
public class Day12 {
    public long totalArrangements(List<String> input) {
        List<SpringData> springDataList = parseInput(input);
        return springDataList.stream().map(this::getNumberOfArrangements).mapToLong(Long::longValue).sum();
    }

    public long totalArrangements(List<String> input, int foldLevel) {
        List<SpringData> rawSpringDataList = parseInput(input);
        List<SpringData> springDataList = unFold(rawSpringDataList, foldLevel);
        return springDataList.stream().map(this::getNumberOfArrangements).mapToLong(Long::longValue).sum();
    }

    private long getNumberOfArrangements(SpringData data){
        List<Integer> unknownIndices = new ArrayList<>();
        for(int i=0; i<data.getSequence().size(); i++){
            if(data.getSequence().get(i).equals(Spring.UNKNOWN)) unknownIndices.add(i);
        }

        List<List<Spring>> allPossibleSequences = allPossibleSequences(data.getSequence(), unknownIndices, data.getTotalDamaged() - data.getInitialDamaged());

        return allPossibleSequences.stream()
                .filter(testSeq -> validateArrangement(data, testSeq))
                .count();
    }

    List<List<Spring>> allPossibleSequences(List<Spring> baseSequence, List<Integer> unknownIndices, long totalDamagedToPlace){
        if(totalDamagedToPlace <= 0){
            return List.of(baseSequence);
        }
        List<List<Spring>> allSeq = new ArrayList<>();
        List<int[]> allReplacements = getAllCombinations(unknownIndices.toArray(new Integer[0]), (int) totalDamagedToPlace);

        for(int[] replacement : allReplacements){
            allSeq.add(applyReplacements(replacement, baseSequence));
        }

        return allSeq;
    }

    List<int[]>  getAllCombinations(Integer[] input, int k){
        List<int[]> subsets = new ArrayList<>();

        int[] s = new int[k];                  // here we'll keep indices
        // pointing to elements in input array

        if (k <= input.length) {
            // first index sequence: 0, 1, 2, ...
            for (int i = 0; (s[i] = i) < k - 1; i++);
            subsets.add(getSubset(input, s));
            for(;;) {
                int i;
                // find position of item that can be incremented
                for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--);
                if (i < 0) {
                    break;
                }
                s[i]++;                    // increment this item
                for (++i; i < k; i++) {    // fill up remaining items
                    s[i] = s[i - 1] + 1;
                }
                subsets.add(getSubset(input, s));
            }
        }
        return subsets;
    }

    int[] getSubset(Integer[] input, int[] subset) {
        int[] result = new int[subset.length];
        for (int i = 0; i < subset.length; i++)
            result[i] = input[subset[i]];
        return result;
    }

    List<Spring> applyReplacements(int[] indices, List<Spring> base){
        List<Spring> updated = new ArrayList<>(base);
        for(Integer index : indices){
            updated.add(index, Spring.DAMAGED);
            updated.remove(index+1);
        }
        return updated;
    }

    private boolean validateArrangement(SpringData data, List<Spring> testSequence){
        if(!calculateDamagedGroups(testSequence).equals(data.getDamagedGroups())){
            return false;
        }
        else return testSequence.size() == data.getSequence().size();
    }

    private List<Long> calculateDamagedGroups(List<Spring> sequence){
        List<Long> damagedGroups = new ArrayList<>();
        boolean currentlyDamaged = false;
        long damagedCount = 0;
        for(Spring spring : sequence){
            if(currentlyDamaged && Spring.DAMAGED.equals(spring)){
                damagedCount++;
            }
            else if(!currentlyDamaged && Spring.DAMAGED.equals(spring)){
                damagedCount++;
                currentlyDamaged = true;
            }
            else if(currentlyDamaged){
                damagedGroups.add(damagedCount);
                currentlyDamaged = false;
                damagedCount = 0L;
            }
        }
        damagedGroups.add(damagedCount);
        return damagedGroups.stream().filter(v -> v !=0).toList();
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
        List<SpringData> result = new ArrayList<>(base.size());
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
        private final long totalDamaged;
        private final long initialDamaged;

        public SpringData(List<Long> damagedGroups, List<Spring> sequence) {
            this.damagedGroups = damagedGroups;
            this.sequence = sequence;
            this.totalDamaged = this.damagedGroups.stream().mapToLong(Long::longValue).sum();
            this.initialDamaged = this.sequence.stream().filter(Spring.DAMAGED::equals).count();
        }
        public List<Long> getDamagedGroups() {
            return damagedGroups;
        }
        public List<Spring> getSequence() {
            return sequence;
        }
        public long getTotalDamaged() {
            return totalDamaged;
        }
        public long getInitialDamaged() {
            return initialDamaged;
        }
        @Override
        public String toString() {
            return "SpringData{" +
                    "damagedGroups=" + damagedGroups +
                    ", sequence=" + sequence +
                    "}\n";
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
