package day03;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import common.DataIterator;

public class Puzzle2 {
    DataIterator it;
    int[] occurs;// = new int[5];
    List<String> candidates = new LinkedList<String>();

    public static void main(String[] args) {
        new Puzzle2().solve();
    }

    private void solve() {
        it = new DataIterator("/day03/input2.txt");
        it.eachLine( candidates::add );

        int oxygen = findOxygen();
        System.out.format( "Oxygen -> %d%n", oxygen );
        int co2 = findCO2();
        System.out.format( "CO2 -> %d%n", co2 );
        
        System.out.format( "Life support -> %d%n", oxygen * co2 );

    }

    private int[] createMostCommonsBits(List<String> candidates) {
        int[] occurs = new int[candidates.get(0).length()];
        for(String str : candidates) {
            int len = str.length();
            if (occurs == null) {
                occurs = new int[ len ];
            }
            for (int i = 0; i < len; i++) {
                char c = str.charAt(i);
                if ( c == '0' ) occurs[i]--;
                if ( c == '1' ) occurs[i]++;
            }
        }
        return occurs;
    }
    
    private int findOxygen() {
        return find(candidates, true);
    }
    
    private int findCO2() {
        return find(candidates, false);
    }
    
    private int find( List<String> candidates, boolean takeOne ) {
        int len = candidates.get(0).length();
        for (int i = 0; i < len; i++) {
            int[] occurs = createMostCommonsBits(candidates);
            boolean bitCriteria = (occurs[i] >= 0 ? takeOne : !takeOne );
            final int testCharPosition = i; 
            List<String> newCandidates = candidates.stream()
                .filter( str -> {
                    char c = str.charAt(testCharPosition);
                    return (bitCriteria && c == '1') || (!bitCriteria && c == '0');
                })
                .collect( Collectors.toList() );
            
            System.out.println("["+i+"] new candidate size => " + newCandidates.size());
            // System.out.println( newCandidates );
            if ( newCandidates.size() == 1 ) {
                System.out.println("return candidate => " + newCandidates.get(0));
                return Integer.parseInt(newCandidates.get(0), 2);
            }
            candidates = newCandidates;
        }
        throw new RuntimeException("No oxygen candidate");
    }

}
