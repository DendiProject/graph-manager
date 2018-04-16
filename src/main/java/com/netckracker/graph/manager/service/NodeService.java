/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface NodeService {
    public String createNode(String receipeId, String userId);
    public String createEdge(String startNodeIs, String endNodeId, String receipeId, String userId);
    public void addInputResourcesToNode(String receipeId, String userId,String nodeId,List<Resources> resources, double resourceNumber );
}
