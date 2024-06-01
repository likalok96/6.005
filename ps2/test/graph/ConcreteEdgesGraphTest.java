/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

//import java.util.Arrays;
import java.util.Collections;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     * Partition:
     *  add(vertex):
     *      empty graph
     *      graph with vertices with vertex already exist or not
     *  
     *  set(source, target, weight):
     *      source = target, source != target
     *      source exist in graph or not
     *      target exist in graph or not
     *      edge exist in graph or not
     *      weight: <0 ,0 ,>0
     * 
     *  remove(vertex):
     *      no vertex in graph
     *      only one vertex in graph
     *      graph doesn't contain vertex
     *      edge from sources to vertex 
     *      edge from vertex to sources
     * 
     *   vertices():
     *      graph empty or not
     *      test immutable
     *
     *   source(vertex):
     *      vertex dosen't exist
     *      no edge between vertex and other vertices
     *      edge from source to vertex in gragh
     *      edge from vertex to source in graph
     *      
     *   target(vertex):
     *      vertex dosen't exist
     *      no edge between vertex and other vertices
     *      edge from source to vertex in gragh
     *      edge from vertex to source in graph
     *      
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */

    @Test
    public void testEmptyGraph(){
        Graph<String> graph = emptyInstance();

        assertEquals(Collections.emptySet(),graph.vertices());
    }


    
    // Testing strategy for ConcreteEdgesGraph.toString()
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   source = target, source != target
    //   weight: <0, 0, >0
    

    @Test
    public void testEdgeFail(){

        String source = "source";
        String target = "target";
        int weight = 0;

        IllegalArgumentException exception0 =  assertThrows(IllegalArgumentException.class, () ->{new Edge<String>(source, target, -1);});
        assertEquals("source should not equal to target, source or target should not be null , weigth should be >0", exception0.getMessage());

        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () ->{new Edge<String>(source, target, weight);});
        assertEquals("source should not equal to target, source or target should not be null , weigth should be >0", exception.getMessage());

        IllegalArgumentException exception2 =  assertThrows(IllegalArgumentException.class, () ->{new Edge<String>(source, source, 1);});
        assertEquals("source should not equal to target, source or target should not be null , weigth should be >0", exception2.getMessage());

    }

    @Test
    public void testEdge(){
        String source = "source";
        String target = "target";
        int weight = 1;
        Edge<String> edge = new Edge<String>(source, target, weight);
        
        assertEquals("expected source","source" ,edge.getSource());
        assertEquals("expected target","target" ,edge.getTarget());
        assertEquals("expected source",1 ,edge.getWeight());

        source ="test";
        target = "testing";
        weight = 100;
        assertEquals("expected source","source" ,edge.getSource());
        assertEquals("expected target","target" ,edge.getTarget());
        assertEquals("expected source",1 ,edge.getWeight());

        String getSource = edge.getSource();
        String getTarget = edge.getTarget();
        int getWeight = edge.getWeight();

        getSource = getSource +"asds";
        getTarget = getTarget + "hahahaha";
        getWeight = getWeight + 1000000;

        assertEquals("expected source","source" ,edge.getSource());
        assertEquals("expected target","target" ,edge.getTarget());
        assertEquals("expected source",1 ,edge.getWeight());

    }

    
}