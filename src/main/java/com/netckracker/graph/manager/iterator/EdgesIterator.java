/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.iterator;

import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.model.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eliza
 */

public class EdgesIterator implements GraphIterator{
    private int currentPosition = 0;
    private Node currentNode;
    int size;
    
    private List<Edges> edges=new ArrayList<>();
    List<Node> nodes=new ArrayList<>();

    public EdgesIterator() {
        this.currentPosition=0;
    }
    public void setList(List<Node> nodes)
    {
        this.nodes=nodes;
    }
     

    @Override
    public boolean hasNext() {
        return (nodes.size()>currentPosition) ;
    }

    @Override
    public Node getNext() {
        if (hasNext())
        {
            Node node=nodes.get(currentPosition);
            currentNode=node;
            currentPosition++;
            return currentNode;
        }
        else return null;
    }
    
    @Override
    public void reset() {
        currentPosition=0;
    }
    
}
