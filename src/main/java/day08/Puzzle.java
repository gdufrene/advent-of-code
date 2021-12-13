package day08;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import common.DataIterator;

public class Puzzle {
    
    DataIterator it;
    List<String> data;
    int sum = 0;
    
    public Puzzle(String path) {
        data = new ArrayList<String>();
        it = new DataIterator(path);
        sum = 0;
        it.eachLine( data::add );
        System.out.println( data.size() + " lines");
    }
    

    
    List<Point> lowestLocation;
    List<Integer> previousLow = new LinkedList<Integer>(); 
    
    public Puzzle solve() {
        lowestLocation = new LinkedList<Point>();
        
        int nb = 0;
        for ( String line : data ) {
            int len = line.length();            
            for (int i = 0; i < len; i++) {
                int a = line.charAt(i);
                boolean lowest = true;
                
                if ( i > 0 ) 
                    lowest = lowest && a < line.charAt(i-1);
                if ( i < len-1 ) 
                    lowest = lowest && a < line.charAt(i+1);
                if ( nb > 0 ) 
                    lowest = lowest && a < data.get(nb-1).charAt(i);
                if ( nb < data.size()-1 ) 
                    lowest = lowest && a < data.get(nb+1).charAt(i);
                
                if ( lowest ) {
                    lowestLocation.add( new Point(i, nb) );
                    sum += (a - '0') + 1;
                }
            }
            nb++;            
        }
        System.out.println("Sum => " + sum);
        return this;
    }
    
    public void solve2() {
        
        int width = data.get(0).length();
        List<Integer> areas = new LinkedList<Integer>();
        for(Point p : lowestLocation) {
            List<String> copy = new ArrayList<String>();
            data.stream().forEach(copy::add);
                        
            
            List<Point> extendsFrom = new LinkedList<Point>();
            extendsFrom.add(p);
            while ( !extendsFrom.isEmpty() ) {
                Point p2 = extendsFrom.remove(0);
                setX(copy, p2);
                //System.out.println( p2 );
                
                BiConsumer<Integer, Integer> propagate = (a, b) -> {
                    Point next = new Point(p2.x+a, p2.y+b);
                    int v = get(copy, next);
                    if ( v != 9 && v != -1 ) extendsFrom.add(next);
                };
                
                if ( p2.x > 0 ) {
                    propagate.accept(-1, 0);
                }
                if ( p2.x < width - 1 ) {
                    propagate.accept(1, 0);
                }
                if ( p2.y > 0 ) {
                    propagate.accept(0, -1);
                }
                if ( p2.y < data.size() - 1 ) {
                    propagate.accept(0, 1);
                }
            }
            /*
            System.out.println("---");
            copy.stream().forEach(System.out::println);
            */
            long area = copy.stream()
                .mapToLong( this::countX )
                .sum();
            areas.add((int) area);
        }
        
        AtomicLong total = new AtomicLong(1);
        
        areas.sort( (a, b) -> b - a );
        areas.stream()
            .mapToInt( i -> i )        
            .limit(3)
            .forEach( i -> total.set(total.get() * i) );
        
        System.out.println("Sum of areas => " + total.get());
        
    }
    
    private void setX(List<String> data, Point p) {
        String str = data.remove(p.y);
        str = replaceChar(str, p.x, 'X');
        data.add(p.y, str);
    }
    
    private long countX(String line) {
        return line.chars().filter( i -> i == 'X' ).count();
    }
    
    private int get(List<String> data,Point p) {
        char c = data.get(p.y).charAt(p.x);
        if ( c == 'X' ) return -1;
        return c - '0';
    }
    
    private String replaceChar(String str, int i, char c) {
        if ( i == 0 ) return c + str.substring(i+1);
        if ( i == str.length()-1 ) return str.substring(0,i) + c; 
        return str.substring(0,i) + c + str.substring(i+1);
    }

    public static void main(String[] args) {
        new Puzzle("/day08/input1.txt")
            .solve()
            .solve2();
    }
}
