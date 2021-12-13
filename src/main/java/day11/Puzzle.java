package day11;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import common.DataIterator;

public class Puzzle {
    
    public static void main(String[] args) {
        new Puzzle("/day11/input1.txt").solve2();
    }
    
    DataIterator it;
    
    int[] octo;
    int w;
    int totalFlash;
    
    int lineNumber = 0;
    int stepNumber = 0;
    
    public Puzzle(String path) {
        it = new DataIterator(path);
        it.eachLine( line -> {
           lineNumber++;
           if (lineNumber == 1) {
               w = line.length();
               octo = new int[ w*w ];
           }
           int from = (lineNumber-1)*w;
           for (int i = 0; i < line.length(); i++) {
               octo[from+i] = line.charAt(i) - '0';
           }
        });
    }
    
    public void solve() {
        show();
        for (int i = 0; i < 100; i++) {
            System.out.println("--- step "+(i+1)+" ---");
            step();
            show();
            System.out.println("  total -> " + totalFlash);
        }
    }
    
    public void solve2() {
        boolean allFlash = false;
        while( !allFlash ) {
            step();
            allFlash = true;
            for (int i = 0; i < octo.length; i++) {
                allFlash = allFlash && octo[i] == 0;
            }
        }
        show();
        System.out.println("==> After step " + stepNumber);
    }
    
    public void step() {
        stepNumber++;
        List<Integer> hasFlash = new LinkedList<Integer>();

        IntConsumer incrementer = x -> {
            octo[x]++;
            if (octo[x] == 10) {
                hasFlash.add(x);
                totalFlash++;
            }
        };
        
        Function<Integer, IntStream> neibourgh = x -> {
            List<Integer> n = new LinkedList<Integer>();
            
            for (int j = x-w; j <= x+w; j+=w) {
                if (j < 0) continue;
                if (j >= octo.length) continue;
                n.add(j);
                if ( j % w > 0 ) n.add(j-1);
                if ( j % w < w-1 ) n.add(j+1);                
            }
            
            return n.stream().mapToInt(i->i);
        };

        for (int i = 0; i < octo.length; i++) {
            incrementer.accept(i);
        }
        while (!hasFlash.isEmpty()) {
            int f = hasFlash.remove(0);
            neibourgh.apply(f)
                .forEach(incrementer);
        }
        for (int i = 0; i < octo.length; i++) {
            if ( octo[i] > 9 ) octo[i] = 0;
        }
    }
    
    public void show() {
        for (int i = 0; i < octo.length; i++) {
            System.out.print( octo[i] );
            if ((i+1)%w==0) System.out.println();
        }
    }
    

}
