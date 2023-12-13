package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day13 {

    public long findMirrorNumberSum(List<String> lines){
        List<List<String>> mirrors = splitIntoMirrors(lines);
        return mirrors.stream().mapToLong(this::findMirrorNumber).sum();
    }
    private long findMirrorNumber(List<String> partialLines){
        List<Integer> bits = convertToRowBits(partialLines, '#');
        Integer rowIndex = checkIfMirrored(bits);
        if(rowIndex != null){
            return rowIndex*100;
        }
        bits = convertToColumnBits(partialLines, '#');
        Integer columnIndex = checkIfMirrored(bits);
        if(columnIndex != null){
            return columnIndex;
        }
        return 0L;
    }

    private List<List<String>> splitIntoMirrors(List<String> input){
        List<List<String>> mirrors = new ArrayList<>();
        List<Integer> spacers = new ArrayList<>();

        for(int i=0; i<input.size(); i++){
            if(input.get(i).isBlank()) spacers.add(i);
        }

        int start = 0;
        for(Integer index : spacers){
            mirrors.add(input.subList(start, index));
            start = index+1;
        }
        mirrors.add(input.subList(start, input.size()));
        return mirrors;
    }
    private Integer checkIfMirrored(List<Integer> bitMap) {
        boolean isMirrored = false;
        Integer mirrorIndex = null;
        for(int i=0; i<bitMap.size()-1; i++){
            if(Objects.equals(bitMap.get(i), bitMap.get(i + 1))){
                isMirrored = true;
                mirrorIndex = i+1;
                int j=1;
                while((i-j) >= 0 && (i+j)<(bitMap.size()-1)){
                    int left = bitMap.get(i-j);
                    int right = bitMap.get(i+j+1);
                    j++;
                    if(left != right){
                        isMirrored = false;
                        break;
                    }
                }
            }
            if(isMirrored){
                break;
            }
        }
        if(isMirrored){
            return mirrorIndex;
        }
        else{
            return null;
        }
    }

    public List<Integer> convertToColumnBits(List<String> lines, char bitMarker){
        int columnSize = lines.get(0).length();
        List<Integer> bitMap = new ArrayList<>(columnSize);

        for(int col=0; col<columnSize; col++){
            int bitLine = 0;
            for (String line : lines) {
                if (line.charAt(col) == bitMarker) bitLine |= 1;
                bitLine <<= 1;
            }
            bitMap.add(bitLine);
        }
        return bitMap;
    }
    public List<Integer> convertToRowBits(List<String> lines, char bitMarker){
        List<Integer> bitMap = new ArrayList<>(lines.size());
        for(String line : lines){
            int bitLine = 0;
            for(char c : line.toCharArray()){
                if(c == bitMarker) bitLine |= 1;
                bitLine <<= 1;
            }
            bitMap.add(bitLine);
        }
        return bitMap;
    }
}
