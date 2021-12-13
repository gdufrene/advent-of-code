package day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import common.DataIterator;

public class Puzzle {
    
    public static void main(String[] args) {
        new Puzzle("/day12/input3.txt").solve();
    }
    
    DataIterator it;

    Set<Cave> caves;
    Set<Connect> connects;
    Cave start;
    Cave end;
    
    int nbPath;
    
    public Puzzle(String path) {
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
        List<Cave> path = new ArrayList<Cave>();
        // path.add(start);
        iter(start, path, 0);
        System.out.println("PATHS => " + nbPath);
    }
    
    public void iter(Cave node, List<Cave> path, int depth) {
        path.add(node);
        if ( node.equals(end) ) {
            nbPath++;
            System.out.println( "** "+path );
            return;
        }
        /*
        StringBuffer pad = new StringBuffer();
        for (int i = 0; i < depth; i++) {
            pad.append("  ");
        }
        System.out.println(pad+"-"+node.name);
        */
        connects.stream()
            .filter( c -> c.from.equals(node) || c.to.equals(node) )
            .map( c -> c.other(node) )
            .filter( c -> !(c.small && path.contains(c)) )
            // .peek( path::add )
            .forEach( c -> {
                List<Cave> nset = new ArrayList<>(path);
                // nset.add(c);
                iter(c,nset,depth+1);
//                System.out.println();
            });
    }
    

}

class Cave {
    String name;
    boolean small;
    Cave(String name) {
        this.name = name;
        small = !name.chars()
            .filter( c -> c >= 'A' && c <= 'Z' )
            .findAny()
            .isPresent();
        // System.out.println( name + (small ? " small" : " big"));
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return ((Cave)obj).name.equals(name);
    }
    @Override
    public String toString() {
        return name;
    }
}

class Connect {
    Cave from;
    Cave to;
    public Cave other(Cave c) {
        return c.equals(from) ? to : from;
    }
}

