package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Tests {
    private FileLoader fileLoader;
    private Day17 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day17();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day17A_sample(){
        List<String> input = fileLoader.readAsLines("sample17.txt");
        int result = runner.findLowestHeatLoss(input);
        assertEquals(102, result);
    }

    @Test
    public void Day17A_puzzle(){
        List<String> input = fileLoader.readAsLines("input17.txt");
        int result = runner.findLowestHeatLoss(input);
        assertEquals(0, result);
    }

    @Test
    public void Day17B_sample(){
        List<String> input = fileLoader.readAsLines("sample17.txt");
        int result = runner.findLowestHeatLoss(input);
        assertEquals(0, result);
    }

    @Test
    public void Day17B_puzzle(){
        List<String> input = fileLoader.readAsLines("input17.txt");
        int result = runner.findLowestHeatLoss(input);
        assertEquals(0, result);
    }
}
