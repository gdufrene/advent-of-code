package day01;

import common.DataIterator;

public class Puzzle2 {
    
    DataIterator it = new DataIterator("/day01/input01.txt");
    int[] window = new int[3];
    int idx = 0;
    int increased = 0;
    int lastSum;
    
    int a, b, c;
    int line = 0;
    
    public Puzzle2() {
    }
    
    public static void main(String[] args) {
        new Puzzle2().solve();
    }
    
    private void solve() {
        it.eachIntLine(this::oneMoreLine);
        System.out.format( "Increased: %d", increased );
    }
    
    private void oneMoreLine(int i) {
        line++;
        
        switch (line) {
        case 1:
            a = i; 
            return;
        case 2:
            b = i; 
            return;
        case 3:
            c = i;
            return;
            
        default:
            lastSum = a + b + c;
            a = b;
            b = c;
            c = i;
            int sum = a + b + c;
            if ( sum > lastSum ) increased++;
            break;
        }

        
        /*
        window[idx] = i;
        if ( idx < window.length-1 ) {
            idx++;
            return;
        }
        int sum = 0;
        for (int j = 0; j < window.length; j++) {
            sum += window[j];
        }
        if ( lastSum != null && lastSum < sum ) {
            increased++;
        }
        for (int j = 1; j < window.length - 1; j++) {
            window[j-1] = window[j];
        }
        lastSum = sum;
        */
    }
    

}
