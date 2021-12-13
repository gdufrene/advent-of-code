package day02;

import common.DataIterator;

public class Puzzle2 {
    
    DataIterator it;
    int horizontal;
    int depth;
    int aim;
    
    
    public static void main(String[] args) {
        new Puzzle2().solve();
    }

    private void solve() {
        it = new DataIterator("/day02/input1.txt");
        it.eachLine( this::oneLine );
        long result = (horizontal*depth);
        System.out.format("%d * %d => %d", horizontal, depth, result );
    }
    
    private void oneLine(String str) {
        String[] words = str.split(" ");
        String command = words[0];
        int value = Integer.parseInt(words[1]);
        
        if ( "forward".equals(command) ) {
            horizontal += value;
            depth += (aim*value);
        }
        if ( "up".equals(command) ) {
            aim -= value;
        }
        if ( "down".equals(command) ) {
            aim += value;
        }
    }
    
    

}
