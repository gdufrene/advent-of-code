package day01;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Puzzle1 {
    
    public static void main(String[] args) throws IOException {
        URL url = Puzzle1.class.getResource("/day01/input01.txt");
        if ( url == null ) {
            throw new RuntimeException("no input data");
        }
        int lastOne = Integer.MIN_VALUE;
        boolean first = true;
        int increaded = 0;
        int line = 0;
        try ( Scanner s = new Scanner(url.openStream()) ) {
            while( s.hasNextInt() ) {
                line++;
                int currentOne = s.nextInt();
                if ( !first ) {
                    if ( currentOne > lastOne ) increaded++;
                } else {
                    first = false;
                }
                lastOne = currentOne;
                s.nextLine();
            }
        }
        System.out.format("%d / %d", increaded, line);
    }

}
