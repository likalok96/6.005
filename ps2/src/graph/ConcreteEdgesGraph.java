/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Graph: {V, E} 
    //   V: vertices where vertex v exists 
    //   E: edges where edge e exists {source=e.getSoruce() , target=e.getSoruce() ,weight=e.getWeight()}
    // Representation invariant:
    //   Graph with v vertices can have <= v(v-1) edges
    //   vertices v in edges, vertices must contain v.source and v.target
    //   (edges only have non-zero positive weigth) 
    // Safety from rep exposure:
    //   set: create new edge which is immutable to replace old one
    //   vertices: return defensive copy
    //   source & target: Map is constructed with permitive value in edge which is immutable
    

    /**
     * check Rep invariant
     * @return true if statisfy Rep invariant else false
     */
    public boolean checkRep(){
        boolean edgesNumberCheck = edges.size()<=vertices.size()*(vertices.size()-1);
        boolean verticesCheck = true;

        for(Edge<L> edge : edges){
            verticesCheck = vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget()) ;
        } 

        return edgesNumberCheck && verticesCheck;
    }

    /**
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @return index of edge(soruce, target) from edges or -1 if not exist
     */

    public int findEdge(L source, L target){
        for(int i=0;i<edges.size();i++){
            if(edges.get(i).getSource().equals(source) && edges.get(i).getTarget().equals(target)){
                return i;
            }
        }
        return -1;
    }
    
    @Override public boolean add(L vertex) {
        if(vertices.contains(vertex)){
            return false;
        }
        vertices.add(vertex);
        assert checkRep();
        return true;
        //throw new RuntimeException("not implemented");
    }
    
    @Override public int set(L source, L target, int weight) {
        int index = findEdge(source, target);

        if(source.equals(target) || weight < 0){
            assert checkRep();
            return -1;
        }
        
        if(index==-1){
            if(!vertices.contains(source)) vertices.add(source);
            if(!vertices.contains(target)) vertices.add(target);
            if(weight>0) {
                edges.add(new Edge<L>(source, target, weight));
            }
            assert checkRep();
            return 0;
        }

        int oldWeight = edges.get(index).getWeight();

        if(weight==0){
            edges.remove(index);
            assert checkRep();
            return 0;
        }

        edges.set(index, new Edge<L>(source, target, weight));

        assert checkRep();

        return oldWeight;
        //throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(L vertex) {
        if(!vertices.contains(vertex)) return false;

        vertices.remove(vertex);

        for(int i=0;i<edges.size();i++){
            if (edges.get(i).getSource().equals(vertex) || edges.get(i).getTarget().equals(vertex)) {
                edges.remove(i);
                i--;
            }
        }

        assert checkRep();

        return true;
    }
    
    @Override public Set<L> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourcesMap = new HashMap<>();

        edges.forEach(edge-> {
            if(edge.getTarget().equals(target)){
                sourcesMap.put(edge.getSource(), edge.getWeight());
            }
        });
        return sourcesMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targetMap = new HashMap<>();

        edges.forEach(edge-> {
            if(edge.getSource().equals(source)){
                targetMap.put(edge.getTarget(), edge.getWeight());
            }
        });
        return targetMap;
        //throw new RuntimeException("not implemented");
    }
    
    @Override public String toString (){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("vertices:{");
        vertices.forEach(vertex -> stringBuilder.append(vertex + ","));
        stringBuilder.append("}edges:{");
        edges.forEach(edge -> stringBuilder.append(edge.toString()));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    private final L source;
    private final L target;
    private final int weight;


    // Abstraction function:
    //   edge : {source, target, weight}
    // Representation invariant:
    //   weigth >0
    //   source != target
    //   source != null, target !=null
    // Safety from rep exposure:
    //   immutable type
    //   implement with primitive value which is also immutable
    
    public Edge(L source, L target, int weight) throws IllegalArgumentException{
        this.source = source;
        this.target = target;
        this.weight = weight;
        if(!checkRep()) throw new IllegalArgumentException("source should not equal to target, source or target should not be null , weigth should be >0");

    }
    
    public boolean checkRep(){

        return !source.equals(target) && weight>0 && source!=null && target!=null;
    }
    
    public L getSource(){
        return source;
    }

    public L getTarget(){
        return target;
    }

    public int getWeight(){
        return weight;
    }
    
    public String toString(){
        return source  + " ," + weight + "->" + target;
    }
    
}
