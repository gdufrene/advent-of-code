package day03;

import common.DataIterator;

public class Puzzle1 {
    DataIterator it;
    int[] occurs;// = new int[5];
    
    
    public static void main(String[] args) {
        new Puzzle1().solve();
    }

    private void solve() {
        it = new DataIterator("/day03/input2.txt");
        it.eachLine( this::oneLine );
        System.out.format( "Gamma -> %d // Epsilon -> %d%n", gamma(), epsilon() );
        System.out.format( "Consumption -> %d%n", (gamma() * epsilon()) );
    }
    
    private void oneLine(String str) {
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
    
    private int gamma() {
        String str = "";
        for (int i = 0; i < occurs.length; i++) {
            if ( occurs[i] > 0 ) str += "1";
            else str += "0";
        }
        return Integer.parseInt(str, 2);
    }
    
    private int epsilon() {
        String str = "";
        for (int i = 0; i < occurs.length; i++) {
            if ( occurs[i] > 0 ) str += "0";
            else str += "1";
        }
        return Integer.parseInt(str, 2);
    }
}
