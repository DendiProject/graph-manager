/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.modelDto.GraphDto;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.service.CatalogService;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ReceipeService;
import com.netckracker.graph.manager.service.TopOfReceipesService;
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
public class TopControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
     @Autowired
    private ReceipeService receipeService;
    @Autowired
    private TopOfReceipesService topService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private NodeService nodeService;
    
    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test
    public void getAllTopTest() throws Exception
    {
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/topofreceipes/gettop");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        //ResultActions  result = mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
        MvcResult  result2 = mockMvc.perform(request).andReturn(); 
        System.out.println(result2.getResponse().getContentAsString());
        
    }
    
    @Test
    public void getTopForUserTest() throws Exception
    {
        String userId="197635776551";
        String userId2="0213543668";
        String catalogId=catalogService.createCatalog("catalog113", "description");
        ReceipeDto receipe1=receipeService.createReceipe("Паста", "descr", catalogId, userId, true);
        ReceipeDto receipe2=receipeService.createReceipe("Лазанья", "descr", catalogId, userId, true);
        
        
        receipeService.setCompleted(receipe1.getReceipeId());
        receipeService.setCompleted(receipe2.getReceipeId());
        for (int i=0; i<10;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe1.getReceipeId(), userId);
        }
        for (int i=0; i<6;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe2.getReceipeId(), userId2);
        }
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/topofreceipes/gettop");
        request.param("page", "0");
        request.param("size", "5");
        request.param("userId", userId2);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        System.out.println(result.getResponse().getContentAsString());
        
        JSONArray receipes=new JSONArray(result.getResponse().getContentAsString());
        JSONObject receipe11=receipes.getJSONObject(0);        
        
        assertEquals("receipe Id incorrect", receipe2.getReceipeId(), receipe11.getString("receipeId"));
    }
    
    @Test
    public void getTopByCatalogTest() throws Exception
    {
        String userId="1945356751";
        String userId2="021311888";
        String catalogId=catalogService.createCatalog("Мясо", "description");
        String catalogId2=catalogService.createCatalog("Cладкое", "description");
        
        ReceipeDto receipe=receipeService.createReceipe("Чизкейк", "descr", catalogId2, userId, true);
        ReceipeDto receipe2=receipeService.createReceipe("Мясо по французски", "descr", catalogId, userId, true);
        
        receipeService.setCompleted(receipe.getReceipeId());
        receipeService.setCompleted(receipe2.getReceipeId());
        for (int i=0; i<2;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe.getReceipeId(), userId);
        }
        for (int i=0; i<1;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe2.getReceipeId(), userId2);
        }
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/topofreceipes/gettop");
        request.param("page", "0");
        request.param("size", "5");
        request.param("catalogId", catalogId);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        System.out.println(result.getResponse().getContentAsString());
        
        JSONArray receipes=new JSONArray(result.getResponse().getContentAsString());
        JSONObject receipe11=receipes.getJSONObject(0);
        
        assertEquals("receipe 1 Id incorrect", receipe2.getReceipeId(), receipe11.getString("receipeId"));
    }
    
    @Test
    public void getTopByCatalogandUserTest() throws Exception
    {
        String userId="19751";
        String userId2="02138";
        String userId3="1234579";
        String catalogId=catalogService.createCatalog("Мясо1", "description");
        String catalogId2=catalogService.createCatalog("Cладкое1", "description");
        ReceipeDto receipe=receipeService.createReceipe("Пирог", "descr", catalogId2, userId, true);
        ReceipeDto receipe2=receipeService.createReceipe("Телятина", "descr", catalogId, userId, true);        
        ReceipeDto receipe3=receipeService.createReceipe("Курица", "descr", catalogId, userId2, true);
        
        receipeService.setCompleted(receipe.getReceipeId());
        receipeService.setCompleted(receipe2.getReceipeId());
        receipeService.setCompleted(receipe3.getReceipeId());
        for (int i=0; i<5;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe.getReceipeId(), userId);
        }
        for (int i=0; i<3;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe2.getReceipeId(), userId2);
        }
        for (int i=0; i<7;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe3.getReceipeId(), userId3 );
        }
        for (int i=0; i<4;i++)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipe2.getReceipeId(), userId3);
        }
        
        topService.getTopOfReceipesByCatalogAndUser(0, 5, userId3, catalogId);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/topofreceipes/gettop");
        request.param("page", "0");
        request.param("size", "5");
        request.param("catalogId", catalogId);
        request.param("userId", userId3);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        System.out.println(result.getResponse().getContentAsString());
        
        JSONArray receipes=new JSONArray(result.getResponse().getContentAsString());
        JSONObject receipe11=receipes.getJSONObject(0);
        JSONObject receipe22=receipes.getJSONObject(1);
        
        assertEquals("receipe 1 Id incorrect", receipe3.getReceipeId(), receipe11.getString("receipeId"));
        assertEquals("receipe 2 Id incorrect", receipe2.getReceipeId(), receipe22.getString("receipeId"));
    }
    
}
