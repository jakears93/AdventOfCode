package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Tests {
    private FileLoader fileLoader;
    private Day12 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day12();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day12A_sample(){
        List<String> input = fileLoader.readAsLines("sample12.txt");
        long result = runner.totalArrangements(input);
        assertEquals(21L, result);
    }

    @Test
    public void Day12A_puzzle(){
        List<String> input = fileLoader.readAsLines("input12.txt");
        long result = runner.totalArrangements(input);
        assertEquals(6488L, result);
    }

    @Disabled
    @Test
    public void Day12B_sample(){
        List<String> input = fileLoader.readAsLines("sample12.txt");
        long result = runner.totalArrangements(input, 5);
        assertEquals(525152L, result);
    }

    @Disabled
    @Test
    public void Day12B_puzzle(){
        List<String> input = fileLoader.readAsLines("input12.txt");
        long result = runner.totalArrangements(input);
        assertEquals(0L, result);
    }
}
