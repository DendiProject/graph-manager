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
public class ComparsionResourcesOfCurrentNode implements Handler{
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;
    @Autowired
    private NodeService nodeService;

    @Override
    public void processNode(NodeState state) {
        int equality=0;
        List<NodeResources> nodeOutputResources=nodeResourcesRepository.findByNodeAndInputOutput(state.getNode(), "output");
        for (int i=0; i<state.getCurrentInputResources().size(); i++)
            {
                for (int j=0; j<nodeOutputResources.size(); j++)
                {            
                    if (state.getCurrentInputResources().get(i).getResource()!=null&&nodeOutputResources.get(j).getResource()!=null)
                    {
                        if (nodeOutputResources.get(j).getResource().equals(state.getCurrentInputResources().get(i).getResource()))
                           equality++ ;
                    }
                }
                if (state.getCurrentInputResources().get(i).getPreviousNode()!=null)
                    {
                        if (state.getCurrentInputResources().get(i).getPreviousNode().equals(state.getNode()))
                           equality++;
                    } 
        }
        
        if (equality>0)
        {
            nodeService.createEdge( state.getNode().getNodeId(), state.getCurrentNode().getNodeId());
        }
    }
    
}
