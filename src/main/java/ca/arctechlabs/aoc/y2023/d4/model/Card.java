package ca.arctechlabs.aoc.y2023.d4.model;

import java.util.Set;

public class Card {
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
