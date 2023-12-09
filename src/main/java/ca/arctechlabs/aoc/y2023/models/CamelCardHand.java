package ca.arctechlabs.aoc.y2023.models;

import java.util.HashMap;
import java.util.Map;

public class CamelCardHand {
    private Map<Integer, CamelCard> hand;
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
