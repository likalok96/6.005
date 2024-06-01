/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
/*     public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    } */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
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
        int set = graph.set("test1","test2",-1);
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
    
    
}
