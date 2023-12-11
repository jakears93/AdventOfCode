package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Tests {
    private FileLoader fileLoader;
    private Day8 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day8();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day8A_sample(){
        List<String> input = fileLoader.loadLines("sample8.txt");
        long result = runner.stepsToTraverseMap(input, (name -> (name.endsWith("AAA"))), (name -> name.endsWith("ZZZ")));
        assertEquals(2L, result);
    }

    @Test
    public void day8A_sample2(){
        List<String> input = fileLoader.loadLines("sample8_2.txt");
        long result = runner.stepsToTraverseMap(input, (name -> (name.endsWith("AAA"))), (name -> name.endsWith("ZZZ")));
        assertEquals(6L, result);
    }

    @Test
    public void day8A_puzzle(){
        List<String> input = fileLoader.loadLines("input8.txt");
        long result = runner.stepsToTraverseMap(input, (name -> (name.endsWith("AAA"))), (name -> name.endsWith("ZZZ")));
        assertEquals(18023L, result);
    }

    @Test
    public void day8B_sample(){
        List<String> input = fileLoader.loadLines("sample8B.txt");
        long result = runner.stepsToTraverseMap(input, (name -> (name.endsWith("A"))), (name -> name.endsWith("Z")));
        assertEquals(6L, result);
    }

    @Test
    public void day8B_puzzle(){
        List<String> input = fileLoader.loadLines("input8.txt");
        long result = runner.stepsToTraverseMap(input, (name -> (name.endsWith("A"))), (name -> name.endsWith("Z")));
        assertEquals(14449445933179L, result);
    }
}
