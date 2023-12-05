package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.Card;

import java.util.*;

public class Day4 {
    //https://adventofcode.com/2023/day/4

    public Integer sumOfAllCardsAfterWinningCopies(List<String> input){
        List<Card> cards = input.stream().map(this::populateCard).toList();
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

    public Integer sumOfCardScore(List<String> input){
        return input.stream()
                .map(this::populateCard)
                .mapToInt(this::calculateWin)
                .map(value -> (int)Math.pow(2, value-1))
                .sum();
    }

    private int calculateWin(Card card){
        int score = 0;
        for(int number : card.getMyNumbers()){
            if(card.getWinningNumbers().contains(number)) score++;
        }
        return score;
    }

    public Card populateCard(String data){
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
