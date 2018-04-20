/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.google.gson.Gson;
import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.modelDto.ResourceNameDto;
import com.netckracker.graph.manager.repository.CatalogRepository;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.service.CatalogService;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ReceipeService;
import com.netckracker.graph.manager.service.ResourceService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class NodeControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private ReceipeService receipeService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private WebApplicationContext wac;
    String receipeId;
    String name="pie";
    String description="apple pie";
    String userId;
    boolean isPublic=true;
    boolean isCompleted=false;
    boolean isDeleted=false;
    String catalogId;
    
    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test
    public void addNodeTest() throws Exception
    {
        userId="112233";
        catalogId=catalogService.createCatalog("pies", "description");
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/node/addnode");
        request.param("receipeId",receipe.getReceipeId());
        request.param("userId",userId);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void addNodeDescriptionTest() throws Exception
    {
        userId="11233";
        String nodeDescription="beat the eggs";
        //catalogId=catalogService.createCatalog("apple pies", "description");
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, true);
        String nodeId=nodeService.createNode(receipe.getReceipeId(), userId);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/node/addnodedescription/"+nodeId);
        request.param("description",nodeDescription);  
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk()); 
        Node node=nodeRepository.findByNodeId(nodeId);
        assertEquals("description incorrecrt",nodeDescription, node.getDescription());
        
    }
    @Test
    public void addEdgeTest() throws Exception
    {
        userId="11233";
        //catalogId=catalogService.createCatalog("apple pies", "description");
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, true);
        String startNodeId=nodeService.createNode(receipe.getReceipeId(), userId);
        String endNodeId=nodeService.createNode(receipe.getReceipeId(), userId);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/node/addedge");
        request.param("startNodeId",startNodeId);  
        request.param("endNodeId",endNodeId);  
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
    } 
    
    @Test
    public void addNodeResource() throws Exception
    {
        userId="111233";
        List<ResourceDto> resources=new ArrayList<>();
        String resourceId1=resourceService.createResource("orange1", null, "piecec", "ingredient", null);
        String resourceId2=resourceService.createResource("orange2", null, "piecec", "ingredient", null);
        ResourceDto resource1=new ResourceDto();
        ResourceDto resource2=new ResourceDto();
        resource1.setResourceId(resourceId1);
        resource1.setResourceNumber(2);
        resource1.setResourceId(resourceId2);
        resource1.setResourceNumber(2.5);
        resources.add(resource1);
        resources.add(resource2);        
        String json = new Gson().toJson(resources);
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, true);
        String nodeId=nodeService.createNode(receipe.getReceipeId(), userId);
       /* MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/node/addresources/"+nodeId);
        request.param("InputOrOutputResources","input");  
        request.param("resources[0].resourceId",resourceId1 );  
        request.param("resources[0].resourceNumber","2" );  
        request.content(json);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());*/
    }
    
}
