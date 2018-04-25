/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.modelDto.GraphDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import java.util.List;

/**
 *
 * @author eliza
 */
public interface NodeService {
    public String createNode(String receipeId, String userId);
    public void createEdge(String startNodeId, String endNodeId);
    public void addNodeDescription(String nodeId, String description);
    public void addInputOrOutputResourcesToNode(String nodeId , List<ResourceDto> resources , String inputOrOutput);
    public GraphDto getReceipeGraph(String receipeId, String userId);
    public void deleteNode(String nodeId);
    public void deleteEdge(String startNodeId, String endNodeId);
    public void addNodePicture(String nodeId, String pictureId);
    public List<ResourceDto> getNodesResources(String nodeId, String inputOrOutput, String ingredientOrResource);
    public GraphDto getReceipeTestGraph(String receipeId, String userId);
}
