/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.service.NodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author eliza
 */
@RestController
public class NodeController {
    @Autowired
    private NodeService nodeService;
    
    @RequestMapping(value = "/node/addnode", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> addNode(@RequestParam String receipeId, @RequestParam String userId){
        String nodeId=nodeService.createNode(receipeId, userId);
     return new ResponseEntity<>(nodeId, HttpStatus.OK);
    }
    @RequestMapping(value = "/node/addnodedescription/{nodeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> addNodeDescription(@PathVariable String nodeId, @RequestParam String description){
        nodeService.addNodeDescription(nodeId, description);
     return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/node/addedge", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addEdge(@RequestParam String startNodeId, @RequestParam String endNodeId){
        nodeService.createEdge(startNodeId, endNodeId);
     return new ResponseEntity<> (HttpStatus.OK);
    }
    @RequestMapping(value = "/node/addresources/{nodeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> addResources(@PathVariable String nodeId, @RequestParam ("resources") List<ResourceDto> resources, 
            @RequestParam ("inputOrOutputResources") String inputOrOutputResources ){
       // nodeService.addInputOrOutputResourcesToNode(nodeId,/* resources,*/ InputOrOutputResources);
     return new ResponseEntity<>( HttpStatus.OK);
    }
    
    
}
