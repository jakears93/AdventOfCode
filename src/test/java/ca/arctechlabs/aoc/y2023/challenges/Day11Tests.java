package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Tests {
    private FileLoader fileLoader;
    private Day11 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day11();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day11A_sample(){
        List<String> input = fileLoader.loadLines("sample11.txt");
        long result = runner.sumOfShortestPaths(input, 2L);
        assertEquals(374L, result);
    }

    @Test
    public void Day11A_puzzle(){
        List<String> input = fileLoader.loadLines("input11.txt");
        long result = runner.sumOfShortestPaths(input, 2L);
        assertEquals(9609130L, result);
    }

    @Test
    public void Day11B_sample(){
        List<String> input = fileLoader.loadLines("sample11.txt");
        long result = runner.sumOfShortestPaths(input, 1000000L);
        assertEquals(82000210L, result);
    }

    @Test
    public void Day11B_puzzle(){
        List<String> input = fileLoader.loadLines("input11.txt");
        long result = runner.sumOfShortestPaths(input, 1000000L);
        assertEquals(702152204842L, result);
    }
}
