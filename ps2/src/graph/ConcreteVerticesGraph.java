/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Graph: {V, E} 
    //   V: vertices where vertex v exists 
    //   E: edges from vertex v's targetMap where edge exists {source=v.getName(), target=v'.getName() ,weight = v.getTargetMap().get(v')}
    //      vertices v, v' from edge (both source and target) must exist in V
    // Representation invariant:
    //   Graph with v vertices can have <= v(v-1) edges
    // Safety from rep exposure:
    
    
    public boolean checkRep(){
        int edges = 0;
        for(Vertex<L> vertex : vertices){
            List<L> verticesName = vertices.stream().map(v -> v.getName()).collect(Collectors.toList()); 
            if(!verticesName.containsAll(vertex.getTargetMap().keySet())) return false;
            edges += vertex.getTargetMap().size();
        }
        return edges <= vertices.size()*(vertices.size() - 1);
    }

    public Vertex<L> getVertex(L name){
        for(Vertex<L> v : vertices){
            if(v.getName().equals(name)){
                return v;
            }
        }
        return null;
    }
    
    @Override public boolean add(L vertex) {
        if(getVertex(vertex)!=null){
            return false;
        }
        vertices.add(new Vertex<L>(vertex));

        assert checkRep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        if (getVertex(source)==null) vertices.add(new Vertex<L>(source));
        if (getVertex(target)==null) vertices.add(new Vertex<L>(target));

        Vertex<L> v = getVertex(source);
        int result = v.setTarget(target, weight);

        assert checkRep();
        return result;
    }
    
    @Override public boolean remove(L vertex) {
        if (getVertex(vertex)==null) return false;
        
        for(Vertex<L> v : vertices){
            v.setTarget(vertex, 0);
        }
        vertices.remove(getVertex(vertex));

        assert checkRep();
        return true;
    }
    
    @Override public Set<L> vertices() {
        Set<L> verticesName = vertices.stream().map(v -> v.getName()).collect(Collectors.toSet()); 

        assert checkRep();
        return verticesName;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        

        Map<L, Integer> sourceMap = new HashMap<>();

        if(getVertex(target)==null) return sourceMap;

        for(Vertex<L> v : vertices){
            //int weight = v.getTargetMap().get(target);

            if(v.getTargetMap().get(target)!=null){
                sourceMap.put(v.getName(), v.getTargetMap().get(target));
            }
/*             if(weight>0){
                sourceMap.put(v.getName(), weight);
            } */
        }

        assert checkRep();
        return sourceMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        
        Vertex<L> sourcVertex = getVertex(source);

        if(sourcVertex==null) return new HashMap<>();

        assert checkRep();
        return sourcVertex.getTargetMap(); 

        //throw new RuntimeException("not implemented");
    }
    
    @Override public String toString (){
        StringBuilder stringBuilder = new StringBuilder();

        vertices.forEach(vertex ->{
            stringBuilder.append("vertex:{");
            stringBuilder.append(vertex.getName() + ",");
            
            stringBuilder.append("}targets:{");
            vertex.getTargetMap().forEach( (target,weight) -> {
                stringBuilder.append(target + "," + weight);
            });

            stringBuilder.append("}");
        });

        return stringBuilder.toString();
    }
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // Abstraction function:
    //   vertex: {name, target}
    //   target: {vertex.name, weight} 
    //   target record represents an edge {source, target, weight} where source = vertex.name, target = targetVertex.name
    // Representation invariant:
    //   In target map, vertex.name != null, != vertex.name (source), weight > 0
    // Safety from rep exposure:
    //   name is immutable
    //   Map is return as deep copy which will not affect original map

    private final Map<L, Integer> targetMap = new HashMap<>();
    private final L name;
    
    public Vertex(L name){
        this.name = name;
        assert checkRep();
    }
    
    public boolean checkRep(){
        if(name == null) return false;
        for(Map.Entry<L, Integer> edge : targetMap.entrySet()){
            if(edge.getKey()==null || edge.getKey().equals(name)) return false;
            if(edge.getValue()<=0) return false;
        }
        return true;
    }
    
    public L getName(){
        return name;
    }

    public Map<L, Integer> getTargetMap(){
        return new HashMap<L, Integer>(targetMap);
    }

    public int setTarget(L target, int weight){

        if (targetMap.containsKey(target)) {

            if(weight==0){
                int oldWeight = targetMap.remove(target);
                assert checkRep();
                return oldWeight;
            } 
            
            int oldWeight = targetMap.get(target);
            targetMap.replace(target, weight);
            assert checkRep();
            return oldWeight;

        }

        if(weight<=0 || name.equals(target) || target==null){
             return -1;
        }

        targetMap.put(target, weight);
        assert checkRep();
        return 0;

    }
    
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("name: ");
        stringBuilder.append(name+" ->target:");
        for(Map.Entry<L, Integer> edge : targetMap.entrySet()){
            stringBuilder.append("{" + edge.getKey() + "weight: " +edge.getValue() + "}");
        }
        return stringBuilder.toString();
    }
    
}
