package day13;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import common.DataIterator;

public class Puzzle {

    public static void main(String[] args) {
        new Puzzle("/day13/input1.txt").solve2();
    }
    
    DataIterator it;
    List<Point> points;
    List<Fold> folds;
    Consumer<String> loadFunction;
    
    public Puzzle(String path) {
        it = new DataIterator(path);
        points = new LinkedList<>();
        folds = new LinkedList<>();
        loadFunction = this::addPoint;
        it.eachLine( line -> {
            if (line.isEmpty()) return;
            if (line.startsWith("fold along ")) {
                loadFold(line.substring(11));
                return;
            }
            addPoint(line);
        });
    }
    
    public void addPoint(String line) {
        if ( line.isEmpty() ) {
            loadFunction = this::loadFold;
            return;
        }
        String[] coord = line.split(",");
        Point p = new Point( Integer.parseInt(coord[0]), Integer.parseInt(coord[1]) );
        points.add(p);
    }
    
    public void loadFold(String line) {
        Fold fold = new Fold();
        String[] arg = line.split("=");
        fold.axe = arg[0].charAt(0);
        fold.z = Integer.parseInt(arg[1]);
        folds.add(fold);
    }
    
    public void solve() {
        apply( folds.get(0) );
        Set<Point> folded = new HashSet<>();
        points.stream()
            // .peek(System.out::println)
            .forEach(folded::add);
        show(folded);
        System.out.println("SIZE -> " + folded.size() );
    }
    
    public void solve2() {
        folds.stream().forEach(this::apply);
        Set<Point> folded = new HashSet<>();
        points.stream()
            // .peek(System.out::println)
            .forEach(folded::add);
        show(folded);
    }
    
    public void apply(Fold fold) {
        // show(points);
        // System.out.println("----");
        points.forEach(fold::transform);
    }
    
    public void show(Collection<Point> points) {
        Point max = new Point();
        for (Point p : points) {
            max.x = Math.max(p.x, max.x);
            max.y = Math.max(p.y, max.y);
        }
        max.x++;
        max.y++;
        System.out.println(max);
        char[] c = new char[max.x * max.y];
        for (int i = 0; i < c.length; i++) {
            c[i] = '.';
        }
        points.forEach( p -> c[p.y*max.x+p.x] = '#' );
        for (int i = 0; i < c.length; i++) {
            System.out.print( c[i] );
            if ((i + 1) % max.x == 0) System.out.println();
        }
    }
}

class Fold {
    char axe;
    int z;
    public void transform(Point p) {
        if ( axe == 'x' && p.x > z ) {
            p.x = z * 2 - p.x;
        }
        if ( axe == 'y' && p.y > z ) {
            p.y = z * 2 - p.y;
        }
    }
}