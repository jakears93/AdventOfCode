package ca.arctechlabs.aoc.y2023.d4;

import ca.arctechlabs.aoc.y2023.d4.model.Card;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day4A {
    //https://adventofcode.com/2023/day/4


    public static void main(String[] args){
        System.out.println("Test Results: " + processFile("src/main/resources/2023/Day4/A/test.txt"));
        System.out.println("Puzzle Results: " + processFile("src/main/resources/2023/Day4/A/input.txt"));
    }

    public static Integer processFile(String fileName){
        File input = new File(fileName);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            while(reader.ready()){
                lines.add(reader.readLine());
            }
        } catch (IOException e) { e.printStackTrace(); }

        return lines.stream()
                .map(Day4A::populateCard)
                .mapToInt(Day4A::calculateWin)
                .sum();

    }

    private static int calculateWin(Card card){
        int score = 0;
        for(int number : card.getMyNumbers()){
            if(card.getWinningNumbers().contains(number)) score++;
        }
        return (int) Math.pow(2, score-1);
    }

    public static Card populateCard(String data){
        Card card = new Card();
        String cardId = data.split(":")[0].split("Card ")[1];
        card.setId(Integer.parseInt(cardId.trim()));

        String[] rawSets = data.split(":")[1].split("\\|");
        Set<Integer> myNumbers = new HashSet<>();
        for(String rawNumber: rawSets[0].split(" ")){
            try{
                myNumbers.add(Integer.parseInt(rawNumber.trim()));
            } catch (NumberFormatException ignored){}
        }
        card.setMyNumbers(myNumbers);

        Set<Integer> winningNumbers = new HashSet<>();
        for(String rawNumber: rawSets[1].split(" ")){
            try{
                winningNumbers.add(Integer.parseInt(rawNumber.trim()));
            } catch (NumberFormatException ignored){}
        }
        card.setWinningNumbers(winningNumbers);
        return card;
    }

}
