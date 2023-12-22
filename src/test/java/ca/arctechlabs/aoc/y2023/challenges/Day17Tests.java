package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        long result = runner.lowestHeatLoss(input);
        assertEquals(102L, result);
    }

    @Test
    public void Day17A_puzzle(){
        List<String> input = fileLoader.readAsLines("input17.txt");
        long result = runner.lowestHeatLoss(input);
        assertEquals(0L, result);
    }

    @Disabled
    @Test
    public void Day17B_sample(){
        List<String> input = fileLoader.readAsLines("sample17.txt");
        long result = runner.lowestHeatLoss(input);
        assertEquals(0L, result);
    }

    @Disabled
    @Test
    public void Day17B_puzzle(){
        List<String> input = fileLoader.readAsLines("input17.txt");
        long result = runner.lowestHeatLoss(input);
        assertEquals(0L, result);
    }
}
