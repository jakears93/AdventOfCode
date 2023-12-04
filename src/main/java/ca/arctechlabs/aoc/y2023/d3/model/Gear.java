package ca.arctechlabs.aoc.y2023.d3.model;

public class Gear {
    private int g1;
    private int g2;

    public int getG1() {
        return g1;
    }

    public void setG1(int g1) {
        this.g1 = g1;
    }

    public int getG2() {
        return g2;
    }

    public void setG2(int g2) {
        this.g2 = g2;
    }

    public int getRatio(){
        return g1*g2;
    }

    @Override
    public String toString() {
        return "Gear{" +
                "g1=" + g1 +
                ", g2=" + g2 +
                '}';
    }
}
