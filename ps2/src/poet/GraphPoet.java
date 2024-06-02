/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyStore.Entry;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new FileReader(corpus));
        //List<String>text = Files.readAllLines(corpus.toPath());
        LinkedList<String> stringList = new LinkedList<>(); 
        Scanner scanner = new Scanner(corpus).useDelimiter(" ");
        String prev;
        String current;
        while (scanner.hasNext()) {

            if(stringList.size()==0){
                stringList.add(scanner.next());
                current = stringList.getLast().toLowerCase();
                graph.add(current);
                continue;
            }

            prev = stringList.getLast().toLowerCase();
            stringList.add(scanner.next());
            //prev = scanner.next();
            current = stringList.getLast().toLowerCase();
            graph.add(current);
            //.get(current)!=null
            if(graph.targets(prev).keySet().contains(current)){
                graph.set(prev, current, 10);
                //graph.targets(prev).get(current) + 1
            }else{
                graph.set(prev, current, 1);
            }

        }
        scanner.close();
        //throw new RuntimeException("not implemented");
    }
    
    // TODO checkRep
    public Graph<String> getGraph(){
        return graph;
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(input).useDelimiter(" ");
        LinkedList<String> stringList = new LinkedList<>(); 
        String prev;
        String current;
        
        while (scanner.hasNext()) {

            if(stringList.size()==0){
                stringList.add(scanner.next());
                current = stringList.getLast();
                stringBuilder.append(current);
                continue;
            }
            prev = stringList.getLast().toLowerCase();
            stringList.add(scanner.next());
            current = stringList.getLast().toLowerCase();

            Map<String, Integer> source = graph.sources(current);
            Map<String, Integer> target = graph.targets(prev);

            if(source.get(prev.toLowerCase())==null){
                int maxWeight = 0;
                String maxString = "";
                for(String t : target.keySet()){
                    Map<String, Integer> targetT = graph.targets(t);
                    //targetT.get(current.toLowerCase())!=null && 
                    if(targetT.get(current.toLowerCase())!=null && targetT.get(current.toLowerCase())>maxWeight){
                        maxWeight =  targetT.get(current);
                        maxString = t;
                        
                    }
                }
                if(maxWeight>0) stringBuilder.append(" "+ maxString);
                
            }

            stringBuilder.append(" " + current);


        }
        scanner.close();

        return stringBuilder.toString();
        //throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    
}
