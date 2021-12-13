package common;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import day01.Puzzle1;

public class DataIterator {
    
    URL dataUrl;
    
    public DataIterator(String ressourcePath) {
        dataUrl = Puzzle1.class.getResource(ressourcePath);
        if ( dataUrl == null ) {
            throw new RuntimeException("no input data");
        }
    }
    
    public int eachIntLine( Consumer<Integer> consumer ) {
        int line = 0;
        try ( Scanner s = new Scanner(dataUrl.openStream()) ) {
            while( s.hasNextInt() ) {
                consumer.accept(s.nextInt());
                s.nextLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading int data", e);
        }
        return line;
    }
    
    public int eachLine( Consumer<String> consumer ) {
        int line = 0;
        try ( Scanner s = new Scanner(dataUrl.openStream()) ) {
            while( s.hasNextLine() ) {
                consumer.accept(s.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading int data", e);
        }
        return line;
    }

}
