/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */

    @Test
    public void testEmptyGraph(){
        Graph<String> graph = emptyInstance();

        assertEquals(Collections.emptySet(),graph.vertices());
    }

    @Test
    public void testAddEmptyGraph(){
        Graph<String> graph = emptyInstance();
        boolean adding = graph.add("test1");
        assertTrue("expected true",adding);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 1 vertex in graph",1, vertices.size());
        assertTrue("expected graph vertices contains test1",vertices.contains("test1"));
    }

    @Test
    public void testAddExistGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        boolean adding = graph.add("test1");
        assertFalse("expected false",adding);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 1 vertex in graph",1, vertices.size());
        assertTrue("expected graph vertices contains test1",vertices.contains("test1"));
    }

    @Test
    public void testSetEmptyGraph(){
        Graph<String> graph = emptyInstance();
        int set = graph.set("test1","test2",1);
        //boolean adding = graph.add("test1");
        //assertEquals("expected -1",-1,set);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 0 return ",0, set);
        assertEquals("expected 0 vertex in graph",2, vertices.size());
        assertEquals("expected 0 vertex in graph",1, graph.targets("test1").size());
        assertTrue("expected graph vertices contains test1",vertices.containsAll(Arrays.asList("test1","test2")));
    }

    @Test
    public void testSetNegativeWeightGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        int set = graph.set("test1","test1",-1);
        //boolean adding = graph.add("test1");
        //assertEquals("expected -1",-1,set);
        Set<String> vertices = graph.vertices();
        assertEquals("expected -1 return ",-1, set);
        assertEquals("expected 0 vertex in graph",2, vertices.size());
        assertEquals("expected 0 vertex in graph",0, graph.targets("test1").size());
        //assertTrue("expected graph vertices contains test1",vertices.contains("test1"));
    }

    @Test
    public void testSetSourceEqualsTargetGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        int set = graph.set("test1","test1",1);
        //boolean adding = graph.add("test1");
        //assertEquals("expected -1",-1,set);
        Set<String> vertices = graph.vertices();
        assertEquals("expected -1 return ",-1, set);
        assertEquals("expected 0 vertex in graph",2, vertices.size());
        assertEquals("expected 0 vertex in graph",0, graph.targets("test1").size());
        //assertTrue("expected graph vertices contains test1",vertices.contains("test1"));
    }

    @Test
    public void testSetNotExistEdgeGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        int set = graph.set("test1","test2",1);
        //boolean adding = graph.add("test1");
        //assertEquals("expected -1",-1,set);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 0 return ",0, set);
        assertEquals("expected 2 vertex in graph",2, vertices.size());
        assertEquals("expected 1 targets",1, graph.targets("test1").size());
        int weight = graph.targets("test1").get("test2");
        assertEquals("expected weight is 1",1, weight);

    }

    @Test
    public void testSetExistEdgeGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.set("test1","test2",1);
        int set = graph.set("test1","test2",2);
        //boolean adding = graph.add("test1");
        //assertEquals("expected -1",-1,set);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 1 return ",1, set);
        assertEquals("expected 2 vertex in graph",2, vertices.size());
        assertEquals("expected 1 targets",1, graph.targets("test1").size());
        int weight = graph.targets("test1").get("test2");
        assertEquals("expected weight is 2",2, weight);
    }


    @Test
    public void testRemoveEmptyGraph(){
        Graph<String> graph = emptyInstance();
        boolean remove = graph.remove("test1");
        assertFalse("expected false",remove);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 1 vertex in graph",0, vertices.size());
        assertFalse("expected graph vertices contains test1",vertices.contains("test1"));
    }
    
    @Test
    public void testRemoveOnlyVertexGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        boolean remove = graph.remove("test1");
        assertTrue("expected true",remove);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 1 vertex in graph",0, vertices.size());
        assertFalse("expected graph vertices contains test1",vertices.contains("test1"));
    }

    @Test
    public void testRemoveMissingVertexGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test2");
        boolean remove = graph.remove("test1");
        assertFalse("expected true",remove);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 1 vertex in graph",1, vertices.size());
        assertFalse("expected graph vertices doesn't contain test1",vertices.contains("test1"));
        assertTrue("expected graph vertices contains test2",vertices.contains("test2"));
    }

    @Test
    public void testRemoveMultiVertexGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test2");
        graph.add("test3");
        boolean remove = graph.remove("test1");
        assertFalse("expected false",remove);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 2 vertex in graph",2, vertices.size());
        assertFalse("expected graph vertices doesn't contain test1",vertices.contains("test1"));
        assertTrue("expected graph vertices contains test2",vertices.containsAll(Arrays.asList("test2","test3")));
    }

    @Test
    public void testRemoveMultiVertexEdgeGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.set("test1", "test2", 1);
        graph.set("test1", "test3", 1);
        boolean remove = graph.remove("test1");
        assertTrue("expected true",remove);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 2 vertex in graph",2, vertices.size());
        assertFalse("expected graph vertices doesn't contain test1",vertices.contains("test1"));
        assertTrue("expected graph vertices contains test2",vertices.containsAll(Arrays.asList("test2","test3")));
        assertEquals("expected graph vertices doesn't contain test1",Collections.EMPTY_MAP ,graph.targets("test1"));
    }

    @Test
    public void testRemoveMultiVertexMultiEdgeGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.add("test4");
        graph.set("test1", "test2", 1);
        graph.set("test1", "test3", 1);
        graph.set("test2", "test1", 1);
        graph.set("test3", "test1", 1);
        graph.set("test4", "test3", 1);
        boolean remove = graph.remove("test1");
        assertTrue("expected true",remove);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 2 vertex in graph",3, vertices.size());
        assertFalse("expected graph vertices doesn't contain test1",vertices.contains("test1"));
        assertTrue("expected graph vertices contains test2-4",vertices.containsAll(Arrays.asList("test2","test3","test4")));
        assertEquals("expected graph vertices doesn't contain test1",Collections.EMPTY_MAP ,graph.targets("test1"));
        assertEquals("expected graph vertices doesn't contain test1",1 ,graph.targets("test4").size());
    }

    @Test
    public void testVerticesEmptyGraph(){
        Graph<String> graph = emptyInstance();
        //assertFalse("expected false",remove);
        Set<String> vertices = graph.vertices();
        assertEquals("expected 0 vertex in graph",0, vertices.size());

        //assertFalse("expected graph vertices contains test1",vertices.contains("test1"));
    }

    @Test
    public void testVerticesGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        //assertFalse("expected false",remove);
        Set<String> vertices = graph.vertices();

        Set<String> expected = new HashSet<>();
        expected.addAll(Arrays.asList("test1","test2"));

        assertEquals("expected 2 vertex in graph",2, vertices.size());
        assertTrue("expected graph vertices contains test1",expected.equals(vertices));
    }

    @Test
    public void testVerticesImmutableGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        //assertFalse("expected false",remove);
        Set<String> vertices = graph.vertices();
        vertices.add("test3");

        Set<String> expected = new HashSet<>();
        expected.addAll(Arrays.asList("test1","test2"));
        assertEquals("expected 3 vertex in vertices",3, vertices.size());
        assertEquals("expected 2 vertex in graph",2, graph.vertices().size());
        assertTrue("expected graph vertices contains test1",expected.equals(graph.vertices()));
    }

    @Test
    public void testSourceEmptyGraph(){
        Graph<String> graph = emptyInstance();
/*         graph.add("test1");
        graph.add("test2");
        //assertFalse("expected false",remove);
        Set<String> vertices = graph.vertices();
        vertices.add("test3"); */
        Map<String, Integer> source = graph.sources("test1");
/*         Set<String> expected = new HashSet<>();
        expected.addAll(Arrays.asList("test1","test2")); */
        assertEquals("expected 0 source",0, source.size());
        //assertEquals("expected 2 vertex in graph",2, graph.vertices().size());
        //assertTrue("expected graph vertices contains test1",expected.equals(graph.vertices()));
    }

    @Test
    public void testSourceMissingEdgeGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.set("test2", "test3", 1);
        Map<String, Integer> source = graph.sources("test1");
/*         Set<String> expected = new HashSet<>();
        expected.addAll(Arrays.asList("test1","test2")); */
        assertEquals("expected 0 source",0, source.size());
        //assertEquals("expected 2 vertex in graph",2, graph.vertices().size());
        //assertTrue("expected graph vertices contains test1",expected.equals(graph.vertices()));
    }

    @Test
    public void testSourceEdgeToGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.set("test2", "test1", 3);
        Map<String, Integer> source = graph.sources("test1");
/*         Set<String> expected = new HashSet<>();
        expected.addAll(Arrays.asList("test1","test2")); */
        assertEquals("expected 1 source",1, source.size());
        int expected = source.get("test2");
        assertEquals("expected 3 weight",3, expected);
        //assertEquals("expected 2 vertex in graph",2, graph.vertices().size());
        //assertTrue("expected graph vertices contains test1",expected.equals(graph.vertices()));
    }

    @Test
    public void testSourceEdgeFromGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.set("test1", "test2", 1);
        Map<String, Integer> source = graph.sources("test1");
        assertEquals("expected 0 source",0, source.size());
    }

    @Test
    public void testTargetEmptyGraph(){
        Graph<String> graph = emptyInstance();

        Map<String, Integer> target = graph.targets("test1");

        assertEquals("expected 0 source",0, target.size());
    }

    @Test
    public void testTargetMissingEdgeGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.set("test2", "test3", 1);
        Map<String, Integer> target = graph.targets("test1");
/*         Set<String> expected = new HashSet<>();
        expected.addAll(Arrays.asList("test1","test2")); */
        assertEquals("expected 0 source",0, target.size());
        //assertEquals("expected 2 vertex in graph",2, graph.vertices().size());
        //assertTrue("expected graph vertices contains test1",expected.equals(graph.vertices()));
    }

    @Test
    public void testTargetEdgeToGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.set("test2", "test1", 3);
        Map<String, Integer> target = graph.targets("test1");
/*         Set<String> expected = new HashSet<>();
        expected.addAll(Arrays.asList("test1","test2")); */
        assertEquals("expected 0 target",0, target.size());
/*         int expected = target.get("test2");
        assertEquals("expected 3 weight",3, expected); */
        //assertEquals("expected 2 vertex in graph",2, graph.vertices().size());
        //assertTrue("expected graph vertices contains test1",expected.equals(graph.vertices()));
    }

    @Test
    public void testTargetEdgeFromGraph(){
        Graph<String> graph = emptyInstance();
        graph.add("test1");
        graph.add("test2");
        graph.add("test3");
        graph.set("test1", "test2", 3);
        Map<String, Integer> target = graph.targets("test1");
        assertEquals("expected 1 source",1, target.size());
        int expected = target.get("test2");
        assertEquals("expected 3 weight",3, expected);
    }

    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   source = target, source != target
    //   weight: <0, 0, >0
    
    // TODO tests for operations of Edge

    @Test
    public void testEdgeFail(){

        String source = "source";
        String target = "target";
        int weight = 0;

        IllegalArgumentException exception0 =  assertThrows(IllegalArgumentException.class, () ->{new Edge(source, target, -1);});
        assertEquals("source should not equal to target, source or target should not be null , weigth should be >0", exception0.getMessage());

        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () ->{new Edge(source, target, weight);});
        assertEquals("source should not equal to target, source or target should not be null , weigth should be >0", exception.getMessage());

        IllegalArgumentException exception2 =  assertThrows(IllegalArgumentException.class, () ->{new Edge(source, source, 1);});
        assertEquals("source should not equal to target, source or target should not be null , weigth should be >0", exception2.getMessage());

    }

    @Test
    public void testEdge(){
        String source = "source";
        String target = "target";
        int weight = 1;
        Edge edge = new Edge(source, target, weight);
        
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

        getSource += "gagagagg";
        getTarget += "hahahaha";
        getWeight += 1000000;

        assertEquals("expected source","source" ,edge.getSource());
        assertEquals("expected target","target" ,edge.getTarget());
        assertEquals("expected source",1 ,edge.getWeight());

    }

    
}
