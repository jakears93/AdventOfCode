package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Tests {
    private FileLoader fileLoader;
    private Day18 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day18();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day18A_sample(){
        List<String> input = fileLoader.readAsLines("sample18.txt");
        int result = runner.countTrenchArea(input);
        assertEquals(62, result);
    }

    @Test
    public void Day18A_puzzle(){
        List<String> input = fileLoader.readAsLines("input18.txt");
        int result = runner.countTrenchArea(input);
        assertEquals(31171, result);
    }

    @Test
    public void Day18B_sample(){
        List<String> input = fileLoader.readAsLines("sample18.txt");
        int result = runner.countTrenchArea(input);
        assertEquals(0, result);
    }

    @Test
    public void Day18B_puzzle(){
        List<String> input = fileLoader.readAsLines("input18.txt");
        int result = runner.countTrenchArea(input);
        assertEquals(0, result);
    }
}
