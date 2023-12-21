package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Tests {
    private FileLoader fileLoader;
    private Day21 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day21();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day21A_sample(){
        List<String> input = fileLoader.readAsLines("sample21.txt");
        int result = runner.numberOfReachableGardenPlots(input, 6);
        assertEquals(16, result);
    }

    @Test
    public void Day21A_puzzle(){
        List<String> input = fileLoader.readAsLines("input21.txt");
        int result = runner.numberOfReachableGardenPlots(input, 64);
        assertEquals(3637, result);
    }

    @Test
    public void Day21B_sample(){
        List<String> input = fileLoader.readAsLines("sample21.txt");
        int result = runner.numberOfReachableGardenPlots(input, 64);
        assertEquals(0, result);
    }

    @Test
    public void Day21B_puzzle(){
        List<String> input = fileLoader.readAsLines("input21.txt");
        int result = runner.numberOfReachableGardenPlots(input, 64);
        assertEquals(0, result);
    }
}
