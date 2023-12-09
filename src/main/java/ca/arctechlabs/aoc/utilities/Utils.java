package ca.arctechlabs.aoc.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
