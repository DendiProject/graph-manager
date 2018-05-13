/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Catalog;
import com.netckracker.graph.manager.repository.CatalogRepository;
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
    private CatalogRepository catalogRepository;
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
      assertEquals("catalog incorrecrt", catalogService.findCatalog("deserts").getCatalogId(), result.getResponse().getContentAsString());
    
    }
    
    @Test
    public void findCatalogByNameTest() throws Exception 
    {
        String catalogId=catalogService.createCatalog("cackes", "description");
        Catalog catalog=catalogRepository.findByCatalogId(catalogId);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/catalog/getbyname/cackes");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);     
      MvcResult  result = mockMvc.perform(request).andReturn();  
     JSONAssert.assertEquals("{catalogId:"+catalog.getCatalogId()+",name:"+catalog.getName()+",description:"+catalog.getDescription()+"}", 
             result.getResponse().getContentAsString(), false);        
    }
    @Test
    public void getCatalogsByLetters() throws Exception
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/catalog/create");
        request.param("catalogName", "indiankirchen");
        request.param("description", "tasty food");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
      MvcResult  result = mockMvc.perform(request).andReturn(); 
      //assertEquals("catalog incorrecrt", catalogService.findCatalog("deserts").getCatalogId(), result.getResponse().getContentAsString());
      
        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .get("/catalog/getbyletters/indi");
       // request2.param("page", "0");
        //request2.param("size", "5");
        request2.accept(MediaType.APPLICATION_JSON);
        request2.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result2= mockMvc.perform(request2).andReturn();
        System.out.println("CATALOGS");
        System.out.println(result2.getResponse().getContentAsString());
    }
    
}
