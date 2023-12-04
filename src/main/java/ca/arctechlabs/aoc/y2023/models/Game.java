package ca.arctechlabs.aoc.y2023.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private int id;
    List<Map<String, Integer>> sets;

    Map<String, Integer> rules;

    public Game() {
        this.sets = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Map<String, Integer>> getSets() {
        return sets;
    }

    public void setSets(List<Map<String, Integer>> sets) {
        this.sets = sets;
    }

    public void addSet(Map<String, Integer> set){
        this.sets.add(set);
    }

    public Map<String, Integer> getRules() {
        return rules;
    }

    public void setRules(Map<String, Integer> rules) {
        this.rules = rules;
    }
}
