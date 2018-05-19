/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.modelDto;

import com.netckracker.graph.manager.model.Edges;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author eliza
 */
public class GraphDto {
    private List<ResourceDto> resources;
    private List<ResourceDto> indredients;
    private List<NodeDto> nodes;
    private Set<EdgeDto> edges;
    private String receipeName;
    private boolean isParallel;
     
     

    public GraphDto() {
        this.edges=new HashSet<>();
        this.nodes=new ArrayList<>();
        this.resources=new ArrayList<>();
        this.indredients=new ArrayList<>();
    }

    public boolean isIsParallel() {
        return isParallel;
    }

    public void setIsParallel(boolean isParallel) {
        this.isParallel = isParallel;
    }

    
    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public List<ResourceDto> getIndredients() {
        return indredients;
    }

    public void setIndredients(List<ResourceDto> indredients) {
        this.indredients = indredients;
    }

    public List<NodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDto> nodes) {
        this.nodes = nodes;
    }

    public Set<EdgeDto> getEdges() {
        return edges;
    }

    public void setEdges(Set<EdgeDto> edges) {
        this.edges = edges;
    }

    public String getReceipeName() {
        return receipeName;
    }

    public void setReceipeName(String receipeName) {
        this.receipeName = receipeName;
    }
    
    

   
    
}
