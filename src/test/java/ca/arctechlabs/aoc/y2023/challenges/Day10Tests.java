package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {
    private FileLoader fileLoader;
    private Day10 runner;

    private int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day10();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day10A_sample(){
        List<String> input = fileLoader.loadLines("sample10.txt");
        this.runner.populateMaze(input);
        long result = runner.lengthOfLoop()/2;
        assertEquals(8L, result);
    }

    @Test
    public void Day10A_puzzle(){
        List<String> input = fileLoader.loadLines("input10.txt");
        this.runner.populateMaze(input);
        long result = runner.lengthOfLoop()/2;
        assertEquals(0L, result);
    }

    @Test
    public void Day10B_sample(){
        List<String> input = fileLoader.loadLines("sample10.txt");
        this.runner.populateMaze(input);
        long result = runner.lengthOfLoop();
        assertEquals(0L, result);
    }

    @Test
    public void Day10B_puzzle(){
        List<String> input = fileLoader.loadLines("input10.txt");
        this.runner.populateMaze(input);
        long result = runner.lengthOfLoop();
        assertEquals(0L, result);
    }
}
