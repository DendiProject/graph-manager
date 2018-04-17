/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.service;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.NodeResources;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.ReceipeVersion;
import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.model.UserStep;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.repository.EdgesRepository;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.NodeResourcesRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ReceipeVersionRepository;
import com.netckracker.graph.manager.repository.ResourcesRepository;
import com.netckracker.graph.manager.repository.UserStepRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author eliza
 */
@Service
public class NodeServiceImpl implements NodeService{
    @Autowired
    private ReceipeVersionRepository versionRepository;
    @Autowired
    private ReceipeRepository receipeRepository;
    @Autowired
    private NodeResourcesRepository nodeResourcesRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private UserStepRepository userStepRepository;
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private EdgesRepository edgesRepository;
    @Autowired
    private Convertor convertor;

    @Override
    @Transactional
    public String createNode(String receipeId, String userId) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version = versionRepository.findByReceipeAndUserId(receipe, userId);
        Node node=new Node();
        Node savedNode=nodeRepository.save(node);
        UserStep step=new UserStep();
        step.setNode(savedNode);
        step.setVersion(version);
        step.setIsCompleted(false);
        userStepRepository.save(step);
        return savedNode.getNodeId();
    }

    @Override
    @Transactional
    public Edges createEdge(String startNodeId, String endNodeId, String receipeId, String userId) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version = versionRepository.findByReceipeAndUserId(receipe, userId);
        Node startNode=nodeRepository.findByNodeIdAndVersion(startNodeId, version);
        Node endNode=nodeRepository.findByNodeIdAndVersion(endNodeId, version);
        Edges edge=new Edges();
        edge.setStartNode(startNode);
        edge.setEndNode(endNode);
        Edges saved=edgesRepository.save(edge);
        return saved;
    }

    @Override
    @Transactional
    public void addInputOrOutputResourcesToNode(String receipeId, String userId, String nodeId, List<ResourceDto> resources, String inputOrOutput) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version = versionRepository.findByReceipeAndUserId(receipe, userId);
        Node node=nodeRepository.findByNodeIdAndVersion(nodeId, version);
        for (int i=0; i<resources.size(); i++)
        {
            NodeResources nodeResource=new NodeResources();
            Resources resource=resourcesRepository.findByResourceId(resources.get(i).getResourceId());
            nodeResource.setInputOrOutput(inputOrOutput);
            nodeResource.setNode(node);
            nodeResource.setNumberOfResource(resources.get(i).getResourceNumber());
        }        
   }    
}
