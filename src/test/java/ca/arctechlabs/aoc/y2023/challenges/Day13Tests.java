package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Tests {
    private FileLoader fileLoader;
    private Day13 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day13();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day13A_sample(){
        List<String> input = fileLoader.readAsLines("sample13.txt");
        long result = runner.findMirrorNumberSum(input);
        assertEquals(405L, result);
    }

    @Test
    public void Day13A_puzzle(){
        List<String> input = fileLoader.readAsLines("input13.txt");
        long result = runner.findMirrorNumberSum(input);
        assertEquals(32371L, result);
    }

    @Test
    public void Day13B_sample(){
        List<String> input = fileLoader.readAsLines("sample13.txt");
        long result = runner.findMirrorNumberSum(input);
        assertEquals(0L, result);
    }

    @Test
    public void Day13B_puzzle(){
        List<String> input = fileLoader.readAsLines("input13.txt");
        long result = runner.findMirrorNumberSum(input);
        assertEquals(0L, result);
    }
}
