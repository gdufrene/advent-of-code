package day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import common.DataIterator;

public class Puzzle2 {
    
    public static void main(String[] args) {
        new Puzzle2("/day12/input3.txt").solve();
    }
    
    DataIterator it;

    Set<Cave> caves;
    Set<Connect> connects;
    Cave start;
    Cave end;
    
    int nbPath;
    
    public Puzzle2(String path) {
        caves = new HashSet<>();
        connects = new HashSet<>();
        it = new DataIterator(path);
        it.eachLine( line -> {
            Cave c1 = new Cave( line.split("-")[0] );
            Cave c2 = new Cave( line.split("-")[1] );
            caves.add(c1);
            caves.add(c2);
            caves.add(c1);
            if (c1.name.equals("start")) start = c1;
            if (c1.name.equals("end")) end = c1;
            caves.add(c2);
            if (c2.name.equals("start")) start = c2;
            if (c2.name.equals("end")) end = c2;
            Connect connect = new Connect();
            connect.from = c1;
            connect.to = c2;
            connects.add(connect);
        });
        System.out.println( connects.size() + " links");
        System.out.println( caves.size() + " caves");
    }
    
    public void solve() {
        iter(start, new Path(), 0);
        System.out.println("PATHS => " + nbPath);
    }
    
    public void iter(Cave node, Path path, int depth) {
        path.add(node);
        if ( node.equals(end) ) {
            nbPath++;
            System.out.println( "** "+path );
            return;
        }
        /* /
        StringBuffer pad = new StringBuffer();
        for (int i = 0; i < depth; i++) {
            pad.append("  ");
        }
        System.out.println(pad+"-"+node.name);
        /* */
        connects.stream()
            .filter( c -> c.from.equals(node) || c.to.equals(node) )
            .map( c -> c.other(node) )
            .filter( c -> !c.equals(start) && !(path.visitSmallTwice && c.small && path.contains(c)) )
            // .peek( path::add )
            .forEach( c -> {
                iter(c, path.copy(), depth+1);
//                System.out.println();
            });
    }
    

}



class Path {
    List<Cave> caves;
    boolean visitSmallTwice;
    Path() {
        this(new ArrayList<Cave>());
    }
    Path(List<Cave> caves) {
        this.caves = new ArrayList<Cave>(caves);
        evaluateVisit();
    }
    public Path copy() {
        return new Path( caves );
    }
    public boolean contains(Cave c) {
        return caves.contains(c);
    }
    public void add(Cave node) {
        caves.add(node);
        if ( node.small ) evaluateVisit();
    }
    private void evaluateVisit() {
        visitSmallTwice = caves.stream()
            .filter(c -> c.small)
            .collect( Collectors.groupingBy(c -> c.name, Collectors.counting()) )
            .entrySet()
            .stream()
            .filter( e -> e.getValue() == 2 )
            .findAny()
            .isPresent();
    }
    @Override
    public String toString() {
        return caves.toString();
    }
}