package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Tests {
    private FileLoader fileLoader;
    private Day16 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day16();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day16A_sample(){
        List<String> input = fileLoader.readAsLines("sample16.txt");
        this.runner.setInput(input);
        long result = runner.getNumberOfIlluminatedTiles();
        assertEquals(46L, result);
    }

    @Test
    public void Day16A_puzzle(){
        List<String> input = fileLoader.readAsLines("input16.txt");
        this.runner.setInput(input);
        long result = runner.getNumberOfIlluminatedTiles();
        assertEquals(6921L, result);
    }

    @Test
    public void Day16B_sample(){
        List<String> input = fileLoader.readAsLines("sample16.txt");
        this.runner.setInput(input);
        long result = runner.getMaxNumberOfIlluminatedTiles();
        assertEquals(51L, result);
    }

    @Test
    public void Day16B_puzzle(){
        List<String> input = fileLoader.readAsLines("input16.txt");
        this.runner.setInput(input);
        long result = runner.getMaxNumberOfIlluminatedTiles();
        assertEquals(7594L, result);
    }
}
