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
import java.util.List;
import java.util.Map;
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
    private ReceipeService receipeService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private GraphParallelization parallelization;
    
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
        Edges edge1=edgesRepository.findByStartNodeAndEndNode(startNode, endNode);
        Edges edge2=edgesRepository.findByStartNodeAndEndNode(endNode, startNode);
        if (edge1==null&&edge2==null){
            Edges edge=new Edges();
            edge.setStartNode(startNode);
            edge.setEndNode(endNode);
            edgesRepository.save(edge);
        }        
    }

    @Override
    @Transactional
    public void addInputOrOutputResourcesToNode(String nodeId, List<ResourceDto> resources, String inputOrOutput) {        
        Node node=nodeRepository.findByNodeId(nodeId);        
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
        else 
        {
            edge=edgesRepository.findByStartNodeAndEndNode(endNode, startNode);
            if (edge!=null)
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
        userId="1111";
        /*Создаем каталог и рецепт*/
        String catalogId=catalogService.createCatalog("Пироги", "description");
        ReceipeDto receipe=receipeService.createReceipe("Шарлотка", "description", catalogId, userId, true);
        receipeId=receipe.getReceipeId();
        
        /*Создаем ингредиенты рецепта*/
        String ingredientId1=resourceService.createResource("Мука", userId, "г", "ingredient", "12345");
        String ingredientId2=resourceService.createResource("Яйцо", userId, "шт", "ingredient", "12346");
        String ingredientId3=resourceService.createResource("Сахар", userId, "г", "ingredient", "12347");
        String ingredientId4=resourceService.createResource("Яблоко", userId, "шт", "ingredient", "12348");
        String ingredientId5=resourceService.createResource("Соль", userId, "г", "ingredient", "12348");
        String ingredientId6=resourceService.createResource("Сода", userId, "г", "ingredient", "12348");
        
        /*Создаем ресурсы рецепта*/
        String resourceId1=resourceService.createResource("Духовка", userId, "шт", "resource", "12345");
        String resourceId2=resourceService.createResource("Форма для запекания", userId, "шт", "resource", "12346");
        
        /*Добавляем ингредиенты к рецепту*/
        receipeService.addReceipeResources(receipeId, userId, ingredientId1, 300);
        receipeService.addReceipeResources(receipeId, userId, ingredientId2, 4);
        receipeService.addReceipeResources(receipeId, userId, ingredientId3, 300);
        receipeService.addReceipeResources(receipeId, userId, ingredientId4, 5);
        receipeService.addReceipeResources(receipeId, userId, ingredientId5, 10);
        receipeService.addReceipeResources(receipeId, userId, ingredientId6, 10);
        receipeService.addReceipeResources(receipeId, userId, resourceId1, 1);
        receipeService.addReceipeResources(receipeId, userId, resourceId2, 1);
        
        /*Создаем ноды рецепта*/
        String nodeId1=createNode(receipe.getReceipeId(), userId);
        String nodeId2=createNode(receipe.getReceipeId(), userId);
        String nodeId3=createNode(receipe.getReceipeId(), userId);
        String nodeId4=createNode(receipe.getReceipeId(), userId);
        String nodeId5=createNode(receipe.getReceipeId(), userId);
        String nodeId6=createNode(receipe.getReceipeId(), userId);
        String nodeId7=createNode(receipe.getReceipeId(), userId);
        String nodeId8=createNode(receipe.getReceipeId(), userId);
        String nodeId9=createNode(receipe.getReceipeId(), userId);
        String nodeId10=createNode(receipe.getReceipeId(), userId);
        String nodeId11=createNode(receipe.getReceipeId(), userId);
        
        /*Создаем описание нод*/
        addNodeDescription(nodeId1, "Отделить белки от желтков.");
        addNodeDescription(nodeId2, "Белки взбить с половиной стакана сахара.");
        addNodeDescription(nodeId3, "Желтки взбить с оставшимся сахаром.");
        addNodeDescription(nodeId4, "Все смешать и добавить муку.");
        addNodeDescription(nodeId5, "Добавить к предыдущему шагу соль и соду.");
        addNodeDescription(nodeId6, "Нарезать яблоки.");
        addNodeDescription(nodeId7, "Добавить к тесту яблоки.");
        addNodeDescription(nodeId8, "Разогреть духовку.");
        addNodeDescription(nodeId9, "Залить тесто в форму для выпечки.");
        addNodeDescription(nodeId10, "Выпекать в духовке 40 мин.");
        addNodeDescription(nodeId11, "Готово!");
        
        /*Додавляем id картинок к нодам*/
        addNodePicture(nodeId1, "nodepictureId1");
        addNodePicture(nodeId2, "nodepictureId2");
        addNodePicture(nodeId3, "nodepictureId3");
        addNodePicture(nodeId4, "nodepictureId4");
        addNodePicture(nodeId5, "nodepictureId5");
        addNodePicture(nodeId6, "nodepictureId6");
        addNodePicture(nodeId7, "nodepictureId7");
        addNodePicture(nodeId8, "nodepictureId8");
        addNodePicture(nodeId9, "nodepictureId9");
        addNodePicture(nodeId10, "nodepictureId10");
        addNodePicture(nodeId11, "nodepictureId11");
        
        /*Создаем связи*/
        createEdge(nodeId1, nodeId3);
        createEdge(nodeId2, nodeId3);
        createEdge(nodeId3, nodeId4);
        createEdge(nodeId4, nodeId5);
        createEdge(nodeId5, nodeId7);
        createEdge(nodeId6, nodeId7);
        createEdge(nodeId7, nodeId9);
        createEdge(nodeId8, nodeId10);
        createEdge(nodeId9, nodeId10);
        createEdge(nodeId10, nodeId11);
        
        GraphDto graph=getReceipeGraph(receipe.getReceipeId(), userId);
        return graph;
        
    }

    @Override
    public GraphDto getReceipeParallelGraph(String receipeId, String userId) {
        GraphDto graph;
        Receipe receipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version =versionRepository.findByReceipeAndUserId(receipe, userId);
        if (version==null)
        {
            ReceipeVersion mainVersion=versionRepository.findByReceipeAndIsMainVersion(receipe, true);           
            ReceipeVersion newVersion=new ReceipeVersion();
            newVersion.setIsMainVersion(false);
            newVersion.setUserId(userId);
            newVersion.setReceipe(receipe);
            ReceipeVersion savedVersion=versionRepository.save(newVersion);
            try {
                copyReceipeVersion(mainVersion, savedVersion);
                parallelization.paralellGraph(savedVersion);
                graph=getReceipeGraph(receipe.getReceipeId(), savedVersion.getUserId());
                return graph;
            } catch (IOException ex) {
                Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NodeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else 
        {
            parallelization.paralellGraph(version);
            graph=getReceipeGraph(receipe.getReceipeId(), version.getUserId());
            return graph;
        }
        return null;
        
    }
    
    @Transactional
    private void copyReceipeVersion(ReceipeVersion fromVersion, ReceipeVersion toVersion) throws IOException, ClassNotFoundException
    {
        Map<Node, Node> oldAndNewNodes=new HashMap<>();
        List<Node> nodes=nodeRepository.findByVersion(fromVersion);
        List<NodeResources> receipeResources=nodeResourcesRepository.findByVersion(fromVersion);
        Map<NodeResources, NodeResources> oldAndNewResources=new HashMap<>();
        
        /*копируем ресурсы рецепта*/
        for (int i=0; i<receipeResources.size(); i++)
        {
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
        }
        
        /*копируем ноды*/
        for (int i=0; i<nodes.size(); i++)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
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
        }
        
        /*копируем ресурсы ноды*/
        List<NodeResources> resources=new ArrayList<>();
        for (int i=0; i<nodes.size(); i++)
        {
            List<NodeResources> nodeResources=nodeResourcesRepository.findByNode(nodes.get(i));
            
            for (int j=0; j<nodeResources.size(); j++)
            {
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                ObjectOutputStream ous1 = new ObjectOutputStream(baos1);
                ous1.writeObject(nodeResources.get(j));
                ous1.close();

                ByteArrayInputStream bais1= new ByteArrayInputStream(baos1.toByteArray());
                ObjectInputStream ois1 = new ObjectInputStream(bais1);
                NodeResources nodeResource=(NodeResources)ois1.readObject();
                if (nodeResource.getNode()!=null)
                {
                    nodeResource.setNode(oldAndNewNodes.get(nodes.get(i)));                              
                }
                if (nodeResource.getVersion()!=null)
                {
                        nodeResource.setVersion(toVersion);
                }
                if (nodeResource.getPreviousNode()!=null)
                {
                    nodeResource.setPreviousNode(oldAndNewNodes.get(nodeResource.getPreviousNode()));
                }
                nodeResource.setNodeResourceId(null);
                NodeResources saved=nodeResourcesRepository.save(nodeResource);
                resources.add(saved);
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
}
