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
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.parallelization.GraphParallelization;
import com.netckracker.graph.manager.repository.EdgesRepository;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.NodeResourcesRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ReceipeVersionRepository;
import com.netckracker.graph.manager.repository.ResourcesRepository;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @Autowired
    private GraphParallelization parallelization;
    @Autowired
    private TopOfReceipesService topService;
    
    @Override
    @Transactional
    public String createNode(String receipeId, String userId) {
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        if (receipe!=null)
        {
            ReceipeVersion version = versionRepository.findByReceipeAndUserId(receipe, userId);
            if (version!=null)
            {
                Node node=new Node();
                node.setVersion(version);
                Node savedNode=nodeRepository.save(node);
                return savedNode.getNodeId();
            }
            else return null;
        }        
        else return null;
    }

    @Override
    @Transactional
    public void createEdge(String startNodeId, String endNodeId) {
        Node startNode=nodeRepository.findByNodeId(startNodeId);
        Node endNode=nodeRepository.findByNodeId(endNodeId);
        if (startNode!=null&&endNode!=null)
        {
            Edges edge1=edgesRepository.findByStartNodeAndEndNode(startNode, endNode);
            Edges edge2=edgesRepository.findByStartNodeAndEndNode(endNode, startNode);
            if (edge1==null&&edge2==null){
                Edges edge=new Edges();
                edge.setStartNode(startNode);
                edge.setEndNode(endNode);
                edgesRepository.save(edge);
            }   
        }             
    }

    @Override
    @Transactional
    public void addInputOrOutputResourcesToNode(String nodeId, List<ResourceDto> resources, String inputOrOutput) {        
        Node node=nodeRepository.findByNodeId(nodeId);    
        if (node!=null)
        {
            for (int i=0; i<resources.size(); i++)
            {               
                NodeResources nodeResource=new NodeResources();
                if (resources.get(i).getResourceId()!=null)
                {
                    Resources resource=resourcesRepository.findByResourceId(resources.get(i).getResourceId());
                    nodeResource.setResource(resource);
                }
                if (resources.get(i).getPreviousNodeId()!=null)
                {
                    Node previousNode=nodeRepository.findByNodeId(resources.get(i).getPreviousNodeId());
                    nodeResource.setPreviousNode(previousNode);
                }            
                nodeResource.setInputOrOutput(inputOrOutput);
                nodeResource.setNode(node);            
                nodeResource.setNumberOfResource(resources.get(i).getResourceNumber());
                nodeResourcesRepository.save(nodeResource);
            }      
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
        if (receipe!=null)
        {
            topService.increaseCounter(receipe, userId);
            ReceipeVersion version = versionRepository.findByReceipeAndUserId(receipe, userId);
            if (version==null)
            {
                version=versionRepository.findByReceipeAndIsMainVersion(receipe, true);
            }
            if (version!=null)
            {
                GraphDto graph=new GraphDto();
                graph.setReceipeName(receipe.getName());
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
        }   
        return null;
    }

    @Override
    @Transactional
    public void deleteNode(String nodeId) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            List<Edges> edges=edgesRepository.findByStartNodeOrEndNode(node, node);
            for (int i=0; i<edges.size();i++)
            {
                edgesRepository.delete(edges.get(i));
            }
            List<NodeResources> resources =nodeResourcesRepository.findByNode(node);
            for (int i=0; i<resources.size();i++)
            {
                nodeResourcesRepository.delete(resources.get(i));
            }
            nodeRepository.delete(node);
        }
    }

    @Override
    @Transactional
    public void deleteEdge(String startNodeId, String endNodeId) {
        Node startNode=nodeRepository.findByNodeId(startNodeId);
        Node endNode=nodeRepository.findByNodeId(endNodeId);
        if (startNode!=null&&endNode!=null)
        {
            Edges edge=edgesRepository.findByStartNodeAndEndNode(startNode, endNode);
            if (edge!=null)
            {
                edgesRepository.delete(edge);
            }
            else 
            {
                edge=edgesRepository.findByStartNodeAndEndNode(endNode, startNode);
                if (edge!=null)
                    edgesRepository.delete(edge);
            }
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
            List<NodeResources> resources = nodeResourcesRepository.findByInputOutputAndNode(inputOrOutput,ingredientOrResource, node.getNodeId());
            return resources.stream()
               .map(resource->convertor.convertNodeResourceToDto(resource))
               .collect(Collectors.toList());
        }
        else return null;              
    }

    @Override
    @Transactional
    public GraphDto getReceipeTestGraph(String receipeId, String userId) {
        userId="1";
        Receipe receipe=receipeRepository.findByReceipeId("1");        
        GraphDto graph=getReceipeGraph(receipe.getReceipeId(), userId);
        return graph;
        
    }

    @Override
    public GraphDto getReceipeParallelGraph(String receipeId, String userId) {
        GraphDto graph;
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        if (receipe!=null)
        {
            ReceipeVersion version =versionRepository.findByReceipeAndUserId(receipe, userId);
            if (version==null)
            {
                ReceipeVersion mainVersion=versionRepository.findByReceipeAndIsMainVersion(receipe, true);           
                ReceipeVersion newVersion=new ReceipeVersion();
                newVersion.setIsMainVersion(false);
                newVersion.setUserId(userId);
                newVersion.setReceipe(receipe);
                ReceipeVersion savedVersion=versionRepository.save(newVersion);
                    copyReceipeVersion(mainVersion, savedVersion);
                    if (savedVersion.isIsParalell()==false)
                    {
                        parallelization.paralellGraph(savedVersion);
                        savedVersion.setIsParalell(true);
                        versionRepository.save(savedVersion);
                    }                
                    graph=getReceipeGraph(receipe.getReceipeId(), savedVersion.getUserId());
                    return graph;
            }
            else 
            {
                if (version.isIsParalell()==false)
                {
                     parallelization.paralellGraph(version);
                     version.setIsParalell(true);
                     versionRepository.save(version);
                }

                graph=getReceipeGraph(receipe.getReceipeId(), version.getUserId());
                return graph;
            }            
        }    
        else return null;
    }
    
    @Override
    @Transactional
    public void copyReceipeVersion(ReceipeVersion fromVersion, ReceipeVersion toVersion) 
    {
        Map<Node, Node> oldAndNewNodes=new HashMap<>();
        List<Node> nodes=nodeRepository.findByVersion(fromVersion);
        List<NodeResources> receipeResources=nodeResourcesRepository.findByVersion(fromVersion);
        Map<NodeResources, NodeResources> oldAndNewResources=new HashMap<>();
        
        /*копируем ресурсы рецепта*/
        for (int i=0; i<receipeResources.size(); i++)
        {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream ous = new ObjectOutputStream(baos);
                ous.writeObject(receipeResources.get(i));
                ous.close();
                
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                NodeResources nodeResource=(NodeResources)ois.readObject();
                nodeResource.setVersion(toVersion);
                nodeResource.setNodeResourceId(null);
                
                NodeResources saved=nodeResourcesRepository.save(nodeResource);
                oldAndNewResources.put(receipeResources.get(i), saved);
            } catch (IOException ex) {
                Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*копируем ноды*/
        for (int i=0; i<nodes.size(); i++)
        {
            ObjectOutputStream ous = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ous = new ObjectOutputStream(baos);
                ous.writeObject(nodes.get(i));
                ous.close();
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Node node=(Node)ois.readObject();
                node.setNodeId(null);
                node.setVersion(toVersion);
                Node saved=nodeRepository.save(node);
                System.out.println(saved.getNodeId());
                oldAndNewNodes.put(nodes.get(i), saved);
            } catch (IOException ex) {
                Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    ous.close();
                } catch (IOException ex) {
                    Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        /*копируем ресурсы ноды*/
        List<NodeResources> resources=new ArrayList<>();
        for (int i=0; i<nodes.size(); i++)
        {
            List<NodeResources> nodeResources=nodeResourcesRepository.findByNode(nodes.get(i));
            
            for (int j=0; j<nodeResources.size(); j++)
            {
                ObjectOutputStream ous1 = null;
                try {
                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                    ous1 = new ObjectOutputStream(baos1);
                    ous1.writeObject(nodeResources.get(j));
                    ous1.close();
                    ByteArrayInputStream bais1= new ByteArrayInputStream(baos1.toByteArray());
                    ObjectInputStream ois1 = new ObjectInputStream(bais1);
                    NodeResources nodeResource=(NodeResources)ois1.readObject();
                    if (nodeResource.getNode()!=null)
                    {
                        nodeResource.setNode(oldAndNewNodes.get(nodes.get(i)));
                    }   if (nodeResource.getVersion()!=null)
                    {
                        nodeResource.setVersion(toVersion);
                    }   if (nodeResource.getPreviousNode()!=null)
                    {
                        nodeResource.setPreviousNode(oldAndNewNodes.get(nodeResource.getPreviousNode()));
                    }   nodeResource.setNodeResourceId(null);
                    NodeResources saved=nodeResourcesRepository.save(nodeResource);
                    resources.add(saved);
                } catch (IOException ex) {
                    Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        ous1.close();
                    } catch (IOException ex) {
                        Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        /*копируем связи*/
        for(int i=0; i<nodes.size();i++)
        {
            for (int j=0;j<nodes.size(); j++)
            {
                Edges edge=edgesRepository.findByStartNodeAndEndNode(nodes.get(i), nodes.get(j));
                if (edge!=null)
                {
                    Node startNode=oldAndNewNodes.get(edge.getStartNode());
                    Node endNode=oldAndNewNodes.get(edge.getEndNode());
                    createEdge(startNode.getNodeId(), endNode.getNodeId());
                }
                else 
                {
                    Edges edge1=edgesRepository.findByStartNodeAndEndNode(nodes.get(j), nodes.get(i));
                    if (edge1!=null)
                    {
                        Node startNode=oldAndNewNodes.get(edge1.getStartNode());
                        Node endNode=oldAndNewNodes.get(edge1.getEndNode());
                        createEdge(startNode.getNodeId(), endNode.getNodeId());
                    }
                }                
            }            
        }
    }

    @Transactional
    @Override
    public void addNodeLabel(String nodeId, String label) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            node.setLabel(label);
            nodeRepository.save(node);
        }
    }

    @Override
    public boolean isNodeExcist(String nodeId) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node!=null)
        {
            return true;
        }
        else return false;
    }

    @Override
    @Transactional
    public Node getDefaultNode() {
        Node node=nodeRepository.findByNodeId("defaultNodeId");
        if (node!=null)
        {
            return node;
        }
        return null;
    }

    @Override
    public boolean isLastNode(String nodeId) {
        Node node=nodeRepository.findByNodeId(nodeId);
        if (node.getVersion()!=null)
        {
            Node lastNode=nodeRepository.findByLastNode(node.getVersion().getVersionId());
            if (lastNode!=null&node!=null)
            {
                if (lastNode.getNodeId().equals(node.getNodeId()));
                {
                    return true;
                }
            }
        }        
        return false;
    }

    @Override
    public ReceipeDto getNotCompletedReceipe(String userId) {
        /*Set<ReceipeVersion> versions=versionRepository.findNotCompletedReceipe(userId);
        Iterator iter=versions.iterator();
        while(iter.hasNext())
        {
            ReceipeVersion version=(ReceipeVersion) iter.next();
            System.out.println("no compl "+version.getReceipe().getName());
        }
        Iterator iter2=versions.iterator();
        while(iter2.hasNext())
        {
            ReceipeVersion version=(ReceipeVersion) iter2.next();
            return convertor.convertReceipeToDto(version.getReceipe());
        }*/
        Receipe receipe=receipeRepository.findNotCompletedReceipe(userId);
        if (receipe!=null)
        {
            return convertor.convertReceipeToDto(receipe);            
        }
        return null;
    }
    
    
    
}
