/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

import com.netckracker.graph.manager.iterator.EdgesIterator;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.repository.NodeResourcesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author eliza
 */
@Component
public class FinderEdgeForNextNode implements Handler {
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;
    @Autowired
    private ComparsionResourcesOfNextNode comparsion;

    @Override
    public void processNode(NodeState state) {
        EdgesIterator iterator=new EdgesIterator();
        iterator.setList(state.getNodes());
        List<NodeResources> nextOutputResources=nodeResourcesRepository.findByNodeAndInputOutput(state.getNextNode(), "output");
        state.setNextOutputResources(nextOutputResources);
        while (iterator.hasNext())
        {            
            Node node=iterator.getNext();            
            if (node.equals(state.getNextNode()))
            {
                break;
            }
            state.setNode(node);
            comparsion.processNode(state);
        } 
    }
    
}
