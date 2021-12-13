package day10;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import common.DataIterator;

public class Puzzle {

    DataIterator it;
    List<String> lines;
    
    public Puzzle(String path) {
        it = new DataIterator(path);
        lines = new LinkedList<String>();
        it.eachLine(lines::add);
        System.out.println("Loaded " + path+": " + lines.size() + " lines");
    }
    
    public void _solve() {
        int[] pts = new int[] {3, 57, 1197, 25137};
        int total = 0;
        for(String line : lines) {
            List<Character> opened = new ArrayList<Character>();
            int z;
            for (int i = 0; i < line.length(); i++) {
                char x = line.charAt(i);
                z = "([{<".indexOf(x);
                if ( z >= 0 ) {
                    opened.add(0, x);
                    continue;
                }
                z = ")]}>".indexOf(x);
                if ( z < 0 ) throw new RuntimeException("Illegal character:"+x);
                int posExpected = "([{<".indexOf( opened.remove(0) );
                char expected = ")]}>".charAt(posExpected);
                if ( x != expected ) {
                    total += pts[z];
                    System.out.println("Syntax error at " + i + " with char "+x+" expected "+expected+" || +"+pts[z]);
                    break;
                }
            }
        }
        System.out.println("Total => " + total);
    }
    
    public void solve() {
        int[] pts = new int[] {3, 57, 1197, 25137};
        int total = 0;
        for(String line : lines) {
            int[] cpt = new int[4];
            for (int i = 0; i < line.length(); i++) {
                char x = line.charAt(i);
                int z = -1;
                if ( x == '(' || x == ')' ) z = 0;
                if ( x == '[' || x == ']' ) z = 1;
                if ( x == '{' || x == '}' ) z = 2;
                if ( x == '<' || x == '>' ) z = 3;
                if ( "([{<".indexOf(x) >= 0 ) cpt[z]++;
                if ( ")]}>".indexOf(x) >= 0 ) cpt[z]--;
                if (cpt[z] < 0) {
                    total += pts[z];
                }
                System.out.print( x+"["+z+"]:"+cpt[z]+" ");
            }
            System.out.println();
        }
        System.out.println("Total => " + total);
    }
    
    public static void main(String[] args) {
        new Puzzle("/day10/input1.txt")._solve();
    }
}
