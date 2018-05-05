/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

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
public class CheckIngredients implements Handler{
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;

    @Override
    public void processNode(NodeState state) {
        int resourcesNumber=0;
        List<NodeResources> receipeResources=nodeResourcesRepository.findByVersion(state.getCurrentNode().getVersion());
        List<NodeResources> currentInputResources=nodeResourcesRepository.findByNodeAndInputOutput(state.getCurrentNode(), "input");
        for (int i=0; i<currentInputResources.size(); i++)
        {
            for (int j=0; j<receipeResources.size(); j++)
            {
                if (currentInputResources.get(i).getResource()!=null&&
                        currentInputResources.get(i).getResource().equals(receipeResources.get(j).getResource()))
                    resourcesNumber++;
            }
        }   
        if (currentInputResources.size()==resourcesNumber)
        {
            state.setIsContainsOnlyInputResources(true);
        }
        else state.setIsContainsOnlyInputResources(false);
    }   
}
