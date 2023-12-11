package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Tests {
    private FileLoader fileLoader;
    private Day7 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day7();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day7A_sample(){
        List<String> input = fileLoader.loadLines("sample7.txt");
        long result = runner.totalWinnings(input, false);
        assertEquals(6440L, result);
    }

    @Test
    public void day7A_puzzle(){
        List<String> input = fileLoader.loadLines("input7.txt");
        long result = runner.totalWinnings(input, false);
        assertEquals(254024898L, result);
    }

    @Test
    public void day7B_sample(){
        List<String> input = fileLoader.loadLines("sample7.txt");
        long result = runner.totalWinnings(input, true);
        assertEquals(5905L, result);
    }

    @Test
    public void day7B_puzzle(){
        List<String> input = fileLoader.loadLines("input7.txt");
        long result = runner.totalWinnings(input, true);
        assertEquals(254115617L, result);
    }
}
