package ca.arctechlabs.aoc.y2023.challenges;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day7 {
    //https://adventofcode.com/2023/day/7
    public long totalWinnings(List<String> input, boolean wildcardsEnabled){
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
    private enum HandType {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1),
        INVALID(0);

        private final int value;

        HandType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    private static class CamelCardHand {
        private final Map<Integer, CamelCard> hand;
        private long bid;
        private HandType handType;

        public CamelCardHand() {
            this.hand = new HashMap<>();
        }

        public void addToHand(Integer index, CamelCard value){
            this.hand.put(index, value);
        }

        public CamelCard getFromHand(Integer index){
            return this.hand.get(index);
        }

        public Map<Integer, CamelCard> getHand() {
            return hand;
        }

        public long getBid() {
            return bid;
        }

        public void setBid(long bid) {
            this.bid = bid;
        }

        public HandType getHandType() {
            return handType;
        }

        public void setHandType(HandType handType) {
            this.handType = handType;
        }
    }
    private enum CamelCard {
        C_2('2', 2),
        C_3('3', 3),
        C_4('4', 4),
        C_5('5', 5),
        C_6('6', 6),
        C_7('7', 7),
        C_8('8', 8),
        C_9('9', 9),
        C_T('T', 10),
        C_J('J', 11),
        C_Q('Q', 12),
        C_K('K', 13),
        C_A('A', 14),
        C_JOKER('W', 1);


        private final char name;
        private final int strength;
        CamelCard(char name, int strength) {
            this.name = name;
            this.strength = strength;
        }

        public static CamelCard fromName(char name) {
            for (CamelCard b : values()) {
                if (b.name == name) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected Camel Card name '" + name + "'");
        }

        public int getStrength() {
            return strength;
        }

        @Override
        public String toString() {
            return String.valueOf(name);
        }
    }

}
