package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Tests {
    private FileLoader fileLoader;
    private Day14 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day14();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day14A_sample(){
        List<String> input = fileLoader.readAsLines("sample14.txt");
        long result = runner.sumOfNorthLoad(input);
        assertEquals(136L, result);
    }

    @Test
    public void Day14A_puzzle(){
        List<String> input = fileLoader.readAsLines("input14.txt");
        long result = runner.sumOfNorthLoad(input);
        assertEquals(107951L, result);
    }

    @Test
    public void Day14B_sample(){
        List<String> input = fileLoader.readAsLines("sample14.txt");
        long result = runner.sumOfNorthLoadWithFullCycles(input, 1000000000);
        assertEquals(64L, result);
    }

    @Test
    public void Day14B_puzzle(){
        List<String> input = fileLoader.readAsLines("input14.txt");
        long result = runner.sumOfNorthLoadWithFullCycles(input, 1000000000);
        assertEquals(95736L, result);
    }
}
