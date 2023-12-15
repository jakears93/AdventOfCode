package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Tests {
    private FileLoader fileLoader;
    private Day15 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day15();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day15A_sample(){
        String[] input = fileLoader.readAsArray("sample15.txt", ",");
        int result = runner.calculateSumOfHashes(input);
        assertEquals(1320, result);
    }

    @Test
    public void Day15A_puzzle(){
        String[] input = fileLoader.readAsArray("input15.txt", ",");
        int result = runner.calculateSumOfHashes(input);
        assertEquals(521341, result);
    }

    @Test
    public void Day15B_sample(){
        String[] input = fileLoader.readAsArray("sample15.txt", ",");
        int result = runner.focusingPower(input);
        assertEquals(145, result);
    }

    @Test
    public void Day15B_puzzle(){
        String[] input = fileLoader.readAsArray("input15.txt", ",");
        int result = runner.focusingPower(input);
        assertEquals(252782, result);
    }
}
