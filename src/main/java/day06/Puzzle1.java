package day06;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import common.CommonPuzzle;

public class Puzzle1 extends CommonPuzzle {
    
    public static int MAX_DAYS = 256;
    
    long[] fishPerDay;
    
    @Override
    public CommonPuzzle load(String filePath) {
        super.load(filePath);
        fishPerDay = new long[9];
        it.eachLine( line -> {
            Stream.of( line.split(",") )
                .map( Integer::parseInt )
                .forEach( i -> fishPerDay[i]++ );
         });
        return this;
    }
    
    
    @Override
    public void solve() {
        for (int i = 0; i < MAX_DAYS; i++) {
            tickOneDay();
        }
        long result = LongStream.of(fishPerDay)
            .sum();
        System.out.println("Result -> " + result);
        // System.out.println("Max Result -> " + Long.MAX_VALUE);
    }
    
    public void tickOneDay() {
        long day0 = fishPerDay[0];
        for (int i = 0; i < fishPerDay.length - 1; i++) {
            fishPerDay[i] = fishPerDay[i+1];
        }
        fishPerDay[8] = day0;
        fishPerDay[6] += day0;
    }
    
    public static void main(String[] args) {
        new Puzzle1()
            .load("/day06/input1.txt")
            .solve();
    }

}
