package ca.arctechlabs.aoc.y2023.d2;

import ca.arctechlabs.aoc.utilities.FileLoader;
import ca.arctechlabs.aoc.y2023.d2.model.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2A {
    //https://adventofcode.com/2023/day/2

    private static Map<String, Integer> testRules;
    private static Map<String, Integer> inputRules;


    public static void main(String[] args){
        setup();
        System.out.println("Test Results: " + processFile("src/main/resources/2023/Day2/A/test.txt", testRules));
        System.out.println("Puzzle Results: " + processFile("src/main/resources/2023/Day2/A/input.txt", inputRules));
    }

    public static void setup(){
        testRules = new HashMap<>();
        testRules.put("red", 12);
        testRules.put("green", 13);
        testRules.put("blue", 14);

        inputRules = new HashMap<>();
        inputRules.put("red", 12);
        inputRules.put("green", 13);
        inputRules.put("blue", 14);
    }

    public static Integer processFile(String fileName, Map<String, Integer> rules){
        List<String> lines = FileLoader.loadLines(fileName);

        return lines.stream()
                .map(line -> extractGame(line, rules))
                .filter(Day2A::validateGame)
                .mapToInt(Game::getId)
                .sum();
    }

    public static boolean validateGame(Game game){
        boolean valid = true;
        for(Map<String, Integer> set : game.getSets()){
            for(String key : set.keySet()){
                if(set.get(key) > game.getRules().get(key)) valid = false;
            }
        }
        return valid;
    }
    public static Game extractGame(String data, Map<String, Integer> rules){
        Game game = new Game();
        game.setRules(rules);
        String gameId = data.split(":")[0].split("Game ")[1];
        game.setId(Integer.parseInt(gameId));

        String[] rawSets = data.split(":")[1].split(";");
        for(String set : rawSets){
            Map<String, Integer> gameSet = new HashMap<>();
            for(String rawColours: set.split(",")){
                String redString = rawColours.split(" red")[0];
                String greenString = rawColours.split(" green")[0];
                String blueString = rawColours.split(" blue")[0];
                if(!redString.equals(rawColours)){
                    gameSet.put("red", Integer.parseInt(redString.trim()));
                }
                if(!greenString.equals(rawColours)){
                    gameSet.put("green", Integer.parseInt(greenString.trim()));
                }
                if(!blueString.equals(rawColours)){
                    gameSet.put("blue", Integer.parseInt(blueString.trim()));
                }
            }
            game.addSet(gameSet);
        }
        return game;
    }

}
