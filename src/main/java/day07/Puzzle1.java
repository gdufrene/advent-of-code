package day07;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import common.DataIterator;

public class Puzzle1 {
    
    DataIterator it;
    List<Integer> positions;
    
    public Puzzle1(String path) {
        it = new DataIterator(path);
        positions = new LinkedList<Integer>();
        it.eachLine( (line) -> Stream.of(line.split(","))
            .map(Integer::parseInt)
            .forEach(positions::add) 
        );
        
    }
    
    public void solve() {
        positions.sort(Comparator.naturalOrder());
        int middle = positions.size() / 2;
        int middleValue = positions.get(middle);
        System.out.println( "result => " + middleValue );
        long fuel = positions.stream()
            .map( i -> Math.abs(i-middleValue) )
            .mapToInt( Integer::intValue )
            .sum();
        System.out.println( "fuel => " + fuel );
    }
    
    public void solve2() {
        
        double average = positions.stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElseThrow( () -> new RuntimeException("no data") );
        System.out.println( "average => " + average );
        
        
        int middleValue = (int) Math.round(average);  
        
        for (int i = middleValue-5; i < middleValue+5; i++) {
            long fuel = fuelAt(i);
            System.out.format( "fuel[%d] => %d%n", i, fuel );
            
        }

    }
    
    long fuelAt(int w) {
        int values[] = new int[2000];
        for (int j = 0, sum = 0; j < values.length; j++, sum += j) {
            values[j] = sum;
        }
        return positions.stream()
            .mapToInt( i -> values[ Math.abs(i-w) ] )
            .sum();
    }

    public static void main(String[] args) {
        new Puzzle1("/day07/input1.txt").solve2();
    }
}
