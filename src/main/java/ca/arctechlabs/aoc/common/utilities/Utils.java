package ca.arctechlabs.aoc.common.utilities;

import ca.arctechlabs.aoc.common.models.Coordinates;
import ca.arctechlabs.aoc.common.models.DijkstraNode;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Utils {
    public static long calculateLCM(List<Long> numbersList){
        Map<Long, Long> primeFactors = new HashMap<>();

        numbersList.stream()
                .map(Utils::calculatePrimeFactors)
                .forEach(map -> map.forEach((k,v) -> primeFactors.merge(k, v, Long::max)));

        AtomicLong lcm = new AtomicLong(1);
        primeFactors.forEach((k,v) -> {
            lcm.set((long) (lcm.get() * Math.pow(k, v)));
        });

        return lcm.get();
    }

    public static Map<Long, Long> calculatePrimeFactors(Long number){
        Long current = number;
        Map<Long, Long> primeFactorCounts = new HashMap<>();
        Long currentPrime = 2L;
        while(current != 1){
            long leftover;
            long power = 0;
            do{
                leftover = current%currentPrime;
                if(leftover == 0L){
                    power++;
                    current = current/currentPrime;
                }
            }while (leftover == 0L);
            primeFactorCounts.put(currentPrime, power);
            currentPrime = calculateNextPrime(currentPrime);
        }
        return primeFactorCounts;
    }

    public static Long calculateNextPrime(long number){
        if(number <= 1){
            return 2L;
        }
        long nextPrime = number;
        boolean foundPrime = false;
        while(!foundPrime){
            nextPrime++;
            if(isPrime(nextPrime)){
                foundPrime=true;
            }
        }
        return nextPrime;
    }

    public static boolean isPrime(long number){
        if(number <= 1) return false;
        if(number <= 3) return true;
        if(number%2 == 0 || number%3 == 0) return false;

        for (long i = 5; i * i <= number; i = i + 6)
            if (number % i == 0 || number % (i + 2) == 0)
                return false;

        return true;
    }

    public static long combination(long n, long r){
        long nF = factorial(n);
        long rF = factorial(r);
        long nrF = factorial(n-r);
        return nF/(rF*nrF);
    }

    public static long factorial(long number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }

    public static BigInteger calculateAreaFromVertices(List<Coordinates> vertices){
        if(vertices.isEmpty()) return BigInteger.ZERO;

        BigInteger pSum = new BigInteger(String.valueOf(0L));
        BigInteger nSum = new BigInteger(String.valueOf(0L));
        BigInteger x;
        BigInteger y;
        BigInteger product;
        for(int i=0; i<vertices.size()-1; i++){
            x = BigInteger.valueOf(vertices.get(i).getX());
            y = BigInteger.valueOf(vertices.get(i+1).getY());
            product = x.multiply(y);
            pSum = pSum.add(product);
        }
        x = BigInteger.valueOf(vertices.get(vertices.size()-1).getX());
        y = BigInteger.valueOf(vertices.get(0).getY());
        product = x.multiply(y);
        pSum = pSum.add(product);

        for(int i=0; i<vertices.size()-1; i++){
            x = BigInteger.valueOf(vertices.get(i+1).getX());
            y = BigInteger.valueOf(vertices.get(i).getY());
            product = x.multiply(y);
            nSum = nSum.add(product);
        }
        x = BigInteger.valueOf(vertices.get(0).getX());
        y = BigInteger.valueOf(vertices.get(vertices.size()-1).getY());
        product = x.multiply(y);
        nSum = nSum.add(product);

        BigInteger abs = pSum.subtract(nSum).abs();
        return abs.divide(BigInteger.valueOf(2));
    }

    public static <T> List<DijkstraNode<T>> dijkstraDistancesFromStart(T start, List<DijkstraNode<T>> allNodes){
        //Find Start Node
        DijkstraNode<T> startNode = allNodes.stream().filter(n -> n.getValue().equals(start)).findFirst().orElseThrow();
        return dijkstraDistancesFromStart(startNode, allNodes);
    }
    public static <T> List<DijkstraNode<T>> dijkstraDistancesFromStart(DijkstraNode<T> start, List<DijkstraNode<T>> allNodes){
        //Set startNode to 0 distance
        start.setShortestDistanceFromStart(0);

        //Created unvisitedNodeList
        List<DijkstraNode<T>> unvisited = new ArrayList<>(allNodes);
        List<DijkstraNode<T>> visited = new ArrayList<>();

        DijkstraNode<T> current;
        while(!unvisited.isEmpty()){
            current = unvisited.stream().min(Comparator.comparingInt(DijkstraNode::getShortestDistanceFromStart)).orElseThrow();
            unvisited.remove(current);
            visited.add(current);

            Map<?,Integer> neighbours = current.getDistanceToNeighbours();
            for(Map.Entry<?, Integer> entry : neighbours.entrySet()){
                if(visited.stream().anyMatch(n -> n.equals(entry.getKey()))){
                    continue;
                }
                DijkstraNode<T> neighbour = unvisited.stream().filter(n -> n.equals(entry.getKey())).findFirst().orElseThrow();

                int newDistance = current.getShortestDistanceFromStart() + neighbours.get(entry.getKey());
                if(newDistance < neighbour.getShortestDistanceFromStart()){
                    neighbour.setShortestDistanceFromStart(newDistance);
                    neighbour.setPreviousVertex(current);
                }
            }
        }
        return visited;
    }
}
