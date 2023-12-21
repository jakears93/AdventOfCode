package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Tests {
    private FileLoader fileLoader;
    private Day21 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day21();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day21A_sample(){
        List<String> input = fileLoader.readAsLines("sample21.txt");
        long result = runner.numberOfReachableGardenPlots(input, 6, false);
        assertEquals(16L, result);
    }

    @Test
    public void Day21A_puzzle(){
        List<String> input = fileLoader.readAsLines("input21.txt");
        long result = runner.numberOfReachableGardenPlots(input, 64, false);
        assertEquals(3637L, result);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("ca.arctechlabs.aoc.y2023.challenges.Day21Tests#sampleBParams")
    public void Day21B_sample(TestPair testPair){
        List<String> input = fileLoader.readAsLines("sample21.txt");
        long result = runner.numberOfReachableGardenPlots(input, testPair.steps(), true);
        assertEquals(testPair.expected(), result);
    }

    @Disabled
    @Test
    public void Day21B_puzzle(){
        List<String> input = fileLoader.readAsLines("input21.txt");
        long result = runner.numberOfReachableGardenPlots(input, 26501365, true);
        assertEquals(0L, result);
    }

    private static Stream<TestPair> sampleBParams(){
        return Stream.of(
                new TestPair(6, 16L)
                , new TestPair(10, 50L)
                , new TestPair(50, 1594L)
                , new TestPair(100, 6536L)
                , new TestPair(500,167004L)
//                , new TestPair(1000, 668697)
//                , new TestPair(5000, 16733044)
                );
    }
    public record TestPair(int steps, long expected){}
}
