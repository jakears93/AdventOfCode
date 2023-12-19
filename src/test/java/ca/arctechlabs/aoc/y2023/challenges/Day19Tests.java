package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19Tests {
    private FileLoader fileLoader;
    private Day19 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day19();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day19A_sample(){
        List<String> input = fileLoader.readAsLines("sample19.txt");
        long result = runner.sumOfAcceptedParts(input);
        assertEquals(19114L, result);
    }

    @Test
    public void Day19A_puzzle(){
        List<String> input = fileLoader.readAsLines("input19.txt");
        long result = runner.sumOfAcceptedParts(input);
        assertEquals(374873L, result);
    }

    @Test
    public void Day19B_sample(){
        List<String> input = fileLoader.readAsLines("sample19.txt");
        long result = runner.sumOfAcceptedParts(input);
        assertEquals(0L, result);
    }

    @Test
    public void Day19B_puzzle(){
        List<String> input = fileLoader.readAsLines("input19.txt");
        long result = runner.sumOfAcceptedParts(input);
        assertEquals(0L, result);
    }
}
