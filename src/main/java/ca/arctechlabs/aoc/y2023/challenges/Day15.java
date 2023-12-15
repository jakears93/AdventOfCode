package ca.arctechlabs.aoc.y2023.challenges;

import java.util.*;

public class Day15 {

    public int calculateSumOfHashes(String[] entries){
        return Arrays.stream(entries).mapToInt(this::hash).sum();
    }

    public int focusingPower(String[] entries){
        int sum=0;
        List<LinkedHashMap<String, Integer>> boxes = createBoxes(entries);
        for(int b=0; b<boxes.size(); b++){
            LinkedHashMap<String, Integer> box = boxes.get(b);
            int i=0;
            for(String key : box.keySet()){
                i++;
                sum+= ((b+1) * i * box.get(key));
            }
        }
        return sum;
    }

    private List<LinkedHashMap<String, Integer>> createBoxes(String[] entries){
        List<LinkedHashMap<String, Integer>> boxes = new ArrayList<>(256);
        for(int i=0; i<256; i++){
            boxes.add(new LinkedHashMap<>());
        }

        for(String lensString : entries){
            Lens lens = parseLens(lensString);
            LinkedHashMap<String, Integer> box = boxes.get(hash(lens.getId()));
            if(lens.getOperation().equals("=")){
                box.compute(lens.getId(), (k, v) -> lens.getFocalLength());
            }
            else{
                box.remove(lens.getId());
            }
        }
        return boxes;
    }

    private Lens parseLens(String lensString) {
        String operation = lensString.contains("-") ? "-" : "=";
        int focalLength = 0;
        String id;

        try{
            focalLength = Integer.parseInt(String.valueOf(lensString.charAt(lensString.length()-1)));
        } catch (NumberFormatException ignored){}

        id = lensString.replace(operation, "").replace(String.valueOf(focalLength), "");
        return new Lens(id, focalLength, operation);
    }

    private int hash(String word){
        char[] chars = word.toCharArray();
        int current = 0;
        for (char c : chars) {
            current += c;
            current *= 17;
            current = current % 256;
        }
        return current;
    }

    private class Lens{
        private final String id;
        private int focalLength;
        private final String operation;

        public Lens(String id, int focalLength, String operation) {
            this.id = id;
            this.focalLength = focalLength;
            this.operation = operation;
        }

        public void setFocalLength(int focalLength) {
            this.focalLength = focalLength;
        }

        public String getId() {
            return id;
        }

        public int getFocalLength() {
            return focalLength;
        }

        public String getOperation() {
            return operation;
        }
    }
}


