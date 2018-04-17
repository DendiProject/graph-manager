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
    @RequestMapping(value = "/node/addegde", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> addEdge(@RequestParam String receipeId, @RequestParam String userId,
            @RequestParam String startNodeId, @RequestParam String endNodeId){
        Edges edge=nodeService.createEdge(startNodeId, endNodeId, receipeId, userId);
     return new ResponseEntity<>(edge, HttpStatus.OK);
    }
    @RequestMapping(value = "/node/addresources", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addResources(@RequestParam String receipeId, @RequestParam String userId,
            @RequestParam List<ResourceDto> resources, @RequestParam String nodeId,@RequestParam String InputOrOutputResources ){
        nodeService.addInputOrOutputResourcesToNode(receipeId, userId, nodeId, resources, InputOrOutputResources);
     return new ResponseEntity<>( HttpStatus.OK);
    }
    
    
}
