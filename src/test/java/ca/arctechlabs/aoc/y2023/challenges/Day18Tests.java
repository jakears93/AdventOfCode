package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.common.utilities.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Tests {
    private FileLoader fileLoader;
    private Day18 runner;

    private static final int AOC_YEAR = 2023;

    @BeforeEach
    void setup(){
        this.runner = new Day18();
        this.fileLoader = new FileLoader(AOC_YEAR);
    }

    @Test
    public void Day18A_sample(){
        List<String> input = fileLoader.readAsLines("sample18.txt");
        BigInteger result = runner.countTrenchArea(input);
        assertEquals(new BigInteger("62"), result);
    }

    @Test
    public void Day18A_sampleCustom(){
        List<String> input = fileLoader.readAsLines("sample18A_2.txt");
        BigInteger result = runner.countTrenchArea(input);
        assertEquals(new BigInteger("36"), result);
    }

    @Test
    public void Day18A_puzzle(){
        List<String> input = fileLoader.readAsLines("input18.txt");
        BigInteger result = runner.countTrenchArea(input);
        assertEquals(new BigInteger("31171"), result);
    }

    @Test
    public void Day18B_sample(){
        List<String> input = fileLoader.readAsLines("sample18.txt");
        BigInteger result = runner.countTrenchAreaColourSwap(input);
        assertEquals(new BigInteger("952408144115"), result);
    }

    @Test
    public void Day18B_puzzle(){
        List<String> input = fileLoader.readAsLines("input18.txt");
        BigInteger result = runner.countTrenchAreaColourSwap(input);
        assertEquals(new BigInteger("131431655002266"), result);
    }
}
