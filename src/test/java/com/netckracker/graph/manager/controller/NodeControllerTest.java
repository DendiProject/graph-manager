/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.MvcResult;
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
        assertEquals("description incorrecrt",nodeDescription, node.getDescriptionId());
        
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
    public void deleteNodeTest() throws Exception
    {
        Node node=new Node();
        Node saved=nodeRepository.save(node);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/node/deletenode/"+saved.getNodeId());
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test 
    public void deleteEdgeTest() throws Exception
    {
        Node startNode=new Node();
        Node saved1=nodeRepository.save(startNode);
        Node endNode=new Node();
        Node saved2=nodeRepository.save(endNode);
        nodeService.createEdge(saved1.getNodeId(), saved2.getNodeId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/node/deleteedge");
        request.param("startNodeId",saved1.getNodeId());  
        request.param("endNodeId",saved2.getNodeId());  
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void getNodeInputResources() throws Exception
    {
        Node node=new Node();
        Node saved=nodeRepository.save(node);
        String resourceId=resourceService.createResource("bread", userId, "g", "ingredient", null);
        ResourceDto resource=new ResourceDto();
        resource.setResourceId(resourceId);
        resource.setName("bread");
        resource.setResourceNumber(3);
        List<ResourceDto> resources =new ArrayList<>();
        resources.add(resource);
        nodeService.addInputOrOutputResourcesToNode(saved.getNodeId(), resources, "input");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/node/getinputresources/"+saved.getNodeId());
        request.param("ingredientOrResource","ingredient"); 
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        
        JSONArray array = new JSONArray(result.getResponse().getContentAsString());     
        JSONObject jsonResource = array.getJSONObject(0);

        assertEquals("ingredient Id incorrect", resourceId, jsonResource.getString("resourceId"));
        assertEquals("ingredient name incorrecrt", "bread", jsonResource.getString("name"));
        assertEquals("ingredient number incorrecrt","3.0", jsonResource.getString("resourceNumber"));
    }
    
    @Test
    public void getNodeOutPutResources() throws Exception
    {
        Node node=new Node();
        Node saved=nodeRepository.save(node);
        String resourceId=resourceService.createResource("milk", userId, "g", "ingredient", null);
        ResourceDto resource=new ResourceDto();
        resource.setResourceId(resourceId);
        resource.setName("bread");
        resource.setResourceNumber(1);
        List<ResourceDto> resources =new ArrayList<>();
        resources.add(resource);
        nodeService.addInputOrOutputResourcesToNode(saved.getNodeId(), resources, "output");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/node/getoutputresources/"+saved.getNodeId());
        request.param("ingredientOrResource","ingredient"); 
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        
        JSONArray array = new JSONArray(result.getResponse().getContentAsString());     
        JSONObject jsonResource = array.getJSONObject(0);

        assertEquals("ingredient Id incorrect", resourceId, jsonResource.getString("resourceId"));
        assertEquals("ingredient name incorrecrt", "milk", jsonResource.getString("name"));
        assertEquals("ingredient number incorrecrt","1.0", jsonResource.getString("resourceNumber"));
    }
    
    @Test
    public void getReceipeParalellGraph()
    {
        
    }
}
