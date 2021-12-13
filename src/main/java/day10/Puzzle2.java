package day10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import common.DataIterator;

public class Puzzle2 {

    DataIterator it;
    List<String> lines;
    
    public Puzzle2(String path) {
        it = new DataIterator(path);
        lines = new LinkedList<String>();
        it.eachLine(lines::add);
        System.out.println("Loaded " + path+": " + lines.size() + " lines");
    }
    
    public void solve() {
        int[] pts = new int[] {1, 2, 3, 4};
        long total = 0;
        List<String> incomple = new ArrayList<String>();
        List<Long> scores = new ArrayList<Long>();
        for(String line : lines) {
            List<Character> opened = new ArrayList<Character>();
            int z;
            int i = 0;
            for (i = 0; i < line.length(); i++) {
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
                if ( x != expected ) break;
            }
            if ( i == line.length() ) {
                incomple.add(line);
                total = 0;
                String completed = "";
                while ( !opened.isEmpty() ) {
                    char x = opened.remove(0);
                    int posExpected = "([{<".indexOf( x );
                    completed += ")]}>".charAt(posExpected);
                    total *= 5;
                    total += pts[posExpected];
                }
                System.out.println(completed+" - "+total);
                scores.add(total);
            }
        }
        
        System.out.println("Incomplete lines : " + incomple.size());
        
        scores.sort(Comparator.comparingLong(i -> i));
        int i = scores.size() / 2;
        System.out.println("Autocomplete score : " + scores.get(i));
        

    }
    

    
    public static void main(String[] args) {
        new Puzzle2("/day10/input1.txt").solve();
    }
}
