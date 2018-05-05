/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.parallelization;

import com.netckracker.graph.manager.model.ReceipeVersion;

/**
 *
 * @author eliza
 */
public interface Handler {
    
    public void processNode(NodeState state);
}
