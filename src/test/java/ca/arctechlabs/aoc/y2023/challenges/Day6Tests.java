package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Tests {
    private FileLoader fileLoader;
    private Day6 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day6();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day6A_sample(){
        List<String> input = fileLoader.loadLines("sample6.txt");
        long result = runner.getMargin(input, 1);
        assertEquals(288L, result);
    }

    @Test
    public void day6A_puzzle(){
        List<String> input = fileLoader.loadLines("input6.txt");
        long result = runner.getMargin(input, 1);
        assertEquals(500346L, result);
    }

    @Test
    public void day6B_sample(){
        List<String> input = fileLoader.loadLines("sample6.txt");
        long result = runner.getMarginAsSingleRace(input, 1);
        assertEquals(71503L, result);
    }

    @Test
    public void day6B_puzzle(){
        List<String> input = fileLoader.loadLines("input6.txt");
        long result = runner.getMarginAsSingleRace(input, 1);
        assertEquals(42515755L, result);
    }
}
