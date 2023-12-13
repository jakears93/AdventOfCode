package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Tests {
    private FileLoader fileLoader;
    private Day5 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day5();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day5A_sample(){
        List<String> input = fileLoader.readAsLines("sample5.txt");
        long result = runner.inPlaceMinimumLocation(input, false);
        assertEquals(35L, result);
    }

    @Test
    public void day5A_puzzle(){
        List<String> input = fileLoader.readAsLines("input5.txt");
        long result = runner.inPlaceMinimumLocation(input, false);
        assertEquals(993500720L, result);
    }

    @Test
    public void day5B_sample(){
        List<String> input = fileLoader.readAsLines("sample5.txt");
        long result = runner.inPlaceMinimumLocation(input, true);
        assertEquals(46L, result);
    }

    @Test
    public void day5B_puzzle(){
        //5 Min run time, need to optimize
        List<String> input = fileLoader.readAsLines("input5.txt");
        long result = runner.inPlaceMinimumLocation(input, true);
        assertEquals(4917124L, result);
    }
}
