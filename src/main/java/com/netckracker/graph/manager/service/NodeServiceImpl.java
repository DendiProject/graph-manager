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
import com.netckracker.graph.manager.modelDto.GraphDto;
import com.netckracker.graph.manager.modelDto.NodeDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.repository.EdgesRepository;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.NodeResourcesRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ReceipeVersionRepository;
import com.netckracker.graph.manager.repository.ResourcesRepository;
import java.util.List;
import java.util.stream.Collectors;
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
        node.setVersion(version);
        Node savedNode=nodeRepository.save(node);
        return savedNode.getNodeId();
    }

    @Override
    @Transactional
    public void createEdge(String startNodeId, String endNodeId) {
        Node startNode=nodeRepository.findByNodeId(startNodeId);
        Node endNode=nodeRepository.findByNodeId(endNodeId);
        Edges edge=new Edges();
        edge.setStartNode(startNode);
        edge.setEndNode(endNode);
        edgesRepository.save(edge);
    }

    @Override
    @Transactional
    public void addInputOrOutputResourcesToNode(String nodeId, List<ResourceDto> resources, String inputOrOutput) {
        
        Node node=nodeRepository.findByNodeId(nodeId);
        
        for (int i=0; i<resources.size(); i++)
        {
            NodeResources nodeResource=new NodeResources();
            Resources resource=resourcesRepository.findByResourceId(resources.get(i).getResourceId());
            nodeResource.setInputOrOutput(inputOrOutput);
            nodeResource.setNode(node);
            nodeResource.setResource(resource);
            nodeResource.setNumberOfResource(resources.get(i).getResourceNumber());
            nodeResourcesRepository.save(nodeResource);
        }      
   }    

    @Override
    @Transactional
    public void addNodeDescription(String nodeId, String description) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            node.setDescription(description);
            nodeRepository.save(node);
        }
    }

    @Override
    public GraphDto getReceipeGraph(String receipeId, String userId) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version = versionRepository.findByReceipeAndUserId(receipe, userId);
        GraphDto graph=new GraphDto();
        List<Node> nodes=nodeRepository.findByVersion(version);
        List<NodeResources> resources=nodeResourcesRepository.findByVersion(version);
        for (int i=0; i<resources.size(); i++)
        {
            if (resources.get(i).getResource().getIngredientOrResource().equals("ingredient"))
            {
                ResourceDto ingredient=convertor.convertNodeResourceToDto(resources.get(i));
                graph.getIndredients().add(ingredient);
            }
            if (resources.get(i).getResource().getIngredientOrResource().equals("resource"))
            {
                ResourceDto resource=convertor.convertNodeResourceToDto(resources.get(i));
                graph.getResources().add(resource);
            }
        }     
        List<NodeDto> nodeDtos=nodes.stream().map(node->convertor.convertNodeToDto(node))
               .collect(Collectors.toList());
        graph.setNodes(nodeDtos);
        
        for(int i=0; i<nodes.size();i++)
        {
            for (int j=0;j<nodes.size(); j++)
            {
                Edges edge=edgesRepository.findByStartNodeAndEndNode(nodes.get(i), nodes.get(j));
                if (edge!=null)
                {
                    graph.getEdges().add(convertor.convertEgdeToDto(edge));
                }
                else 
                {
                    Edges edge1=edgesRepository.findByStartNodeAndEndNode(nodes.get(j), nodes.get(i));
                    if (edge1!=null)
                    {
                    graph.getEdges().add(convertor.convertEgdeToDto(edge1));
                    }
                }                
            }            
        }        
        return graph;
    }

    @Override
    @Transactional
    public void deleteNode(String nodeId) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            nodeRepository.delete(node);
        }
    }

    @Override
    @Transactional
    public void deleteEdge(String startNodeId, String endNodeId) {
        Node startNode=nodeRepository.findByNodeId(startNodeId);
        Node endNode=nodeRepository.findByNodeId(endNodeId);
        Edges edge=edgesRepository.findByStartNodeAndEndNode(startNode, endNode);
        if (edge!=null)
        {
            edgesRepository.delete(edge);
        }
    } 

    @Override
    @Transactional
    public void addNodePicture(String nodeId, String pictureId) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            node.setPictureId(pictureId);
            nodeRepository.save(node);
        }
    }

    @Override
    public List<ResourceDto> getNodesResources(String nodeId, String inputOrOutput, String ingredientOrResource) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            List<NodeResources> resources = nodeResourcesRepository.findByInputOutputAndNode(inputOrOutput,ingredientOrResource, nodeId);
            return resources.stream()
               .map(resource->convertor.convertNodeResourceToDto(resource))
               .collect(Collectors.toList());
        }
        else return null;              
    }
}
