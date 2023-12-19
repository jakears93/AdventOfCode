package ca.arctechlabs.aoc.y2023.challenges;

import java.util.*;

public class Day4 {
    //https://adventofcode.com/2023/day/4

    public Integer sumOfAllCardsAfterWinningCopies(List<String> input){
        List<LotteryCard> lotteryCards = input.stream().map(this::populateCard).toList();
        Map<Integer, Integer> numOfCardsMap = new HashMap<>();
        for(LotteryCard lotteryCard : lotteryCards){
            numOfCardsMap.put(lotteryCard.getId(), 1);
        }

        for (LotteryCard lotteryCard : lotteryCards) {
            int score = calculateWin(lotteryCard);
            for (int k = 1; k <= score; k++) {
                numOfCardsMap.put(lotteryCard.getId() + k, numOfCardsMap.get(lotteryCard.getId() + k) + numOfCardsMap.get(lotteryCard.getId()));
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

    private LotteryCard populateCard(String data){
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

    private static class LotteryCard {
        private int id;
        private Set<Integer> myNumbers;
        private Set<Integer> winningNumbers;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Set<Integer> getMyNumbers() {
            return myNumbers;
        }

        public void setMyNumbers(Set<Integer> myNumbers) {
            this.myNumbers = myNumbers;
        }

        public Set<Integer> getWinningNumbers() {
            return winningNumbers;
        }

        public void setWinningNumbers(Set<Integer> winningNumbers) {
            this.winningNumbers = winningNumbers;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "id=" + id +
                    ", myNumbers=" + myNumbers +
                    ", winningNumbers=" + winningNumbers +
                    '}';
        }
    }
}
