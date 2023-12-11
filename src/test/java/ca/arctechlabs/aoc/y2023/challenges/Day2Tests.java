package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Tests {
    private FileLoader fileLoader;
    private Day2 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day2();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void day2A_sample(){
        Map<String, Integer> rules = createRules(12, 13, 14);
        List<String> input = fileLoader.loadLines("sample2.txt");
        int result = runner.countValidGames(input, rules);
        assertEquals(8, result);
    }

    @Test
    public void day2A_puzzle(){
        Map<String, Integer> rules = createRules(12, 13, 14);
        List<String> input = fileLoader.loadLines("input2.txt");
        int result = runner.countValidGames(input, rules);
        assertEquals(2512, result);
    }

    @Test
    public void day2B_sample(){
        List<String> input = fileLoader.loadLines("sample2B.txt");
        int result = runner.sumOfPowerOfSets(input);
        assertEquals(2286, result);
    }

    @Test
    public void day2B_puzzle(){
        List<String> input = fileLoader.loadLines("input2.txt");
        int result = runner.sumOfPowerOfSets(input);
        assertEquals(67335, result);
    }

    private Map<String, Integer> createRules(int red, int green, int blue){
        Map<String, Integer> rules = new HashMap<>();
        rules.put("red", red);
        rules.put("green", green);
        rules.put("blue", blue);
        return rules;
    }

}
