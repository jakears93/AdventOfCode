package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Tests {
    private FileLoader fileLoader;
    private Day20 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day20();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day20A_sample(){
        List<String> input = fileLoader.readAsLines("sample20.txt");
        long result = runner.productOfDifferentSignals(input, 1000);
        assertEquals(32000000L, result);
    }

    @Test
    public void Day20A_2_sample(){
        List<String> input = fileLoader.readAsLines("sample20A_2.txt");
        long result = runner.productOfDifferentSignals(input, 1000);
        assertEquals(11687500L, result);
    }

    @Test
    public void Day20A_puzzle(){
        List<String> input = fileLoader.readAsLines("input20.txt");
        long result = runner.productOfDifferentSignals(input, 1000);
        assertEquals(886347020L, result);
    }

    @Test
    public void Day20B_puzzle(){
        List<String> input = fileLoader.readAsLines("input20.txt");
        long result = runner.buttonPressesUntilGoal(input, "rx", "LOW");
        assertEquals(233283622908263L, result);
    }
}
