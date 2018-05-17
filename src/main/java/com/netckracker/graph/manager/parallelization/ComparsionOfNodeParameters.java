/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.model.ReceipeVersion;
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
public class ComparsionOfNodeParameters implements Handler{
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;
    @Autowired
    private FinderEdgeForNextNode nextFinder;
    @Autowired
    private FinderEdgeForCurrentNode currentFinder;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private CheckIngredients checkIngredients;

    @Override
    public void processNode(NodeState state) {
       int equality=0;
        if (state.getNextNode()!=null)
        {
            List<NodeResources> currentInputResources=nodeResourcesRepository.findByNodeAndInputOutput(state.getCurrentNode(), "input");
            List<NodeResources> nextOutputResources=nodeResourcesRepository.findByNodeAndInputOutput(state.getNextNode(), "output");
            for (int i=0; i<currentInputResources.size(); i++)
            {
                for (int j=0; j<nextOutputResources.size(); j++)
                {
                    if (currentInputResources.get(i).getResource()!=null&nextOutputResources.get(j).getResource()!=null)
                    {                       
                        if ((currentInputResources.get(i).getResource().equals(nextOutputResources.get(j).getResource())))
                        {
                            equality++;
                        }                
                    }                                  
                }
                if (currentInputResources.get(i).getPreviousNode()!=null)
                    {
                        if (currentInputResources.get(i).getPreviousNode().getNodeId().equals(state.getNextNode().getNodeId()))
                        {                            
                            System.out.println("equality++");
                            equality++;
                        }                    
                    }   
            }

            if (equality>0)        
            {  
                
            }
            else 
            { 
                nextFinder.processNode(state);
                checkIngredients.processNode(state);
            if (state.isIsContainsOnlyInputResources()==false)
                    { 
                       currentFinder.processNode(state);
                    }
            nodeService.deleteEdge(state.getNextNode().getNodeId(), state.getCurrentNode().getNodeId());  
            }
        }        
    }


}
