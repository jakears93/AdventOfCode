package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {
    //https://adventofcode.com/2023/day/2

    public Integer countValidGames(List<String> input, Map<String, Integer> rules){
        return input.stream()
                .map(line -> extractGame(line, rules))
                .filter(this::validateGame)
                .mapToInt(Game::getId)
                .sum();
    }

    public Integer sumOfPowerOfSets(List<String> input){
        return input.stream()
                .map(line -> extractGame(line, null))
                .mapToInt(this::getPowerOfMinimumBalls)
                .sum();
    }

    private boolean validateGame(Game game){
        boolean valid = true;
        for(Map<String, Integer> set : game.getSets()){
            for(String key : set.keySet()){
                if(set.get(key) > game.getRules().get(key)) valid = false;
            }
        }
        return valid;
    }

    private int getPowerOfMinimumBalls(Game game){
        int red = 0;
        int blue = 0;
        int green = 0;
        for(Map<String, Integer> set : game.getSets()){
            if(set.get("blue") != null && set.get("blue") > blue) blue = set.get("blue");
            if(set.get("red") != null && set.get("red") > red) red = set.get("red");
            if(set.get("green") != null && set.get("green") > green) green = set.get("green");
        }
        return red*green*blue;
    }
    private Game extractGame(String data, Map<String, Integer> rules){
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
