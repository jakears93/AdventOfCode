package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.LotteryCard;

import java.util.*;

public class Day4 {
    //https://adventofcode.com/2023/day/4

    public Integer sumOfAllCardsAfterWinningCopies(List<String> input){
        List<LotteryCard> lotteryCards = input.stream().map(this::populateCard).toList();
        Map<Integer, Integer> numOfCardsMap = new HashMap<>();
        for(LotteryCard lotteryCard : lotteryCards){
            numOfCardsMap.put(lotteryCard.getId(), 1);
        }

        for(int i = 0; i< lotteryCards.size(); i++){
            LotteryCard lotteryCard = lotteryCards.get(i);
            int score = calculateWin(lotteryCard);
            for(int k=1; k<=score; k++){
                numOfCardsMap.put(lotteryCard.getId()+k, numOfCardsMap.get(lotteryCard.getId()+k) + numOfCardsMap.get(lotteryCard.getId()));
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

    private int calculateWin(LotteryCard lotteryCard){
        int score = 0;
        for(int number : lotteryCard.getMyNumbers()){
            if(lotteryCard.getWinningNumbers().contains(number)) score++;
        }
        return score;
    }

    public LotteryCard populateCard(String data){
        LotteryCard lotteryCard = new LotteryCard();
        String cardId = data.split(":")[0].split("Card ")[1];
        lotteryCard.setId(Integer.parseInt(cardId.trim()));

        String[] rawSets = data.split(":")[1].split("\\|");
        Set<Integer> myNumbers = new HashSet<>();
        for(String rawNumber: rawSets[0].split(" ")){
            try{
                myNumbers.add(Integer.parseInt(rawNumber.trim()));
            } catch (NumberFormatException ignored){}
        }
        lotteryCard.setMyNumbers(myNumbers);

        Set<Integer> winningNumbers = new HashSet<>();
        for(String rawNumber: rawSets[1].split(" ")){
            try{
                winningNumbers.add(Integer.parseInt(rawNumber.trim()));
            } catch (NumberFormatException ignored){}
        }
        lotteryCard.setWinningNumbers(winningNumbers);
        return lotteryCard;
    }

}
