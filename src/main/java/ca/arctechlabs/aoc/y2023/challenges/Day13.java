package ca.arctechlabs.aoc.y2023.challenges;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//https://adventofcode.com/2023/day/13
public class Day13 {

    public long findMirrorNumberSum(List<String> lines){
        List<List<String>> mirrors = splitIntoMirrors(lines);
        return mirrors.stream().mapToLong(this::findMirrorNumber).sum();
    }

    public long findSmudgedMirrorNumberSum(List<String> lines){
        List<List<String>> mirrors = splitIntoMirrors(lines);
        return mirrors.stream().mapToLong(this::findSmudgedMirrorNumber).sum();
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

    private long findSmudgedMirrorNumber(List<String> partialLines){
        List<Integer> bits = convertToRowBits(partialLines, '#');
        Integer smudgedMirrorIndex = checkIfSmudgedMirror(bits);
        if(smudgedMirrorIndex != null){
            return smudgedMirrorIndex*100;
        }

        bits = convertToColumnBits(partialLines, '#');
        smudgedMirrorIndex = checkIfSmudgedMirror(bits);
        if (smudgedMirrorIndex == null) {
            return 0L;
        }
        return smudgedMirrorIndex;

    }

    private Integer checkIfSmudgedMirror(List<Integer> bitMap) {
        Integer mirrorIndex = null;
        boolean foundSmudge = false;
        boolean isMirrored = false;
        for(int i=0; i<bitMap.size()-1; i++){
            foundSmudge = checkIfSmudged(bitMap.get(i), bitMap.get(i + 1));
            if(Objects.equals(bitMap.get(i), bitMap.get(i + 1)) || foundSmudge){
                isMirrored = true;
                if(foundSmudge){
                    mirrorIndex = i+1;
                }
                int offset=1;
                while((i-offset) >= 0 && (i+offset)<(bitMap.size()-1)){
                    int left = bitMap.get(i-offset);
                    int right = bitMap.get(i+offset+1);
                    offset++;
                    if(left != right){
                        if(!foundSmudge && checkIfSmudged(left, right)){
                            foundSmudge=true;
                            mirrorIndex = i+1;
                        }
                        else{
                            isMirrored = false;
                            break;
                        }
                    }
                }
            }
            if(isMirrored && foundSmudge){
                break;
            }
        }
        if(isMirrored && foundSmudge){
            return mirrorIndex;
        }
        else{
            return null;
        }
    }

    private Integer checkIfMirrored(List<Integer> bitMap) {
        boolean isMirrored = false;
        Integer mirrorIndex = null;
        for(int i=0; i<bitMap.size()-1; i++){
            if(Objects.equals(bitMap.get(i), bitMap.get(i + 1))){
                isMirrored = true;
                mirrorIndex = i+1;
                int offset=1;
                while((i-offset) >= 0 && (i+offset)<(bitMap.size()-1)){
                    int left = bitMap.get(i-offset);
                    int right = bitMap.get(i+offset+1);
                    offset++;
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

    private boolean checkIfSmudged(int line1, int line2){
        int difference = Math.abs(line2-line1);
        return (difference != 0) && ((difference & (difference-1)) == 0);
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
}
