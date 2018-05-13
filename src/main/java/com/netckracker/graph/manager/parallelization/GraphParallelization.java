/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

import com.netckracker.graph.manager.model.ReceipeVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author eliza
 */
@Component
public class GraphParallelization {
    @Autowired
    private BaseHandler handler;


    public GraphParallelization() {
    }
    
    public void paralellGraph(ReceipeVersion version)
    {
            NodeState state=new NodeState();
            state.setReceipeVersion(version);
            handler.processNode(state);
        
    }
}
