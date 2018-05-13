/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Receipe;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.repository.ReceipeRepository;
import com.netckracker.graph.manager.repository.ResourcesRepository;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ResourceService;
import com.netckracker.graph.manager.service.TagService;
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
public class TagControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    String receipeId;
    @Autowired
    private ResourcesRepository resourceRepository;
    @Autowired
    private ResourceService resourceService;
    @Autowired 
    private ReceipeRepository receipeRepository;
    @Autowired
    private TagService tagService;
    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test
    public void addTagTest() throws Exception
    {
        Receipe receipe = new Receipe();
        receipe.setName("pie");
        Receipe saved=receipeRepository.save(receipe);
        receipeId=saved.getReceipeId();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/receipe/addtag/"+saved.getReceipeId());
        request.param("tagName", "pies");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void getReceipesByTags() throws Exception
    {
        Receipe receipe = new Receipe();
        receipe.setName("meat");
        Receipe saved=receipeRepository.save(receipe);
        receipeId=saved.getReceipeId();
        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders
                .post("/receipe/addtag/"+saved.getReceipeId());
        request3.param("tagName", "dinner");
        request3.accept(MediaType.APPLICATION_JSON);
        request3.contentType(MediaType.APPLICATION_JSON);
        ResultActions result3 = mockMvc.perform(request3)
                 .andExpect(MockMvcResultMatchers.status().isOk());
        
        Receipe receipe1 = new Receipe();
        receipe1.setName("meat");
        Receipe saved1=receipeRepository.save(receipe1);
        Receipe receipe2 = new Receipe();
        receipe1.setName("soup");
        Receipe saved2=receipeRepository.save(receipe2);
        tagService.addTag(saved1.getReceipeId(), "dinner");
        tagService.addTag(saved2.getReceipeId(), "dinner");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/receipe/getbytag/dinner");
        request.param("page", "0");
        request.param("size", "5");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result = mockMvc.perform(request).andReturn(); 
        System.out.println(result.getResponse().getContentAsString());
        //String expected = "[{receipeId:"+receipe1.getReceipeId()+"},{receipeId:"+receipe2.getReceipeId()+"}]";
        //JSONAssert.assertEquals( expected, result.getResponse().getContentAsString(), false);
        /*ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());*/
        
        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .get("/tag/getbyletters/dinn");
        request2.param("page", "0");
        request2.param("size", "5");
        request2.accept(MediaType.APPLICATION_JSON);
        request2.contentType(MediaType.APPLICATION_JSON);
        MvcResult  result2= mockMvc.perform(request2).andReturn();
        System.out.println("TAGS");
        System.out.println(result2.getResponse().getContentAsString());
    }
    
    @Test
    public void getTagsByLetters() throws Exception
    {
        Receipe receipe = new Receipe();
        receipe.setName("meat");
        Receipe saved=receipeRepository.save(receipe);
        receipeId=saved.getReceipeId();
        tagService.addTag(receipeId, "tasty food");
        tagService.addTag(receipeId, "tasty meat");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/tag/getbyletters/tasty");
        request.param("page", "0");
        request.param("size", "5");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request)
                 .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
