package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Tests {
    private FileLoader fileLoader;
    private Day1 runner;

    private int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day1();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day1A_sample(){
        List<String> input = fileLoader.loadLines("sample1A.txt");
        int result = runner.sumOfCalibrationNumbers(input, false);
        assertEquals(142, result);
    }

    @Test
    public void day1A_puzzle(){
        List<String> input = fileLoader.loadLines("input1.txt");
        int result = runner.sumOfCalibrationNumbers(input, false);
        assertEquals(54708, result);
    }

    @Test
    public void day1B_sample(){
        List<String> input = fileLoader.loadLines("sample1B.txt");
        int result = runner.sumOfCalibrationNumbers(input, true);
        assertEquals(281, result);
    }

    @Test
    public void day1B_puzzle(){
        List<String> input = fileLoader.loadLines("input1.txt");
        int result = runner.sumOfCalibrationNumbers(input, true);
        assertEquals(54087, result);
    }

}
