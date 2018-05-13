/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

import com.netckracker.graph.manager.iterator.EdgesIterator;
import com.netckracker.graph.manager.iterator.NodeGraph;
import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.ReceipeVersion;
import com.netckracker.graph.manager.repository.EdgesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author eliza
 */
@Component
public  class BaseHandler implements Handler{
    @Autowired
    private NodeGraph nodeGraph;
    @Autowired
    private EdgesRepository edgeRepository;
    @Autowired
    private ComparsionOfNodeParameters comparsion;

    @Override
    public void processNode(NodeState state) {
        nodeGraph.reset();
        ReceipeVersion version=state.getReceipeVersion();
        state.setNodes(nodeGraph.getNodeList(version));
        EdgesIterator iterator=new EdgesIterator();
        iterator.setList(state.getNodes());
        while (iterator.hasNext())
        {
            Node node=iterator.getNext();
            List<Edges> edges=edgeRepository.findByEndNode(node);
            for (int i=0; i<edges.size(); i++)
            {
                state.setCurrentNode(node);
                state.setNextNode(edges.get(i).getStartNode());
                comparsion.processNode(state);
            } 
        }
    }
}
