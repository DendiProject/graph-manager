/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.repository.NodeResourcesRepository;
import com.netckracker.graph.manager.service.NodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author eliza
 */
@Component
public class ComparsionResourcesOfNextNode implements Handler {
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;
    @Autowired
    private NodeService nodeService;

    @Override
    public void processNode(NodeState state) {
        int equality=0;
        List<NodeResources> nodeInputResources=nodeResourcesRepository.findByNodeAndInputOutput(state.getNode(), "input");
        for (int i=0; i<nodeInputResources.size(); i++)
        {
            for (int j=0; j<state.getNextOutputResources().size(); j++)
            {
                if (state.getNextOutputResources().get(j).getResource()!=null&&nodeInputResources.get(i).getResource()!=null)
                {
                    if (nodeInputResources.get(i).getResource().equals(state.getNextOutputResources().get(j).getResource()))
                       equality++ ;
                }
            }
            if (nodeInputResources.get(i).getPreviousNode()!=null)
                {
                    if (nodeInputResources.get(i).getPreviousNode().equals(state.getNextNode()))
                       equality++;
                } 
        }
        
        if (equality>0)
        {
            nodeService.createEdge(state.getNextNode().getNodeId(), state.getNode().getNodeId());
        }
    }
    
}
