/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.model.Resources;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ResourcesRepository;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ResourceService;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
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
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ResourceControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    String userId="1111";
    String name="egg";
    String resourceOrIngredient="ingredient";
    String measuring="g";
    @Autowired
    private ResourcesRepository resourceRepository;
    @Autowired
    private ResourceService resourceService;
    @Autowired 
    private NodeRepository nodeRepository;
    @Autowired
    private NodeService nodeService;
    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test
    public void addResourceTest() throws Exception
    {
     MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/resource/addresource");
        request.param("name", name);
        request.param("ingredientOrResource",resourceOrIngredient );
        request.param("userId",userId );
        request.param("measuring", measuring);
        request.param("pictureId","null");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        Resources resource=resourceRepository.findByResourceId(result.getResponse().getContentAsString());
      assertEquals("resourceId incorrecrt", resource.getResourceId(), result.getResponse().getContentAsString());
      assertEquals("name incorrecrt", name, resource.getName());
      assertEquals("resourceOrIngredient incorrect", resourceOrIngredient, resource.getIngredientOrResource());
      assertEquals("measuring incorrect", measuring, resource.getMeasuring());
    }
    
    @Test
    public void getResourcesByFirstLettersTest() throws UnsupportedEncodingException, Exception
    {
        
        String resourceId1=resourceService.createResource("apple1", null, "piecec", "ingredient", null);
        String resourceId2=resourceService.createResource("apple2", null, "piecec", "ingredient", null);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .get("/resource/getbyfirstletters/app");
        request.param("page", "0");
        request.param("size", "5");
        request.param("ingredientOrResource", "ingredient");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        //ResultActions result = mockMvc.perform(request)
         //        .andExpect(MockMvcResultMatchers.status().isOk());
        MvcResult  result = mockMvc.perform(request).andReturn();         
       String expected = "[{resourceId:"+resourceId1+",name:apple1},{resourceId:"+resourceId2+",name:apple2}]";
       JSONAssert.assertEquals( expected, result.getResponse().getContentAsString(), false);
        
    }
    
    @Test
    public void addNodeRessourceTest() throws Exception
    {
        Node node=new Node();
        Node saved=nodeRepository.save(node);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/resource/addnoderesource");
        request.param("name", name);
        request.param("ingredientOrResource",resourceOrIngredient );
        request.param("userId",userId );
        request.param("measuring", measuring);
        request.param("pictureId","null");
        request.param("nodeId",saved.getNodeId());
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    
}
