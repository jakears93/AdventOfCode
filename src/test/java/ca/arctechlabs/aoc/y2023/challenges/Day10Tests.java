package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {
    private FileLoader fileLoader;
    private Day10 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day10();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day10A_sample(){
        List<String> input = fileLoader.readAsLines("sample10.txt");
        this.runner.populateMaze(input);
        long result = runner.lengthOfLoop()/2;
        assertEquals(8L, result);
    }

    @Test
    public void Day10A_puzzle(){
        List<String> input = fileLoader.readAsLines("input10.txt");
        this.runner.populateMaze(input);
        long result = runner.lengthOfLoop()/2;
        assertEquals(6768L, result);
    }

    @Test
    public void Day10B_sample(){
        List<String> input = fileLoader.readAsLines("sample10B.txt");
        this.runner.populateMaze(input);
        BigInteger result = runner.areaInsideLoop();
        assertEquals(new BigInteger("4"), result);
    }

    @Test
    public void Day10B_sample2(){
        List<String> input = fileLoader.readAsLines("sample10B_2.txt");
        this.runner.populateMaze(input);
        BigInteger result = runner.areaInsideLoop();
        assertEquals(new BigInteger("8"), result);
    }

    @Disabled
    @Test
    public void Day10B_sample3(){
        List<String> input = fileLoader.readAsLines("sample10B_3.txt");
        this.runner.populateMaze(input);
        BigInteger result = runner.areaInsideLoop();
        assertEquals(new BigInteger("10"), result);
    }

    @Test
    public void Day10B_puzzle(){
        List<String> input = fileLoader.readAsLines("input10.txt");
        this.runner.populateMaze(input);
        BigInteger result = runner.areaInsideLoop();
        assertEquals(new BigInteger("351"), result);
    }
}