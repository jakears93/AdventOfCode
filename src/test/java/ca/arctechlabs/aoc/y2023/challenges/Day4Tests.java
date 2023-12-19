package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Tests {
    private FileLoader fileLoader;
    private Day4 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day4();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day4A_sample(){
        List<String> input = fileLoader.readAsLines("sample4.txt");
        int result = runner.sumOfCardScore(input);
        assertEquals(13, result);
    }

    @Test
    public void day4A_puzzle(){
        List<String> input = fileLoader.readAsLines("input4.txt");
        int result = runner.sumOfCardScore(input);
        assertEquals(23847, result);
    }

    @Test
    public void day4B_sample(){
        List<String> input = fileLoader.readAsLines("sample4.txt");
        int result = runner.sumOfAllCardsAfterWinningCopies(input);
        assertEquals(30, result);
    }

    @Test
    public void day4B_puzzle(){
        List<String> input = fileLoader.readAsLines("input4.txt");
        int result = runner.sumOfAllCardsAfterWinningCopies(input);
        assertEquals(8570000, result);
    }
}
