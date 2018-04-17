/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.service.CatalogService;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
 


/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CatalogControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private WebApplicationContext wac;
    
    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test 
    public void createCatalogTest() throws Exception
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/catalog/create");
        request.param("catalogName", "deserts");
        request.param("description", "tasty deserts");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
      MvcResult  result = mockMvc.perform(request).andReturn(); 
      assertEquals("catalog incorrecrt", catalogService.findCatalog("deserts"), result.getResponse().getContentAsString());
    
    }
    
    @Test
    public void getCatalogByNameTest() throws Exception
    {
        /*String catalogId=catalogService.createCatalog("cackes", "description");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/catalog/getByName/cackes");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
       ResultActions result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
       
       MvcResult  result = mockMvc.perform(request).andReturn();  
       System.out.println(result.getResponse().getContentAsString());
       assertEquals("catalog incorrect", catalogId, result.getResponse().getContentAsString());*/
        
    }
    
}
