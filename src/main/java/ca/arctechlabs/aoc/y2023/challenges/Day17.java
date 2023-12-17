package ca.arctechlabs.aoc.y2023.challenges;

import java.util.List;
import java.util.Objects;

public class Day17 {

    public int findLowestHeatLoss(List<String> input){
        return 0;
    }

    class Visit{
        private int consecutiveMoves;
        private Direction incomingDirection;
        private Coords location;

        public Visit() {
        }

        public Visit(int consecutiveMoves, Direction incomingDirection, Coords location) {
            this.consecutiveMoves = consecutiveMoves;
            this.incomingDirection = incomingDirection;
            this.location = location;
        }


    }

    enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    class Coords{
        private int x;
        private int y;

        public Coords() {
        }

        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coords coords = (Coords) o;
            return x == coords.x && y == coords.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
