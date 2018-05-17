/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;

import com.netckracker.graph.manager.model.Edges;
import com.netckracker.graph.manager.modelDto.GraphDto;
import com.netckracker.graph.manager.modelDto.NodeDto;
import com.netckracker.graph.manager.modelDto.ReceipeDto;
import com.netckracker.graph.manager.modelDto.ResourceDto;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ReceipeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private ReceipeService receipeService;
    
    @RequestMapping(value = "/node/addnode", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> addNode(@RequestParam String receipeId, @RequestParam String userId){
        String nodeId=nodeService.createNode(receipeId, userId);
        if (nodeId==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(nodeId, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/node/deletenode/{nodeId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNode(@PathVariable String nodeId){
        if (nodeService.isNodeExcist(nodeId)==true)
        {
            nodeService.deleteNode(nodeId);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/node/deleteedge", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEdge(@RequestParam ("startNodeId") String startNodeId, @RequestParam ("endNodeId") String endNodeId){
        if (nodeService.isNodeExcist(startNodeId)==true&&nodeService.isNodeExcist(endNodeId)==true)
        {
            nodeService.deleteEdge(startNodeId, endNodeId);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/node/addnodedescription/{nodeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addNodeDescription(@PathVariable String nodeId, @RequestParam String description){
        if (nodeService.isNodeExcist(nodeId)==true)
        {
            nodeService.addNodeDescription(nodeId, description);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
        @RequestMapping(value = "/node/addnodedelabel/{nodeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addNodeLabel(@PathVariable String nodeId, @RequestParam String label){
        if (nodeService.isNodeExcist(nodeId)==true)
        {
            nodeService.addNodeLabel(nodeId, label);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/node/addnodepicture/{nodeId}", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addNodePicture(@PathVariable String nodeId, @RequestParam String pictureId){
        if (nodeService.isNodeExcist(nodeId)==true)
        {
            nodeService.addNodePicture(nodeId, pictureId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/node/addedge", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addEdge(@RequestParam String startNodeId, @RequestParam String endNodeId){
        if (nodeService.isNodeExcist(startNodeId)==true&&nodeService.isNodeExcist(endNodeId)==true)
        {
            nodeService.createEdge(startNodeId, endNodeId);
            return new ResponseEntity<> (HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/node/addinputresources/{nodeId}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addInputResources(@PathVariable String nodeId, @RequestBody  List<ResourceDto> resources){
        if (nodeService.isNodeExcist(nodeId)==true)
        {
            nodeService.addInputOrOutputResourcesToNode(nodeId, resources, "input");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/node/addoutputresources/{nodeId}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Void> addOutputResources(@PathVariable String nodeId, @RequestBody  List<ResourceDto> resources){
        if (nodeService.isNodeExcist(nodeId)==true)
        {
            nodeService.addInputOrOutputResourcesToNode(nodeId, resources, "output");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/node/getinputresources/{nodeId}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getInputResources(@PathVariable String nodeId,@RequestParam String ingredientOrResource ){
        List<ResourceDto> resources =nodeService.getNodesResources(nodeId, "input", ingredientOrResource);
        if (resources.isEmpty())
        {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(resources,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/node/getoutputresources/{nodeId}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getOutputResources(@PathVariable String nodeId,@RequestParam String ingredientOrResource ){
        List<ResourceDto> resources =nodeService.getNodesResources(nodeId, "output", ingredientOrResource);
        if (resources.isEmpty())
        {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(resources,HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/graph/getgraph", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getGraph( @RequestParam ("userId") String userId, @RequestParam ("receipeId") String receipeId){
        if (receipeService.isVersionCompleted(receipeId)==true)
        {
            GraphDto graph=nodeService.getReceipeGraph(receipeId, userId);
            if (graph==null)
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else return new ResponseEntity<>(graph, HttpStatus.OK); 
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);     
    }
    @RequestMapping(value = "/graph/getparallelgraph", method = RequestMethod.GET ,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getParallelGraph( @RequestParam ("userId") String userId, @RequestParam ("receipeId") String receipeId){
        if (receipeService.isVersionCompleted(receipeId)==true)
        {
            GraphDto graph=nodeService.getReceipeParallelGraph(receipeId, userId);
            if (graph==null)
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else return new ResponseEntity<>(graph, HttpStatus.OK);  
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
    }
    
    @RequestMapping(value = "/graph/getnotcompletedgraph", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<?> getNotCompletedGraph( @RequestParam ("userId") String userId){
        
            ReceipeDto receipe=nodeService.getNotCompletedReceipe(userId);
            if (receipe==null)
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else return new ResponseEntity<>(receipe, HttpStatus.OK);  
    }
    
    @RequestMapping(value = "/graph/gettestgraph", method = RequestMethod.GET)
    public ResponseEntity<?> getTestGraph( @RequestParam ("userId") String userId, @RequestParam ("receipeId") String receipeId){
                GraphDto graph=nodeService.getReceipeTestGraph(receipeId, userId);
            return new ResponseEntity<>(graph, HttpStatus.OK);        
    }  
    
    
 
}
