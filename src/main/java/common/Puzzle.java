package common;

import common.DataIterator;

public class Puzzle {

    public static void main(String[] args) {
        new Puzzle("/day13/input0.txt").solve();
    }
    
    DataIterator it;
    
    public Puzzle(String path) {
        it = new DataIterator(path);
        it.eachLine(System.out::println);
    }
    
    public void solve() {
        
    }
}
