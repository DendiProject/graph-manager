/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager;

import com.netckracker.graph.manager.convertor.Convertor;
import com.netckracker.graph.manager.iterator.NodeGraph;
import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.ReceipeVersion;
import com.netckracker.graph.manager.modelDto.EdgeDto;
import com.netckracker.graph.manager.modelDto.GraphDto;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.parallelization.GraphParallelization;
import com.netckracker.graph.manager.repository.EdgesRepository;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.NodeResourcesRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ReceipeVersionRepository;
import com.netckracker.graph.manager.repository.ResourcesRepository;
import com.netckracker.graph.manager.service.CatalogService;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ReceipeService;
import com.netckracker.graph.manager.service.ResourceService;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class GraphTest {
    @Autowired
    private NodeService nodeService;    
    
    @Autowired
    private ReceipeService receipeService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CatalogService catalogService;    
    @Autowired
    private ReceipeVersionRepository versionRepository;
    @Autowired
    private ReceipeRepository receipeRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private NodeGraph nodeGraph;
    @Autowired
    private GraphParallelization parallelization;
    @Test
    public void getParallelGraphTest()
    {
        String userId="11113";
          
        /*Создаем каталог и рецепт*/
     /*   String catalogId=catalogService.createCatalog("Пироги", "description");
        ReceipeDto receipe=receipeService.createReceipe("Шарлотка", "description", catalogId, userId, true);
        String receipeId=receipe.getReceipeId();
        
        /*Создаем ингредиенты рецепта*/
     /*   String ingredientId1=resourceService.createResource("Мука", userId, "г", "ingredient", "12345");
        String ingredientId2=resourceService.createResource("Яйцо", userId, "шт", "ingredient", "12346");
        String ingredientId3=resourceService.createResource("Сахар", userId, "г", "ingredient", "12347");
        String ingredientId4=resourceService.createResource("Яблоко", userId, "шт", "ingredient", "12348");
        String ingredientId5=resourceService.createResource("Соль", userId, "г", "ingredient", "12348");
        String ingredientId6=resourceService.createResource("Сода", userId, "г", "ingredient", "12348");
        
        /*Создаем ресурсы рецепта*/
   /*     String resourceId1=resourceService.createResource("Духовка", userId, "шт", "resource", "12345");
        String resourceId2=resourceService.createResource("Форма для запекания", userId, "шт", "resource", "12346");
 
        /*Добавляем ингредиенты к рецепту*/
   /*     receipeService.addReceipeResources(receipeId, userId, ingredientId1, 300);
        receipeService.addReceipeResources(receipeId, userId, ingredientId2, 4);
        receipeService.addReceipeResources(receipeId, userId, ingredientId3, 300);
        receipeService.addReceipeResources(receipeId, userId, ingredientId4, 5);
        receipeService.addReceipeResources(receipeId, userId, ingredientId5, 10);
        receipeService.addReceipeResources(receipeId, userId, ingredientId6, 10);
        receipeService.addReceipeResources(receipeId, userId, resourceId1, 1);
        receipeService.addReceipeResources(receipeId, userId, resourceId2, 1);
        
        /*Создаем ноды рецепта*/
     /*   String nodeId1=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId2=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId3=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId4=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId5=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId6=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId7=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId8=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId9=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId10=nodeService.createNode(receipe.getReceipeId(), userId);
        String nodeId11=nodeService.createNode(receipe.getReceipeId(), userId);
        
        /*Создаем описание нод*/
    /*    nodeService.addNodeDescription(nodeId1, "Отделить белки от желтков.");
        nodeService.addNodeDescription(nodeId2, "Белки взбить с половиной стакана сахара.");
        nodeService.addNodeDescription(nodeId3, "Желтки взбить с оставшимся сахаром.");
        nodeService.addNodeDescription(nodeId4, "Все смешать и добавить муку.");
        nodeService.addNodeDescription(nodeId5, "Добавить к предыдущему шагу соль и соду.");
        nodeService.addNodeDescription(nodeId6, "Нарезать яблоки.");
        nodeService.addNodeDescription(nodeId7, "Добавить к тесту яблоки.");
        nodeService.addNodeDescription(nodeId8, "Разогреть духовку.");
        nodeService.addNodeDescription(nodeId9, "Залить тесто в форму для выпечки.");
        nodeService.addNodeDescription(nodeId10, "Выпекать в духовке 40 мин.");
        nodeService.addNodeDescription(nodeId11, "Готово!");
        
        /*создаем выходные ресурсы*/
   /*     String outputIngredient1=resourceService.createResource("Белки", userId, "шт", "ingredient", "1111");
        String outputIngredient2=resourceService.createResource("Желтки", userId, "шт", "ingredient", "1111");
        String outputIngredient3=resourceService.createNodeResource("Белки с сахаром",nodeId2, "ingredient");
        String outputIngredient4=resourceService.createNodeResource("Желтки с сахаром",nodeId3, "ingredient");
        String outputIngredient5=resourceService.createNodeResource("Нарезанные яблоки",nodeId6, "ingredient");
        String outputIngredient6=resourceService.createNodeResource("Разогретая духовка",nodeId8,  "resource");
        String outputIngredient7=resourceService.createNodeResource("Тесто",nodeId4, "ingredient");
        String outputIngredient8=resourceService.createNodeResource("Тесто с солью",nodeId5,  "ingredient");
        String outputIngredient9=resourceService.createNodeResource("Тесто с солью и яблоками",nodeId7,  "ingredient");
        
        /*входные ресурсы Node1*/
    /*    ResourceDto node1Resource1=new ResourceDto();
        node1Resource1.setResourceId(ingredientId2);
        node1Resource1.setName("Яйцо");
        node1Resource1.setResourceNumber(3);
        
        List<ResourceDto> node1InputResources=Arrays.asList(node1Resource1);
        nodeService.addInputOrOutputResourcesToNode(nodeId1, node1InputResources, "input");
        
        /*выходные ресурсы Node1*/
      /*  ResourceDto node1Resource2=new ResourceDto();
        node1Resource2.setResourceId(outputIngredient1);
        node1Resource2.setName("Белки");
        node1Resource2.setResourceNumber(3);
        ResourceDto node1Resource3=new ResourceDto();
        node1Resource3.setResourceId(outputIngredient2);
        node1Resource3.setName("Желтки");
        node1Resource3.setResourceNumber(3);
        
        List<ResourceDto> node1OutputResources=Arrays.asList(node1Resource2,node1Resource3 );
        nodeService.addInputOrOutputResourcesToNode(nodeId1, node1OutputResources, "output");
        
        /*входные ресурсы Node2*/
      /*  ResourceDto node2Resource1=new ResourceDto();
        node2Resource1.setResourceId(ingredientId3);
        node2Resource1.setName("Сахар");
        node2Resource1.setResourceNumber(100);
        ResourceDto node2Resource2=new ResourceDto();
        node2Resource2.setResourceId(outputIngredient1);
        node2Resource2.setName("Белки");
        node2Resource2.setResourceNumber(3);
        List<ResourceDto> node2InputResources=Arrays.asList(node2Resource1, node2Resource2);
        nodeService.addInputOrOutputResourcesToNode(nodeId2, node2InputResources, "input");
        
        /*выходные ресурсы Node2*/
     /*   ResourceDto node2Resource3=new ResourceDto();
        node2Resource3.setResourceId(outputIngredient3);
        node2Resource3.setName("Белки с сахаром");
        node2Resource3.setResourceNumber(3);
 
        List<ResourceDto> node2OutputResources=Arrays.asList(node2Resource3 );
        nodeService.addInputOrOutputResourcesToNode(nodeId2, node2OutputResources, "output");
        
        /*входные ресурсы Node3*/
     /*   ResourceDto node3Resource1=new ResourceDto();
        node3Resource1.setResourceId(ingredientId3);
        node3Resource1.setName("Сахар");
        node3Resource1.setResourceNumber(100);
        ResourceDto node3Resource2=new ResourceDto();
        node3Resource2.setResourceId(outputIngredient2);
        node3Resource2.setName("Желтки");
        node3Resource2.setResourceNumber(3);
        List<ResourceDto> node3InputResources=Arrays.asList(node3Resource1, node3Resource2);
        nodeService.addInputOrOutputResourcesToNode(nodeId3, node3InputResources, "input");
        
        /*выходные ресурсы Node3*/
   /*     ResourceDto node3Resource3=new ResourceDto();
        node3Resource3.setResourceId(outputIngredient4);
        node3Resource3.setName("Желтки с сахаром");
        node3Resource3.setResourceNumber(3);
 
        List<ResourceDto> node3OutputResources=Arrays.asList(node3Resource3 );
        nodeService.addInputOrOutputResourcesToNode(nodeId3, node3OutputResources, "output");
        
        /*входные ресурсы Node4*/
   /*     ResourceDto node4Resource1=new ResourceDto();
        node4Resource1.setResourceId(outputIngredient4);
        node4Resource1.setName("Желтки с сахаром");
        node4Resource1.setResourceNumber(3);
        ResourceDto node4Resource2=new ResourceDto();
        node4Resource2.setResourceId(outputIngredient3);
        node4Resource2.setName("Белки с сахаром");
        node4Resource2.setResourceNumber(3);
        ResourceDto node4Resource3=new ResourceDto();
        node4Resource3.setResourceId(ingredientId1);
        node4Resource3.setName("Мука");
        node4Resource3.setResourceNumber(300);
        List<ResourceDto> node4InputResources=Arrays.asList(node4Resource1, node4Resource2, node4Resource3);
        nodeService.addInputOrOutputResourcesToNode(nodeId4, node4InputResources, "input");
        
        /*выходные ресурсы Node4*/
     /*   ResourceDto node4Resource4=new ResourceDto();
        node4Resource4.setResourceId(outputIngredient7);
        node4Resource4.setName("Тесто");
        node4Resource4.setResourceNumber(300);
        List<ResourceDto> node4OutputResources=Arrays.asList(node4Resource4 );
        nodeService.addInputOrOutputResourcesToNode(nodeId4, node4OutputResources, "output");
        
        
        /*входные ресурсы Node5*/
      /*  ResourceDto node5Resource1=new ResourceDto();
        node5Resource1.setPreviousNodeId(nodeId4);
        ResourceDto node5Resource2=new ResourceDto();
        node5Resource2.setResourceId(ingredientId5);
        node5Resource2.setName("Соль");
        node5Resource2.setResourceNumber(3);
        List<ResourceDto> node5InputResources=Arrays.asList(node5Resource1, node5Resource2);
        nodeService.addInputOrOutputResourcesToNode(nodeId5, node5InputResources, "input");
        
        
        /*выходные ресурсы Node5*/
    /*    ResourceDto node5Resource4=new ResourceDto();
        node5Resource4.setResourceId(outputIngredient8);
        node5Resource4.setName("Тесто с солью");
        node5Resource4.setResourceNumber(300);
        List<ResourceDto> node5OutputResources=Arrays.asList(node5Resource4 );
        nodeService.addInputOrOutputResourcesToNode(nodeId5, node5OutputResources, "output");
        
        /*входные ресурсы Node6*/
    /*    ResourceDto node6Resource1=new ResourceDto();
        node6Resource1.setResourceId(ingredientId4);
        node6Resource1.setName("Яблоки");
        node6Resource1.setResourceNumber(3);
        List<ResourceDto> node6InputResources=Arrays.asList(node6Resource1);
        nodeService.addInputOrOutputResourcesToNode(nodeId6, node6InputResources, "input");
        
        /*выходные ресурсы Node6*/
     /*   ResourceDto node6Resource2=new ResourceDto();
        node6Resource2.setResourceId(outputIngredient5);
        node6Resource2.setName("Нарезанные яблоки");
        node6Resource2.setResourceNumber(300);
        List<ResourceDto> node6OutputResources=Arrays.asList(node6Resource2 );
        nodeService.addInputOrOutputResourcesToNode(nodeId6, node6OutputResources, "output");
        
        /*входные ресурсы Node7*/
    /*    ResourceDto node7Resource1=new ResourceDto();
        node7Resource1.setResourceId(outputIngredient5);
        node7Resource1.setName("Нарезанные яблоки");
        node7Resource1.setResourceNumber(3);
        ResourceDto node7Resource2=new ResourceDto();
        node7Resource2.setPreviousNodeId(nodeId5);
        List<ResourceDto> node7InputResources=Arrays.asList(node7Resource1, node7Resource2);
        nodeService.addInputOrOutputResourcesToNode(nodeId7, node7InputResources, "input");
        
        /*выходные ресурсы Node7*/
    /*    ResourceDto node7Resource3=new ResourceDto();
        node7Resource3.setResourceId(outputIngredient9);
        node7Resource3.setName("Тесто с солью и яблоками");
        node7Resource3.setResourceNumber(300);
        List<ResourceDto> node7OutputResources=Arrays.asList(node7Resource3 );
        nodeService.addInputOrOutputResourcesToNode(nodeId7, node7OutputResources, "output");
        
        /*входные ресурсы Node8*/
     /*   ResourceDto node8Resource1=new ResourceDto();
        node8Resource1.setResourceId(resourceId1);
        node8Resource1.setName("Духовка");
        node8Resource1.setResourceNumber(1);
        List<ResourceDto> node8InputResources=Arrays.asList(node8Resource1);
        nodeService.addInputOrOutputResourcesToNode(nodeId8, node8InputResources, "input");
        
        /*выходные ресурсы Node8*/
    /*    ResourceDto node8Resource2=new ResourceDto();
        node8Resource2.setResourceId(outputIngredient6);
        node8Resource2.setName("Разогретая духовка");
        node8Resource2.setResourceNumber(1);
        List<ResourceDto> node8OutputResources=Arrays.asList(node8Resource2 );
        nodeService.addInputOrOutputResourcesToNode(nodeId8, node8OutputResources, "output");
        
        /*входные ресурсы Node9*/
    /*    ResourceDto node9Resource1=new ResourceDto();
        node9Resource1.setResourceId(resourceId2);
        node9Resource1.setName("Форма для выпечки");
        node9Resource1.setResourceNumber(1);
        ResourceDto node9Resource2=new ResourceDto();
        node9Resource2.setPreviousNodeId(nodeId7);
        node9Resource2.setResourceNumber(1);        
        List<ResourceDto> node9InputResources=Arrays.asList(node9Resource1, node9Resource2);
        nodeService.addInputOrOutputResourcesToNode(nodeId9, node9InputResources, "input");
        
         /*входные ресурсы Node10*/
     /*   ResourceDto node10Resource1=new ResourceDto();
        node10Resource1.setPreviousNodeId(nodeId8); 
        node10Resource1.setResourceNumber(1);
        ResourceDto node10Resource2=new ResourceDto();
        node10Resource2.setPreviousNodeId(nodeId9);
        node10Resource2.setResourceNumber(1);        
        List<ResourceDto> node10InputResources=Arrays.asList(node10Resource1, node10Resource2);
        nodeService.addInputOrOutputResourcesToNode(nodeId10, node10InputResources, "input");
        
        /*входные ресурсы Node11*/
   /*     ResourceDto node11Resource1=new ResourceDto();
        node11Resource1.setPreviousNodeId(nodeId10); 
        node11Resource1.setResourceNumber(1);      
        List<ResourceDto> node11InputResources=Arrays.asList(node11Resource1);
        nodeService.addInputOrOutputResourcesToNode(nodeId11, node11InputResources, "input");
        
        
        /*Додавляем id картинок к нодам*/
    /*    nodeService.addNodePicture(nodeId1, "nodepictureId1");
        nodeService.addNodePicture(nodeId2, "nodepictureId2");
        nodeService.addNodePicture(nodeId3, "nodepictureId3");
        nodeService.addNodePicture(nodeId4, "nodepictureId4");
        nodeService.addNodePicture(nodeId5, "nodepictureId5");
        nodeService.addNodePicture(nodeId6, "nodepictureId6");
        nodeService.addNodePicture(nodeId7, "nodepictureId7");
        nodeService.addNodePicture(nodeId8, "nodepictureId8");
        nodeService.addNodePicture(nodeId9, "nodepictureId9");
        nodeService.addNodePicture(nodeId10, "nodepictureId10");
        nodeService.addNodePicture(nodeId11, "nodepictureId11");
        
        /*Создаем связи*/
    /*    nodeService.createEdge(nodeId1, nodeId2);
        nodeService.createEdge(nodeId2, nodeId3);
        nodeService.createEdge(nodeId3, nodeId4);
        nodeService.createEdge(nodeId4, nodeId5);
        nodeService.createEdge(nodeId5, nodeId7);
        nodeService.createEdge(nodeId6, nodeId7);
        nodeService.createEdge(nodeId7, nodeId8);
        nodeService.createEdge(nodeId8, nodeId9);
        nodeService.createEdge(nodeId9, nodeId10);
        nodeService.createEdge(nodeId10, nodeId11);
        nodeService.createEdge(nodeId3, nodeId6);
        
        Receipe findReceipe=receipeRepository.findByReceipeId(receipeId);
        ReceipeVersion version=versionRepository.findByReceipeAndUserId(findReceipe, userId);
        parallelization.paralellGraph(version);
        GraphDto graph=nodeService.getReceipeGraph(receipeId, userId);
        Iterator iter=graph.getEdges().iterator();
        while (iter.hasNext())
        {
            EdgeDto edge=(EdgeDto) iter.next();
            Node startNode=nodeRepository.findByNodeId(edge.getStartNodeId());
            Node endNode=nodeRepository.findByNodeId(edge.getEndNodeId());
            System.out.println(startNode.getDescription()+" : "+endNode.getDescription());
        }
        
        List<Node> startNodes=nodeRepository.findFirst(version.getVersionId());
        for (int i=0; i<startNodes.size();i++)
        {
            System.out.println("START NODE"+startNodes.get(i).getDescription());
        }*/
    }

    
}
