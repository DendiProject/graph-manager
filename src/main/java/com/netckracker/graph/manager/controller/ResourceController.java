/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netckracker.graph.manager.controller;


import com.netckracker.graph.manager.modelDto.ResourceNameDto;
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
    
    @RequestMapping(value = "/resource/getbyfirstletters/{letters}", params = { "page", "size" },method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public  ResponseEntity<?> getResourcesByLetters(@PathVariable  String  letters, @RequestParam( "page" ) int page, @RequestParam( "size" ) int size ){
        
        if (size==0&&page==0)
        {
            page=0;
            size=6;
        }
        
        List<ResourceNameDto> receipes=resourceService.findByFirstLetters(letters, page, size);
        if (receipes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{           
            return new ResponseEntity<>(receipes, HttpStatus.OK);
        }
    }
    
     @RequestMapping(value = "/resource/getbyname/{name}", method = RequestMethod.GET, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> getResourceByName(@PathVariable String name){
        String resourceId=resourceService.getResourceIdByName(name);
     return new ResponseEntity<>(resourceId, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/resource/addresource", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> addResource(@RequestParam String name, @RequestParam String resourceOrIngredient, 
            @RequestParam String measuring, @RequestParam String userId, @RequestParam String pictureId){
         String resourceId=resourceService.createResource(name, userId, measuring, resourceOrIngredient, pictureId);
     return new ResponseEntity<>(resourceId, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/resource/addnoderesource", method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<String> addNodeResource(@RequestParam String name, @RequestParam String resourceOrIngredient, 
            @RequestParam String measuring, @RequestParam String nodeId){
         String resourceId=resourceService.createNodeResource(name, nodeId, measuring, resourceOrIngredient);
     return new ResponseEntity<>(resourceId, HttpStatus.OK);
    }
    
    
}
