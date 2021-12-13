package day02;

import common.DataIterator;

public class Puzzle1 {
    
    DataIterator it;
    int horizontal;
    int depth;
    
    
    public static void main(String[] args) {
        new Puzzle1().solve();
    }

    private void solve() {
        it = new DataIterator("/day02/input1.txt");
        it.eachLine( this::oneLine );
        
        System.out.format("%d * %d => %d", horizontal, depth, (horizontal*depth) );
    }
    
    private void oneLine(String str) {
        String[] words = str.split(" ");
        String command = words[0];
        int value = Integer.parseInt(words[1]);
        
        if ( "forward".equals(command) ) {
            horizontal += value;
        }
        if ( "up".equals(command) ) {
            depth -= value;
        }
        if ( "down".equals(command) ) {
            depth += value;
        }
    }
    
    

}
