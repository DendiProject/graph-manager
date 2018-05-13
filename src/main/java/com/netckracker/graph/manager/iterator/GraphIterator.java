/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.iterator;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.ReceipeVersion;

/**
 *
 * @author eliza
 */
public interface GraphIterator {
    
    
    public boolean hasNext();
    public void reset();
    public Node getNext();
    
}
