/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.service.CatalogService;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ReceipeService;
import com.netckracker.graph.manager.service.ResourceService;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ReceipeControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ReceipeService receipeService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private ReceipeRepository receipeRepository;
    @Autowired
    private WebApplicationContext wac;
    String receipeId;
    String name="cake";
    String description="tasty cake";
    String userId;
    boolean isPublic=true;
    boolean isCompleted=false;
    boolean isDeleted=false;
    String catalogId;
    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test()
    public void addReceipeTest() throws Exception
    {
        userId="1111";
        catalogId=catalogService.createCatalog("mycatalog", "description");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/receipe/addreceipe");
        request.param("name", name);
        request.param("description",description );
        request.param("userId", userId);
        request.param("catalogId", catalogId);
        request.param("isPublic", "true");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
     
      MvcResult  result = mockMvc.perform(request).andReturn(); 
      JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString()); 
      receipeId=jsonObject.get("receipeId").toString();
      
      Receipe receipe=receipeRepository.findByReceipeId(receipeId);
      assertEquals("receipeId incorrecrt", receipe.getReceipeId(), receipeId);
      assertEquals("receipe name incorrecrt", name, receipe.getName());
      assertEquals("receipe description incorrecrt", description, receipe.getDescription());
      assertEquals("receipe catalogId incorrecrt", catalogId, receipe.getCatalog().getCatalogId());
      assertEquals("receipe isPublic incorrecrt", isPublic, receipe.isIsPublic());
    }
    
    @Test
    public void getReceipeInformation() throws Exception
    {
        userId="1234";
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, isPublic);        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/receipe/getreceipeinfo/"+receipe.getReceipeId());
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        /*ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());*/
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString()); 
        receipeId=jsonObject.get("receipeId").toString();
        assertEquals("receipeId incorrecrt", receipe.getReceipeId(), receipeId);
        assertEquals("receipe name incorrecrt", name, jsonObject.get("name").toString());
        assertEquals("receipe description incorrecrt", description, jsonObject.get("description").toString());
        assertEquals("receipe isPublic incorrecrt", String.valueOf(isPublic), jsonObject.get("isPublic").toString());
        assertEquals("receipe isPublic incorrecrt", String.valueOf(isCompleted), jsonObject.get("isCompleted").toString());
        assertEquals("receipe isDeleted incorrecrt", String.valueOf(isDeleted), jsonObject.get("isDeleted").toString());
    }
    
    @Test
    public void deleteReceipeTest() throws Exception
    {
        userId="12345";
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, isPublic);        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/receipe/delete/"+receipe.getReceipeId());
        request.param("userId", userId);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void getPublicAndCompletedReceipesTest() throws Exception
    {
        ReceipeDto receipe1=receipeService.createReceipe(name, description, catalogId, userId, true);
        ReceipeDto receipe2=receipeService.createReceipe(name, description, catalogId, userId, true);
        receipeService.setCompleted(receipe1.getReceipeId());
        receipeService.setCompleted(receipe2.getReceipeId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/receipe/getpublicandcompleted");
        request.param("page", "0");
        request.param("size", "5");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        String expected = "[{receipeId:"+receipe1.getReceipeId()+"},{receipeId:"+receipe2.getReceipeId()+"}]";
       JSONAssert.assertEquals( expected, result.getResponse().getContentAsString(), false);
    }
    
    @Test 
    public void getReceipeByCatalogTest() throws Exception
    {
        catalogId=catalogService.createCatalog("newcatalog", "description");
        ReceipeDto receipe1=receipeService.createReceipe(name, description, catalogId, userId, true);
        ReceipeDto receipe2=receipeService.createReceipe(name, description, catalogId, userId, true);
        receipeService.setCompleted(receipe1.getReceipeId());
        receipeService.setCompleted(receipe2.getReceipeId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/receipe/getbycatalog/"+catalogId);
        request.param("page", "0");
        request.param("size", "5");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
       
        String expected = "[{receipeId:"+receipe1.getReceipeId()+"},{receipeId:"+receipe2.getReceipeId()+"}]";
       JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    @Test
    public void setReceipeCompletedTest() throws Exception
    {
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/receipe/setcompleted/"+receipe.getReceipeId());
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
        Receipe findedReceipe=receipeRepository.findByReceipeId(receipe.getReceipeId());
        assertEquals("receipe isCompleted incorrecrt", true, findedReceipe.isIsCompleted());
    }
    
    @Test
    public void addReceipeResourceTest() throws Exception
    {
        userId="11112222";
        String resourceId=resourceService.createResource("egg", userId, "g", "ingredient", null);       
        ReceipeDto receipe=receipeService.createReceipe(name, description, catalogId, userId, true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/receipe/addreceiperesources/"+receipe.getReceipeId());
        request.param("userId",userId);
        request.param("resourceId", resourceId);
        request.param("resourceNumber", String.valueOf(2));
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());       
    }
    
    @Test 
    public void getGraphTest() throws Exception
    {
        userId="9999";
        catalogId=catalogService.createCatalog("soupcatalog", "description");
        ReceipeDto receipe=receipeService.createReceipe("soup", "description", catalogId, userId, true);
        
        String resourceId=resourceService.createResource("egg", userId, "g", "ingredient", null); 
        receipeService.addReceipeResources(receipe.getReceipeId(), userId, resourceId, 2);
        
        String startNodeId=nodeService.createNode(receipe.getReceipeId(), userId);
        nodeService.addNodeDescription(startNodeId, "description");
        nodeService.addNodePicture(startNodeId, "1111");
        
        String endNodeId=nodeService.createNode(receipe.getReceipeId(), userId);
        nodeService.addNodeDescription(endNodeId, "description");
        nodeService.addNodePicture(endNodeId, "1111");
        
        nodeService.createEdge(startNodeId, endNodeId);
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/graph/getgraph");
        request.param("receipeId", receipe.getReceipeId());
        request.param("userId",userId);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray resources=jsonObject.getJSONArray("indredients");
        JSONArray nodes=jsonObject.getJSONArray("nodes");
        JSONArray edges=jsonObject.getJSONArray("edges");
        JSONObject resource = resources.getJSONObject(0);
        JSONObject node1=nodes.getJSONObject(0);
        JSONObject node2=nodes.getJSONObject(1);
        JSONObject edge=edges.getJSONObject(0);
        
        assertEquals("ingredient Id incorrect", resourceId, resource.getString("resourceId"));
        assertEquals("ingredient name incorrecrt", "egg", resource.getString("name"));
        assertEquals("ingredient number incorrecrt","2.0", resource.getString("resourceNumber"));
        assertEquals("first nodeId incorrect", startNodeId, node1.getString("nodeId"));
        assertEquals("first node description incorrect", "description", node1.getString("description"));
        assertEquals("first node pictureId incorrect", "1111", node1.getString("pictureId"));
        assertEquals("second nodeId incorrect", endNodeId, node2.getString("nodeId"));
        assertEquals("second node description incorrect", "description", node2.getString("description"));
        assertEquals("second node pictureId incorrect", "1111", node2.getString("pictureId"));
        assertEquals("start node id incorrect", startNodeId, edge.getString("startNodeId"));
        assertEquals("end node id incorrect", endNodeId, edge.getString("endNodeId"));
    }
    
    @Test
    public void getTestGraph() throws Exception
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/graph/gettestgraph");
        request.param("receipeId", "12345");
        request.param("userId","1111");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        System.out.println(result.getResponse().getContentAsString());
       // JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
    }
    
}
