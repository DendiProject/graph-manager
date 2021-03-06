/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Node;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.repository.NodeRepository;
import com.netckracker.graph.manager.service.CatalogService;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ReceipeService;
import com.netckracker.graph.manager.service.ResourceService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author eliza
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
   private MockRestServiceServer mockServer;
    private final RestTemplate testRestTemplate=new RestTemplate();
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
    String userId;
    
    @LocalServerPort
    private int port;

    @Value("${server.contextPath}")
    String serverContexPath;

    protected String getAbsoluteUrl() {
        return "http://localhost:"+port +serverContexPath;
    }

     @Before	     
     public void setUp() {	     
        //restTemplate=new RestTemplate() ;	        
     }    	
    
    @Test
    public void addInputNodeResourcesTest()
    {
        Node node=new Node();
        Node saved=nodeRepository.save(node);
        userId="111233";
        List<ResourceDto> resources=new ArrayList<>();
        String resourceId1=resourceService.createResource("apple1", null, "piecec", "ingredient", null);
        String resourceId2=resourceService.createResource("apple2", null, "piecec", "ingredient", null);
        ResourceDto resource1=new ResourceDto();
        ResourceDto resource2=new ResourceDto();
        resource1.setResourceId(resourceId1);
        resource1.setResourceNumber(2);
        resource2.setResourceId(resourceId2);
        resource2.setResourceNumber(2.5);
        resources.add(resource1);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = getAbsoluteUrl() + "/node/addinputresources/"+node.getNodeId();
        HttpEntity<?> requestEntity = new HttpEntity<>(resources,headers);
        String response = testRestTemplate.postForObject(uri, requestEntity, String.class);
    } 
    
    @Test
    public void addOutputNodeResourcesTest()
    {
        Node node=new Node();
        Node saved=nodeRepository.save(node);
        userId="111231";
        List<ResourceDto> resources=new ArrayList<>();
        String resourceId1=resourceService.createResource("egg", null, "piecec", "ingredient", null);
        String resourceId2=resourceService.createResource("milk", null, "liters", "ingredient", null);
        ResourceDto resource1=new ResourceDto();
        ResourceDto resource2=new ResourceDto();
        resource1.setResourceId(resourceId1);
        resource1.setResourceNumber(2);
        resource2.setResourceId(resourceId2);
        resource2.setResourceNumber(2.5);
        resources.add(resource1);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = getAbsoluteUrl() + "/node/addoutputresources/"+node.getNodeId();
        HttpEntity<?> requestEntity = new HttpEntity<>(resources,headers);
        String response = testRestTemplate.postForObject(uri, requestEntity, String.class);
    } 
    
    

}
