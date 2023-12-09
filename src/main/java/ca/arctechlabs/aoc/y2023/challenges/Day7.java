package ca.arctechlabs.aoc.y2023.challenges;

import ca.arctechlabs.aoc.y2023.models.CamelCard;
import ca.arctechlabs.aoc.y2023.models.CamelCardHand;
import ca.arctechlabs.aoc.y2023.models.HandType;

import java.util.*;
import java.util.stream.IntStream;

public class Day7 {
    //https://adventofcode.com/2023/day/7
    public long totalWinnings(List<String> input, boolean wildcardsEnabled){
        HandComparator comparator = new HandComparator();

        List<CamelCardHand> sortedHands = input.stream()
                .map(line -> this.parseHand(line, wildcardsEnabled))
                .toList()
                .stream()
                .sorted(new HandComparator())
                .toList();

        return IntStream.range(0, sortedHands.size())
                .mapToLong(handIndex -> sortedHands.get(handIndex).getBid() * (handIndex+1))
                .sum();
    }
    private CamelCardHand parseHand(String line, boolean wildcardsEnabled){
        String[] components = line.split(" ");
        CamelCardHand hand = new CamelCardHand();
        hand.setBid(Long.parseLong(components[1]));
        char[] cards = components[0].toCharArray();
        for(int i=0; i<cards.length; i++){
            char cardName = cards[i];
            if(cardName == 'J' && wildcardsEnabled){
                cardName = 'W';
            }
            hand.addToHand(i, CamelCard.fromName(cardName));
        }
        hand.setHandType(calculateScore(hand));
        return hand;
    }

    private HandType calculateScore(CamelCardHand hand){
        int wildcardCount = 0;

        Map<CamelCard, Integer> uniqueCards = new HashMap<>();
        for(CamelCard card : hand.getHand().values()){
            uniqueCards.put(card, uniqueCards.getOrDefault(card, 0) + 1);
        }

        if(uniqueCards.get(CamelCard.C_JOKER) != null){
            wildcardCount = uniqueCards.get(CamelCard.C_JOKER);
            uniqueCards.remove(CamelCard.C_JOKER);
        }

        if(uniqueCards.size() == 5){
            return HandType.HIGH_CARD;
        }

        else if(uniqueCards.size() == 4){
            return HandType.ONE_PAIR;
        }

        else if(uniqueCards.size() == 3){
            for(Integer count : uniqueCards.values()){
                if(count+wildcardCount == 3){
                    return HandType.THREE_OF_A_KIND;
                }
            }
            return HandType.TWO_PAIR;
        }
        else if(uniqueCards.size() == 2){
            for(Integer count : uniqueCards.values()){
                if(count+wildcardCount == 4){
                    return HandType.FOUR_OF_A_KIND;
                }
            }
            return HandType.FULL_HOUSE;
        }
        else if(uniqueCards.size() == 1 || wildcardCount == 5){
            return HandType.FIVE_OF_A_KIND;
        }
        return HandType.INVALID;
    }

    private static class HandComparator implements Comparator<CamelCardHand>{
        @Override
        public int compare(CamelCardHand h1, CamelCardHand h2) {
            int h1Score = h1.getHandType().getValue();
            int h2Score = h2.getHandType().getValue();
            if(h1Score > h2Score){
                return 1;
            }
            else if(h1Score < h2Score){
                return -1;
            }
            for(int i=0; i<h1.getHand().size(); i++){
                if(h1.getFromHand(i).getStrength() > h2.getFromHand(i).getStrength()){
                    return 1;
                }
                else if(h1.getFromHand(i).getStrength() < h2.getFromHand(i).getStrength()){
                    return -1;
                }
            }
            return 0;
        }
    }
}
