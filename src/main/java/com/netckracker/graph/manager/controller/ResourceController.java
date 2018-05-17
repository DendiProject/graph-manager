/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;


import com.netckracker.graph.manager.modelDto.ResourceNameDto;
import com.netckracker.graph.manager.service.NodeService;
import com.netckracker.graph.manager.service.ResourceService;
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
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private NodeService nodeService;
    
    @RequestMapping(value = "/resource/getbyfirstletters/{letters}",method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public  ResponseEntity<?> getResourcesByLetters(@PathVariable  String  letters, @RequestParam String ingredientOrResource,
            @RequestParam (required=false) Integer page, @RequestParam (required=false) Integer size ){
        
        if (size==null&&page==null)
        {
            page=0;
            size=5;
        }
        
        List<ResourceNameDto> resources=resourceService.findByFirstLetters(letters, ingredientOrResource,page, size);
        if (resources.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{           
            return new ResponseEntity<>(resources, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/resource/addresource", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> addResource(@RequestParam String name, @RequestParam String ingredientOrResource, 
            @RequestParam String measuring, @RequestParam String userId, @RequestParam String pictureId){
         String resourceId=resourceService.createResource(name, userId, measuring, ingredientOrResource, pictureId);
     return new ResponseEntity<>(resourceId, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/resource/addnoderesource", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> addNodeResource(@RequestParam String name, @RequestParam String ingredientOrResource, 
            @RequestParam String nodeId){
        String resourceId=resourceService.createNodeResource(name, nodeId, ingredientOrResource);
        if (resourceId!=null)
        {
            return new ResponseEntity<>(resourceId, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
    @RequestMapping(value = "/resource/getall",method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public  ResponseEntity<?> getResources(){
        
        List<ResourceNameDto> resources=resourceService.findAll();
        if (resources.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{           
            return new ResponseEntity<>(resources, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/resource/getbyname/{name}",method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public  ResponseEntity<?> getResourcesByLetters(@PathVariable  String  name ){
        
        ResourceNameDto resource=resourceService.getByName(name);
        if (resource==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{           
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
    }
    
}
