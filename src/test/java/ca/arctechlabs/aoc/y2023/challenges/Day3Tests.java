package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Tests {
    private FileLoader fileLoader;
    private Day3 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day3();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day3A_sample(){
        List<String> input = fileLoader.readAsLines("sample3.txt");
        int result = runner.sumOfPartNumbers(input);
        assertEquals(4361, result);
    }

    @Test
    public void day3A_puzzle(){
        List<String> input = fileLoader.readAsLines("input3.txt");
        int result = runner.sumOfPartNumbers(input);
        assertEquals(530849, result);
    }

    @Test
    public void day3B_sample(){
        List<String> input = fileLoader.readAsLines("sample3.txt");
        int result = runner.sumOfGearRatios(input);
        assertEquals(467835, result);
    }

    @Test
    public void day3B_puzzle(){
        List<String> input = fileLoader.readAsLines("input3.txt");
        int result = runner.sumOfGearRatios(input);
        assertEquals(84900879, result);
    }
}
