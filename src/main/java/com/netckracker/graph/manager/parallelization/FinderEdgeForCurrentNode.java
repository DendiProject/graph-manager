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
public class FinderEdgeForCurrentNode implements Handler{
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;
    @Autowired
    private ComparsionResourcesOfCurrentNode comparsion;

    @Override
    public void processNode(NodeState state) {
        EdgesIterator iterator=new EdgesIterator();
        iterator.setList(state.getNodes());
        List<NodeResources> currentInputResources=nodeResourcesRepository.findByNodeAndInputOutput(state.getCurrentNode(), "input");
        state.setCurrentInputResources(currentInputResources);
        while (iterator.hasNext())
        {            
            Node node=iterator.getNext();
            state.setNode(node);
            comparsion.processNode(state);
        } 
    }
    
}
