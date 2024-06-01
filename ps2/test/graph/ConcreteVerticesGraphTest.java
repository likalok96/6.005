/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
//import java.util.Set;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }


    /*
     * Testing ConcreteVerticesGraph...
     */

     @Test
     public void testEmptyGraph(){
         Graph<String> graph = emptyInstance();
 
         assertEquals(Collections.emptySet(),graph.vertices());
     }
     


    
    // Testing strategy for ConcreteVerticesGraph.toString()
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   construct:
    //      name: null, String
    //   setTarget:
    //      target: null, vertex.name(source), other String
    //      weight: <0, 0, >0
    
    @Test(expected=AssertionError.class)
    public void testNullName(){
        new Vertex<>(null);
    }

    @Test
    public void testName(){
        Vertex<String> vertex = new Vertex<>("test1");
        assertEquals("expected name test1","test1", vertex.getName());
    }

    @Test
    public void testSetTarget(){
        Vertex<String> vertex = new Vertex<>("test1");
        int set = vertex.setTarget("test2", 1);
        assertEquals("expected 0 return",0, set);
        assertTrue("expected targetMap contains test2", vertex.getTargetMap().keySet().contains("test2"));
        int weight = vertex.getTargetMap().get("test2");
        assertEquals("expected weight equals 1",1, weight);
    }

    @Test
    public void testSetExistTarget(){
        Vertex<String> vertex = new Vertex<>("test1");
        vertex.setTarget("test2", 1);
        int set = vertex.setTarget("test2", 2);

        assertEquals("expected 1 return",1, set);
        assertTrue("expected targetMap contains test2", vertex.getTargetMap().keySet().contains("test2"));
        int weight = vertex.getTargetMap().get("test2");
        assertEquals("expected weight equals 2",2, weight);
    }

    @Test
    public void testSetTargetDelete(){
        Vertex<String> vertex = new Vertex<>("test1");
        vertex.setTarget("test2", 1);
        int set = vertex.setTarget("test2", 0);

        assertEquals("expected 1 return",1, set);
        assertFalse("expected targetMap doesn't contain test2", vertex.getTargetMap().keySet().contains("test2"));
        assertEquals("expected no target test2",null, vertex.getTargetMap().get("test2"));
    }

    @Test
    public void testSetSameTarget(){
        Vertex<String> vertex = new Vertex<>("test1");
        int set = vertex.setTarget("test1", 1);

        assertEquals("expected -1 return",-1, set);
        assertFalse("expected targetMap doesn't contain test1", vertex.getTargetMap().keySet().contains("test1"));

    }

    @Test
    public void testSetNullTarget(){
        Vertex<String> vertex = new Vertex<>("test1");
        int set = vertex.setTarget(null, 1);

        assertEquals("expected -1 return",-1, set);
        assertFalse("expected targetMap doesn't contain null", vertex.getTargetMap().keySet().contains(null));

    }

    

    
}
