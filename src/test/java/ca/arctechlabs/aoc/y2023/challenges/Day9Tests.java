package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Tests {
    private FileLoader fileLoader;
    private Day9 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day9();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day9A_sample(){
        List<String> input = fileLoader.loadLines("sample9.txt");
        long result = runner.sumOfExtrapolations(input, false);
        assertEquals(114L, result);
    }

    @Test
    public void day9A_puzzle(){
        List<String> input = fileLoader.loadLines("input9.txt");
        long result = runner.sumOfExtrapolations(input, false);
        assertEquals(1581679977L, result);
    }

    @Test
    public void day9B_sample(){
        List<String> input = fileLoader.loadLines("sample9.txt");
        long result = runner.sumOfExtrapolations(input, true);
        assertEquals(2L, result);
    }

    @Test
    public void day9B_puzzle(){
        List<String> input = fileLoader.loadLines("input9.txt");
        long result = runner.sumOfExtrapolations(input, true);
        assertEquals(889L, result);
    }
}
