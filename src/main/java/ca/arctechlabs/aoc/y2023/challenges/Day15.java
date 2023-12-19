package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

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
            if(lensString.contains("=")){
                int focalLength = Integer.parseInt(String.valueOf(lensString.charAt(lensString.length()-1)));
                String id = lensString.replace("=", "").replace(String.valueOf(focalLength), "");
                boxes.get(hash(id)).compute(id, (k, v) -> focalLength);
            }
            else{
                String id = lensString.replace("-", "");
                boxes.get(hash(id)).remove(id);
            }
        }
        return boxes;
    }

    private int hash(String word){
        int hash = 0;
        for (char c : word.toCharArray()) {
            hash += c;
            hash *= 17;
            hash = hash & 255;
        }
        return hash;
    }
}