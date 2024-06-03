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
    /* Partition:
     *  GraphPoet():
     *      file not exist
     *  poem(input):
     *      word match with different and same case
     *      no word match
     *      bridge word: 0,1, >1 (with different weight) 
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests

    @Test(expected=IOException.class)
    public void testGraphPoetNotExist() throws IOException{
        File file = new File("test/poet/poet-hahaha.txt");
        GraphPoet poetGraph = new GraphPoet(file);        
    }

    @Test
    public void testGraphPoet() throws IOException{
        File file = new File("test/poet/poet-try.txt");
        GraphPoet poetGraph = new GraphPoet(file);

        assertEquals("Test of the system.", poetGraph.poem("Test the system.") );
    }

    @Test
    public void testGraphPoetNoMatch() throws IOException{
        File file = new File("test/poet/poet-test0.txt");
        GraphPoet poetGraph = new GraphPoet(file);
        
        assertEquals("seek to explore new and exciting syn", poetGraph.poem("seek to explore new and exciting syn") );
    }

    @Test
    public void testGraphPoetOneMatch() throws IOException{
        File file = new File("test/poet/poet-test1.txt");
        GraphPoet poetGraph = new GraphPoet(file);

        assertEquals("seek to explore strange new life and exciting syn", poetGraph.poem("seek to explore new and exciting syn") );
    }

    
    @Test
    public void testGraphPoetOneMatch2() throws IOException{
        File file = new File("test/poet/poet-test2.txt");
        GraphPoet poetGraph = new GraphPoet(file);
        
        assertEquals("seek to explore strange new life and new exciting syn", poetGraph.poem("seek to explore new and exciting syn") );
    }

    @Test
    public void testGraphPoetTwoMatch() throws IOException{
        File file = new File("test/poet/poet-test3.txt");
        GraphPoet poetGraph = new GraphPoet(file);
        
        assertEquals("seek to explore haha new life and exciting syn", poetGraph.poem("seek to explore new and exciting syn") );
    }
    
}
