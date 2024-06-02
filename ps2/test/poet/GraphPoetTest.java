/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import graph.Graph;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    @Test
    public void testGraphPoet() throws IOException{
        File file = new File("test/poet/poet-try.txt");
        GraphPoet poetGraph = new GraphPoet(file);
        Graph<String> graph = poetGraph.getGraph();
        
/*         assertEquals(2, graph.vertices().size());
        assertTrue(graph.vertices().containsAll(Arrays.asList("hello,","goodbye!")));
        int num = graph.sources("goodbye!").get("hello,");
        assertEquals(3,num); */

        
        assertEquals("Test of the system.", poetGraph.poem("Test the system.") );
    }

    @Test
    public void testGraphPoet2() throws IOException{
        File file = new File("test/poet/poet-test1.txt");
        GraphPoet poetGraph = new GraphPoet(file);
        Graph<String> graph = poetGraph.getGraph();
        
/*         assertEquals(2, graph.vertices().size());
        assertTrue(graph.vertices().containsAll(Arrays.asList("hello,","goodbye!")));
        int num = graph.sources("goodbye!").get("hello,");
        assertEquals(3,num); */

        
        assertEquals("seek to explore strange new life and exciting syn", poetGraph.poem("seek to explore new and exciting syn") );
    }
    
}
