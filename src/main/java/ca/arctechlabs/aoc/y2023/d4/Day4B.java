package ca.arctechlabs.aoc.y2023.d4;

import ca.arctechlabs.aoc.utilities.FileLoader;
import ca.arctechlabs.aoc.y2023.models.Card;

import java.util.*;

public class Day4B {
    //https://adventofcode.com/2023/day/4#part2


    public static void main(String[] args){
        System.out.println("Test Results: " + processFile("src/main/resources/2023/Day4/B/test.txt"));
        System.out.println("Puzzle Results: " + processFile("src/main/resources/2023/Day4/B/input.txt"));
    }

    public static Integer processFile(String fileName){
        List<String> lines = FileLoader.loadLines(fileName);

        List<Card> cards = lines.stream().map(Day4B::populateCard).toList();
        Map<Integer, Integer> numOfCardsMap = new HashMap<>();
        for(Card card : cards){
            numOfCardsMap.put(card.getId(), 1);
        }

        for(int i=0; i<cards.size(); i++){
            Card card = cards.get(i);
            int score = calculateWin(card);
            for(int k=1; k<=score; k++){
                numOfCardsMap.put(card.getId()+k, numOfCardsMap.get(card.getId()+k) + numOfCardsMap.get(card.getId()));
            }
        }


        return numOfCardsMap.values()
                .stream()
                .mapToInt(value -> value)
                .sum();

    }

    private static int calculateWin(Card card){
        int score = 0;
        for(int number : card.getMyNumbers()){
            if(card.getWinningNumbers().contains(number)) score++;
        }
        return score;
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
